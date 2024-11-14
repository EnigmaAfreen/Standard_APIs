package com.airtel.buyer.airtelboe.service.fetchagentlist;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.fetchchaagentlist.response.ChaAgentList;
import com.airtel.buyer.airtelboe.dto.fetchchaagentlist.response.ChaAgentListData;
import com.airtel.buyer.airtelboe.dto.fetchchareassignmentpagedata.request.FetchChaReassignmentPageDataRequest;
import com.airtel.buyer.airtelboe.dto.fetchchareassignmentpagedata.response.ChaReassignmentPageData;
import com.airtel.buyer.airtelboe.dto.fetchchareassignmentpagedata.response.ChaReassignmentPageDataList;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_EMP_ROLE_MAPPING_TBL;
import com.airtel.buyer.airtelboe.repository.ChaAgents;
import com.airtel.buyer.airtelboe.repository.ChaReAssgnmtData;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlEmpRoleMappingTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeChaTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipmentTblRepository;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class ChaReAssignmentServiceImpl implements ChaReAssignmentService {

    @Value("${app.node}")
    public String appNode;

    @Autowired
    BtvlMstBoeChaTblRepository btvlMstBoeChaTblRepository;

    @Autowired
    BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    BtvlEmpRoleMappingTblRepository btvlEmpRoleMappingTblRepository;

    @Override
    public BoeResponse<ChaAgentListData> fetchChaAgentList() {
        BoeResponse boeResponse = new BoeResponse();
        ChaAgentListData chaAgentListData = new ChaAgentListData();
        boeResponse.setData(chaAgentListData);
        List<ChaAgents> chaAgentList = this.btvlMstBoeChaTblRepository.fetchChaAgentList();
        if (chaAgentList != null) {
            ChaAgentList agentList = new ChaAgentList();
            agentList.setChaCode("Assignment Failed");
            agentList.setChaName("Assignment Failed");

            chaAgentListData.setFromChaAgentList(Stream.concat(
                    chaAgentList.stream().map(this::getChaAgentObject),
                    Stream.of(agentList)
            ).collect(Collectors.toList()));

            chaAgentListData.setToChaAgentList(chaAgentList.stream().map(this::getChaAgentObject).collect(Collectors.toList()));

        }
        return boeResponse;
    }

    @Override
    public BoeResponse<ChaReassignmentPageData> fetchReAssignmentPageData(FetchChaReassignmentPageDataRequest fetchChaReassignmentPageDataRequest, String olm, String role) {
        BoeResponse<ChaReassignmentPageData> boeResponse = new BoeResponse<>();
        ChaReassignmentPageData chaReassignmentPageData = new ChaReassignmentPageData();
//        boeResponse.setData(chaReassignmentPageData);
        List<Integer> stagelist = null;
        if (fetchChaReassignmentPageDataRequest.getFromChaCode() != null) {
            if (!StringUtils.isBlank(fetchChaReassignmentPageDataRequest.getFromChaCode()) && !fetchChaReassignmentPageDataRequest.getFromChaCode().equalsIgnoreCase("Assignment Failed")) {
                log.info("record processing for approved and not approved case");
                stagelist = new ArrayList<>();
                stagelist.add(3);
                stagelist.add(5);
                chaReassignmentPageData = this.fetchData(fetchChaReassignmentPageDataRequest, stagelist);

            } else if (!StringUtils.isBlank(fetchChaReassignmentPageDataRequest.getFromChaCode()) &&
                    fetchChaReassignmentPageDataRequest.getFromChaCode().equalsIgnoreCase("Assignment Failed")) {
                stagelist = new ArrayList<>();
                chaReassignmentPageData = this.fetchData(fetchChaReassignmentPageDataRequest, stagelist);
            }


            String lobVal = null;
            if (role.equalsIgnoreCase("SCM_LOB") && !StringUtils.isBlank(role)) {
                log.info("Filtering records for SCM_LOB");
                lobVal = this.getLoggedInAirtelUserSCMLob(olm, 13, boeResponse);
                if (!StringUtils.isBlank(lobVal)) {
                    List<String> lobValList = Arrays.asList(lobVal.split(","));
                    log.info("lobValList " + lobValList);
                    if (fetchChaReassignmentPageDataRequest.getFromChaCode().equalsIgnoreCase("Assignment Failed")) {
                        log.info("Filtering records for SCM_LOB and for Assignment Failed case");

                        if (chaReassignmentPageData.getAssignmentFailedList() != null) {
                            chaReassignmentPageData.setAssignmentFailedList(chaReassignmentPageData.getAssignmentFailedList()
                                    .stream()
                                    .filter(x -> lobValList.contains(x.getLob()))
                                    .collect(Collectors.toList()));
                        }
                    } else {
                        log.info("Filtering records for SCM_LOB and for approved and not approved case");
                        if (chaReassignmentPageData.getNotApprovedList() != null) {
                            chaReassignmentPageData.setNotApprovedList(chaReassignmentPageData.getNotApprovedList()
                                    .stream()
                                    .filter(x -> lobValList.contains(x.getLob()))
                                    .collect(Collectors.toList()));
                        }
                        if (chaReassignmentPageData.getApprovedList() != null) {
                            chaReassignmentPageData.setApprovedList(chaReassignmentPageData.getApprovedList()
                                    .stream()
                                    .filter(x -> lobValList.contains(x.getLob()))
                                    .collect(Collectors.toList()));
                        }
                    }

                }
            }


        } else {
            throw new BoeException("FromChaCode is null please check", boeResponse, HttpStatus.OK);
        }
        boeResponse.setData(chaReassignmentPageData);
        return boeResponse;
    }

    private ChaAgentList getChaAgentObject(ChaAgents chaAgents) {
        ChaAgentList agentList = new ChaAgentList();
        agentList.setChaCode(chaAgents.getChaCode());
        agentList.setChaName(chaAgents.getChaName());
        return agentList;
    }


    private ChaReassignmentPageData fetchData(FetchChaReassignmentPageDataRequest fetchChaReassignmentPageDataRequest, List<Integer> stageList) {
        log.info("Inside class :: ChaReAssignmentServiceImpl :: method :: fetchData :: ");
        ChaReassignmentPageData chaReassignmentPageData = new ChaReassignmentPageData();
        List<ChaReAssgnmtData> chaReAssignmentList = this.btvlWkfBoeShipmentTblRepository.fetchChaReAssignmentPageData(fetchChaReassignmentPageDataRequest.getFromChaCode());
        log.info("Inside class :: ChaReAssignmentServiceImpl :: method :: fetchData :: chaReAssignmentList from DB " + chaReAssignmentList);
        if (chaReAssignmentList != null) {
            if ("Assignment Failed".equalsIgnoreCase(fetchChaReassignmentPageDataRequest.getFromChaCode())) {
                log.info("Inside class :: ChaReAssignmentServiceImpl :: method :: fetchData :: processesing request for Assignment Failed");
                chaReassignmentPageData.setAssignmentFailedList(chaReAssignmentList.stream().map(this::getChaDataObject).collect(Collectors.toList()));

            } else {
                log.info("Inside class :: ChaReAssignmentServiceImpl :: method :: fetchData :: processesing request for approved and not approved records ");
                chaReassignmentPageData.setApprovedList(chaReAssignmentList.stream().filter(p -> "5".equals(p.getStage())).map(this::getChaDataObject).collect(Collectors.toList()));
                chaReassignmentPageData.setNotApprovedList(chaReAssignmentList.stream().filter(p -> "3".equals(p.getStage())).map(this::getChaDataObject).collect(Collectors.toList()));
            }


        }
        return chaReassignmentPageData;
    }

    private ChaReassignmentPageDataList getChaDataObject(ChaReAssgnmtData chaAgents) {
        log.info("Inside class :: ChaReAssignmentServiceImpl :: entering method :: getChaDataObject ");
        log.info("Inside class :: ChaReAssignmentServiceImpl :: entering method :: getChaDataObject " + chaAgents.getStage());
        ChaReassignmentPageDataList dataList = new ChaReassignmentPageDataList();
        dataList.setChaAgent(chaAgents.getChaAgent());
        dataList.setOuName(chaAgents.getOuName());
        dataList.setLeName(chaAgents.getLeName());
        dataList.setStatusRef(chaAgents.getStatusRef());
        dataList.setPortName(chaAgents.getPortName());
        dataList.setShipmentId(chaAgents.getShipmentId());
        dataList.setStatus(chaAgents.getStatus());
        dataList.setAssignedChaId(chaAgents.getAssignedChaId());
        dataList.setStage(chaAgents.getStage());
        dataList.setBucketNo(chaAgents.getBucketNo());
        dataList.setLob(chaAgents.getLob());
        dataList.setInvoiceNumber(chaAgents.getInvoiceNumber());
        dataList.setDestinationPort(chaAgents.getDestinationPort());
        return dataList;
    }

    private String getLoggedInAirtelUserSCMLob(String olm, Integer empRoleId, BoeResponse<ChaReassignmentPageData> boeResponse) {
        log.info("Inside method :: getLoggedInAirtelUserSCMLob");
        List<BTVL_EMP_ROLE_MAPPING_TBL> btvlEmpRoleMappingTblList = this.btvlEmpRoleMappingTblRepository.findByEmpOlmIdAndEmpRoleIdAllIgnoreCase(olm, empRoleId);
        log.info("Olm id :: " + btvlEmpRoleMappingTblList.get(0).getEmpOlmId() + " Lob's :: " + btvlEmpRoleMappingTblList.get(0).getLob());
        return btvlEmpRoleMappingTblList.get(0).getLob().toUpperCase();
    }

    private void setErrorObject(BoeResponse<ChaReassignmentPageData> boeResponse) {
        Error error = new Error();
        error.setCode(CommonConstants.SCM_DASHBOARD_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }


}
