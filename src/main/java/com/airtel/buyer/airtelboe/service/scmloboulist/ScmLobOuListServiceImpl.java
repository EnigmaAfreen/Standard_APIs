package com.airtel.buyer.airtelboe.service.scmloboulist;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.scmloblist.response.ScmLobListResponse;
import com.airtel.buyer.airtelboe.dto.scmoulist.response.OuListResponseData;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_BOE_OU_LOB_MAPP_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_EMP_ROLE_MAPPING_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlBoeOuLobMappTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlEmpRoleMappingTblRepository;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ScmLobOuListServiceImpl implements ScmLobOuListService {

    @Value("${app.node}")
    public String appNode;

    @Autowired
    private BtvlBoeOuLobMappTblRepository btvlBoeOuLobMappTblRepository;

    @Autowired
    BtvlEmpRoleMappingTblRepository btvlEmpRoleMappingTblRepository;

    @Override
    public BoeResponse<List<ScmLobListResponse>> getLobNames(String olm, String role) {
        log.info("Inside class ScmLobOuListServiceImpl :: method :: getLobNames");

        BoeResponse<List<ScmLobListResponse>> boeResponse = new BoeResponse<>();
        List<String> lobListBtvlBoeOuLobMappTblRepository = this.btvlBoeOuLobMappTblRepository.findDistinctLobs();

        String lobVal = null;
        if (role.equalsIgnoreCase("SCM_LOB")) {

            lobVal = this.getLoggedInAirtelUserSCMLob(olm, 13, boeResponse);
            boeResponse.setData(Stream.of(lobVal.split(",")).filter(lobListBtvlBoeOuLobMappTblRepository::contains)
                    .map(this::setLobData)
                    .collect(Collectors.toList()));

        } else if (role.equalsIgnoreCase("SCM_GVIEW")) {

            lobVal = this.getLoggedInAirtelUserSCMLob(olm, 14, boeResponse);
            boeResponse.setData(Stream.of(lobVal.split(",")).filter(lobListBtvlBoeOuLobMappTblRepository::contains)
                    .map(this::setLobData)
                    .collect(Collectors.toList()));

        } else if (role.equalsIgnoreCase("SCM_ADMIN")) {

            boeResponse.setData(lobListBtvlBoeOuLobMappTblRepository.stream().map(this::setLobData)
                    .collect(Collectors.toList()));
        }
        log.info("Exit method :: getLobNames");
        return boeResponse;
    }

    private ScmLobListResponse setLobData(String lob) {

        ScmLobListResponse scmLobListResponse = new ScmLobListResponse();
        scmLobListResponse.setLobName(lob);
        scmLobListResponse.setLobNameValue(lob);

        return scmLobListResponse;
    }

    private String getLoggedInAirtelUserSCMLob(String olm, Integer empRoleId, BoeResponse<List<ScmLobListResponse>> boeResponse) {
        log.info("Inside method :: getLoggedInAirtelUserSCMLob");
        try {
            List<BTVL_EMP_ROLE_MAPPING_TBL> btvlEmpRoleMappingTblList = this.btvlEmpRoleMappingTblRepository.findByEmpOlmIdAndEmpRoleIdAllIgnoreCase(olm, empRoleId);

            log.info("Olm id :: " + btvlEmpRoleMappingTblList.get(0).getEmpOlmId() + " Lob's :: " + btvlEmpRoleMappingTblList.get(0).getLob());
            return btvlEmpRoleMappingTblList.get(0).getLob().toUpperCase();

        } catch (Exception e) {
            e.getMessage();
            log.info("Exception raised :: method :: getLoggedInAirtelUserSCMLob");
            this.setErrorObjectLob(boeResponse);
        }
        return null;
    }


    @Override
    public BoeResponse<List<OuListResponseData>> getOuList(String lob) {
        log.info("Inside class :: ScmLobOuListServiceImpl :: method :: getOuList");

        BoeResponse<List<OuListResponseData>> boeResponse = new BoeResponse<>();

        List<BTVL_BOE_OU_LOB_MAPP_TBL> ouLobMappTblList = btvlBoeOuLobMappTblRepository.findByLobAllIgnoreCase(lob);
        if (ouLobMappTblList != null && !ouLobMappTblList.isEmpty()) {
            List<OuListResponseData> list = new ArrayList<>();

            ouLobMappTblList.stream().forEach(f -> {
                OuListResponseData ouListResponseData = new OuListResponseData();
                ouListResponseData.setOrgId(f.getOrgId());
                ouListResponseData.setOuName(f.getOuName());

                list.add(ouListResponseData);
            });
            boeResponse.setData(list);
        } else {
            this.setErrorObjectOu(boeResponse);
        }
        log.info("Exit method :: getOuList");
        return boeResponse;
    }

    private void setErrorObjectLob(BoeResponse<List<ScmLobListResponse>> boeResponse) {
        Error error = new Error();
        error.setCode(CommonConstants.SCM_LOB_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }

    private void setErrorObjectOu(BoeResponse<List<OuListResponseData>> boeResponse) {
        Error error = new Error();
        error.setCode(CommonConstants.SCM_OU_LIST_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }
}
