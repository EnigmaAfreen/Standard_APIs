package com.airtel.buyer.airtelboe.service.WpcInfo;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.wpcinfo.request.WpcInfoRequest;
import com.airtel.buyer.airtelboe.dto.wpcinfo.response.WpcInfoDashRecords;
import com.airtel.buyer.airtelboe.dto.wpcinfo.response.WpcInfoFilterResponse;
import com.airtel.buyer.airtelboe.dto.wpcinfo.response.WpcInfoResponse;
import com.airtel.buyer.airtelboe.dto.wpcsubmitted.request.WpcSubmitRequest;
import com.airtel.buyer.airtelboe.dto.wpcsubmitted.response.WpcSubmitResponse;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_EMP_ROLE_MAPPING_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.repository.WpcInfo;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlEmpRoleMappingTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipmentTblRepository;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WpcInfoServiceImpl implements WpcInfoService {

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlEmpRoleMappingTblRepository btvlEmpRoleMappingTblRepository;

    @Value("${app.node}")
    public String appNode;

    @Value("${UPLOAD_TMP_DIRECTORY}")
    public String uploadTmpDir;

    @Override
    public BoeResponse<WpcInfoResponse> getWpcInfoDashData(int page, int size, String olm, String role, WpcInfoRequest wpcInfoRequest) {
        BoeResponse<WpcInfoResponse> boeResponse = new BoeResponse<>();
        WpcInfoResponse wpcInfoResponse = new WpcInfoResponse();
        wpcInfoResponse.setWpcInfoFilterResponse(this.getFilterResponseData(wpcInfoRequest));

        if (!this.checkMandatoryFields(wpcInfoRequest, wpcInfoResponse, boeResponse)) {


            String lobVal = null;
            switch (role.toUpperCase()) {
                case "SCM_ADMIN":
                    wpcInfoResponse.setWpcInfoDashRecords(this.getBucketData(page, size, lobVal, wpcInfoRequest, boeResponse));
                    break;
                case "SCM_LOB":
                    lobVal = this.getLoggedInAirtelUserSCMLob(olm, 13, boeResponse);
                    wpcInfoResponse.setWpcInfoDashRecords(this.getBucketData(page, size, lobVal, wpcInfoRequest, boeResponse));
                    break;
                case "SCM_GVIEW":
                    lobVal = this.getLoggedInAirtelUserSCMLob(olm, 14, boeResponse);
                    wpcInfoResponse.setWpcInfoDashRecords(this.getBucketData(page, size, lobVal, wpcInfoRequest, boeResponse));
                    break;
            }

        }
        boeResponse.setData(wpcInfoResponse);
        log.info("Exit method :: getScmDashData");

        return boeResponse;
    }

    @Override
    public BoeResponse<WpcSubmitResponse> submitWpc(WpcSubmitRequest wpcSubmitRequest, UserDetailsImpl user) {

        BoeResponse<WpcSubmitResponse> boeResponse = new BoeResponse<>();
        WpcSubmitResponse wpcSubmitResponse = new WpcSubmitResponse();
        boeResponse.setData(wpcSubmitResponse);

        if (!this.checkMandatoryFieldsForSubmitWpc(wpcSubmitRequest, boeResponse)) {

            List<BTVL_WKF_BOE_SHIPMENT_TBL> btvlWkfBoeShipmentTblList = this.btvlWkfBoeShipmentTblRepository.findByShipmentIdIn(wpcSubmitRequest.getAirtelBoeRefNo());
            if (btvlWkfBoeShipmentTblList != null && !btvlWkfBoeShipmentTblList.isEmpty()) {
                for (BTVL_WKF_BOE_SHIPMENT_TBL entity : btvlWkfBoeShipmentTblList) {
                    entity.setFlag1("WPCS");
                    entity.setModifiedby(user.getUsername());
                    entity.setModifiedDate(LocalDateTime.now());
                }
                btvlWkfBoeShipmentTblRepository.saveAll(btvlWkfBoeShipmentTblList);
                wpcSubmitResponse.setStatus("success");
            }
        }
        return boeResponse;
    }

    private WpcInfoFilterResponse getFilterResponseData(WpcInfoRequest wpcInfoRequest) {

        WpcInfoFilterResponse wpcInfoFilterResponse = new WpcInfoFilterResponse();

        wpcInfoFilterResponse.setAirtelBoeRefNo(wpcInfoRequest.getAirtelBoeRefNo());
        wpcInfoFilterResponse.setInvoiceNumber(wpcInfoRequest.getInvoiceNumber());
        wpcInfoFilterResponse.setPartnerName(wpcInfoRequest.getPartnerName());
        wpcInfoFilterResponse.setFromDate(wpcInfoRequest.getFromDate());
        wpcInfoFilterResponse.setToDate(wpcInfoRequest.getToDate());


        return wpcInfoFilterResponse;
    }

    private List<WpcInfoDashRecords> getBucketData(int page, int size, String lob, WpcInfoRequest wpcInfoRequest, BoeResponse<WpcInfoResponse> boeResponse) {

//        WpcInfoResponse wpcInfoResponse = new WpcInfoResponse();
        List<WpcInfoDashRecords> wpcInfoDashRecords = new ArrayList<>();
        try {
            Pageable pageable = PageRequest.of(page - 1, size);

            Page<WpcInfo> wpcDashData = this.btvlWkfBoeShipmentTblRepository.getWpcInfoDashData(wpcInfoRequest.getAirtelBoeRefNo(),
                    wpcInfoRequest.getFromDate(), wpcInfoRequest.getToDate(),
                    wpcInfoRequest.getInvoiceNumber(), wpcInfoRequest.getPartnerName(),
                    lob, wpcInfoRequest.getOuName(), pageable);


            log.info("Records per page :: " + wpcDashData.getNumberOfElements());
            wpcInfoDashRecords = this.getRecords(wpcDashData, boeResponse, wpcInfoDashRecords);
//            wpcInfoResponse.getWpcInfoDashRecords(this.getRecords(wpcDashData, boeResponse));
        } catch (Exception e) {
            log.info("Exception raised while fetching scm dashboard data :: " + e.getMessage());
            e.printStackTrace();
            this.setErrorObject(boeResponse);
        }
        return wpcInfoDashRecords;
    }

    //    WpcInfoDashRecords
    private List<WpcInfoDashRecords> getRecords(Page<WpcInfo> wpcDashData, BoeResponse<WpcInfoResponse> boeResponse, List<WpcInfoDashRecords> wpcInfoDashRecords) {

        List<WpcInfoDashRecords> recordsList = new ArrayList<>();

        wpcDashData.stream().forEach(f -> {

            WpcInfoDashRecords records = new WpcInfoDashRecords();

            records.setOuName(f.getOuName() != null ? f.getOuName() : "");
            records.setAirtelBoeRefNo(f.getAirtelBoeRefNo() != null ? f.getAirtelBoeRefNo() : null);
            records.setBoeNo(f.getBoeNo() != null ? f.getBoeNo() : "");
            records.setBoeDate(f.getBoeDate() != null ? f.getBoeDate() : null);
            records.setPoNo(f.getPoNo() != null ? f.getPoNo() : "");
            records.setIncoTerm(f.getIncoTerm() != null ? f.getIncoTerm() : "");
            records.setShipmentArrivalDate(f.getShipmentArrivalDate() != null ? f.getShipmentArrivalDate() : null);
            records.setDestinationPort(f.getDestinationPort() != null ? f.getDestinationPort() : "");
            records.setChaAgent(f.getChaAgent() != null ? f.getChaAgent() : "");
            records.setStatus(f.getStatus() != null ? f.getStatus() : null);
            records.setStatusDesc(f.getStatusDesc() != null ? f.getStatusDesc() : "");
            records.setCreationDate(f.getCreationDate() != null ? f.getCreationDate() : null);
            records.setOrgId(f.getOrgId() != null ? f.getOrgId() : null);
            records.setLob(f.getLob() != null ? f.getLob() : "");
            records.setAssignedChaId(f.getAssignedChaId() != null ? f.getAssignedChaId() : "");
            records.setAwbBol(f.getAwbBol() != null ? f.getAwbBol() : "");
            records.setInvoiceNumbers(f.getInvoiceNumber() != null ? f.getInvoiceNumber() : "");
            records.setVendorName(f.getVendorName() != null ? f.getVendorName() : "");
            records.setShipmentMode(f.getShipmentMode() != null ? f.getShipmentMode() : "");

//            recordsList.add(records);
            wpcInfoDashRecords.add(records);
        });
        return wpcInfoDashRecords;
    }

    private void setErrorObject(BoeResponse<WpcInfoResponse> boeResponse) {
        Error error = new Error();
        error.setCode(CommonConstants.WPC_INFO_DASHBOARD_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }

    private void setErrorObjectForWpcSubmit(BoeResponse<WpcSubmitResponse> boeResponse) {
        Error error = new Error();
        error.setCode(CommonConstants.WPC_SUBMIT_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }

    private String getLoggedInAirtelUserSCMLob(String olm, Integer empRoleId, BoeResponse<WpcInfoResponse> boeResponse) {
        log.info("Inside method :: getLoggedInAirtelUserSCMLob");
        try {
            List<BTVL_EMP_ROLE_MAPPING_TBL> btvlEmpRoleMappingTblList = this.btvlEmpRoleMappingTblRepository.findByEmpOlmIdAndEmpRoleIdAllIgnoreCase(olm, empRoleId);

            log.info("Olm id :: " + btvlEmpRoleMappingTblList.get(0).getEmpOlmId() + " Lob's :: " + btvlEmpRoleMappingTblList.get(0).getLob());
            return btvlEmpRoleMappingTblList.get(0).getLob().toUpperCase();

        } catch (Exception e) {
            e.getMessage();
            log.info("Exception raised :: method :: getLoggedInAirtelUserSCMLob");
            this.setErrorObject(boeResponse);
        }
        return null;
    }

    public Boolean checkMandatoryFields(WpcInfoRequest wpcInfoRequest,
                                        WpcInfoResponse wpcInfoResponse, BoeResponse<WpcInfoResponse> boeResponse) {
        Boolean status = Boolean.FALSE;
        WpcInfoFilterResponse wpcInfoFilterResponse = wpcInfoResponse.getWpcInfoFilterResponse();

        if (wpcInfoRequest.getFromDate() != null && wpcInfoRequest.getToDate() == null) {
            status = Boolean.TRUE;
            wpcInfoFilterResponse.setToDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }
        if (wpcInfoRequest.getToDate() != null && wpcInfoRequest.getFromDate() == null) {
            status = Boolean.TRUE;
            wpcInfoFilterResponse.setFromDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }
        if (wpcInfoRequest.getFromDate() != null && wpcInfoRequest.getToDate() != null) {
            if (wpcInfoRequest.getToDate().isBefore(wpcInfoRequest.getFromDate())) {
                status = Boolean.TRUE;
                wpcInfoFilterResponse.setToDateErrMsg("To Date Should be after From Date");
            }

        }

        if (status) {
            this.setErrorObject(boeResponse);
        }
        log.info("WpcInfoServiceImpl :: inside method :: checkMandatoryFields :: status: " + status);
        return status;
    }

    public Boolean checkMandatoryFieldsForSubmitWpc(WpcSubmitRequest wpcSubmitRequest,
                                                    BoeResponse<WpcSubmitResponse> boeResponse) {
        Boolean status = Boolean.FALSE;
        WpcSubmitResponse wpcInfoFilterResponse = boeResponse.getData();

        if (wpcSubmitRequest.getAirtelBoeRefNo() == null || wpcSubmitRequest.getAirtelBoeRefNo().isEmpty()) {
            status = Boolean.TRUE;
            wpcInfoFilterResponse.setAirtelBoeRefNoErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }


        if (status) {
            this.setErrorObjectForWpcSubmit(boeResponse);
        }
        log.info("WpcInfoServiceImpl :: inside method :: checkMandatoryFields :: status: " + status);
        return status;
    }
}
