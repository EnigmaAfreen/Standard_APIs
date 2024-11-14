package com.airtel.buyer.airtelboe.service.cco;

import com.airtel.buyer.airtelboe.dto.ccoaction.request.CcoActionRequest;
import com.airtel.buyer.airtelboe.dto.ccoaction.response.CcoActionData;
import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.fetchcco.request.FetchCcoRequest;
import com.airtel.buyer.airtelboe.dto.fetchcco.response.CcoFilter;
import com.airtel.buyer.airtelboe.dto.fetchcco.response.CcoRecord;
import com.airtel.buyer.airtelboe.dto.fetchcco.response.FetchCcoData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.repository.Cco;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipDocTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipmentTblRepository;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import com.airtel.buyer.airtelboe.util.EmailUtil;
import com.airtel.buyer.airtelboe.util.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CcoServiceImpl implements CcoService {

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlWkfBoeShipDocTblRepository btvlWkfBoeShipDocTblRepository;

    @Autowired
    private CcoTransactionService ccoTransactionService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("UPLOAD_TMP_DIRECTORY")
    private String uploadTmpDir;

    @Value("${airtel.mailBody.emailApprovalURL}")
    private String emailApprovalURL;

    @Value("${app.node}")
    public String appNode;

    @Override
    public BoeResponse<FetchCcoData> ccoInformation(FetchCcoRequest fetchCcoRequest, int page, int size, UserDetailsImpl userDetails) {

        BoeResponse<FetchCcoData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchCcoDataObject(fetchCcoRequest, page, size, userDetails.getUsername()));

        return boeResponse;
    }

    private FetchCcoData getFetchCcoDataObject(FetchCcoRequest fetchCcoRequest, int page, int size, String olmId) {

        FetchCcoData fetchCcoData = new FetchCcoData();
        fetchCcoData.setCcoFilter(this.getCcoFilterObject(fetchCcoRequest));

        this.getOverAllBucketData(fetchCcoRequest, page, size, fetchCcoData, olmId);
        this.getOnHoldBucketData(fetchCcoRequest, page, size, fetchCcoData, olmId);

        return fetchCcoData;
    }

    private CcoFilter getCcoFilterObject(FetchCcoRequest fetchCcoRequest) {

        CcoFilter ccoFilter = new CcoFilter();
        ccoFilter.setBinNo(fetchCcoRequest.getBinNo());
        ccoFilter.setOu(fetchCcoRequest.getOu());
        ccoFilter.setClearanceType(fetchCcoRequest.getClearanceType());
        ccoFilter.setShipmentId(fetchCcoRequest.getShipmentId());
        ccoFilter.setValidFrom(fetchCcoRequest.getValidFrom());
        ccoFilter.setValidTo(fetchCcoRequest.getValidTo());

        return ccoFilter;
    }

    private void getOverAllBucketData(FetchCcoRequest fetchCcoRequest,
                                      int page, int size, FetchCcoData fetchCcoData, String olmId) {

        Page<Cco> resultPage = this.getRecord(fetchCcoRequest, page, size, new BigDecimal(18), olmId);

        if (fetchCcoRequest.getBinNo() == 1) {

            List<Cco> dbList = resultPage.getContent();

            if (dbList != null && !dbList.isEmpty()) {

                fetchCcoData.setOverAllBucketList(
                        dbList.stream().map(this::getCcoRecordObject).collect(Collectors.toList())
                );
            }
        }

        fetchCcoData.setOverAllBucketCount((int) resultPage.getTotalElements());

    }

    private void getOnHoldBucketData(FetchCcoRequest fetchCcoRequest,
                                     int page, int size, FetchCcoData fetchCcoData, String olmId) {

        Page<Cco> resultPage = this.getRecord(fetchCcoRequest, page, size, new BigDecimal(19), olmId);

        if (fetchCcoRequest.getBinNo() == 2) {

            List<Cco> dbList = resultPage.getContent();

            if (dbList != null && !dbList.isEmpty()) {

                fetchCcoData.setOverAllBucketList(
                        dbList.stream().map(this::getCcoRecordObject).collect(Collectors.toList())
                );
            }
        }

        fetchCcoData.setOnHoldBucketCount((int) resultPage.getTotalElements());

    }

    private Page<Cco> getRecord(FetchCcoRequest fetchCcoRequest, int page, int size, BigDecimal bucketNo, String olmId) {

        Page<Cco> resultPage = this.btvlWkfBoeShipmentTblRepository.fetchCcoRecords(
                bucketNo,
                fetchCcoRequest.getShipmentId(),
                fetchCcoRequest.getOu(),
                fetchCcoRequest.getClearanceType(),
                fetchCcoRequest.getValidFrom(),
                fetchCcoRequest.getValidTo(),
                olmId,
                PageRequest.of(page - 1, size)
        );

        return resultPage;
    }

    private CcoRecord getCcoRecordObject(Cco Cco) {

        CcoRecord CcoRecord = new CcoRecord();
        CcoRecord.setOuName(Cco.getOuName());
        CcoRecord.setBoeNo(Cco.getBoeNo());
        CcoRecord.setBoeDate(Cco.getBoeDate());
        CcoRecord.setShipmentId(Cco.getShipmentId());
        CcoRecord.setPoNo(Cco.getPoNo());
        CcoRecord.setInvoiceNo(Cco.getInvoiceNumbers());
        CcoRecord.setChaName(Cco.getChaName());
        CcoRecord.setModeOfShipment(Cco.getShipmentMode());
        CcoRecord.setShipmentArrivalDate(Cco.getShipmentArrivalDate());
        CcoRecord.setPartnerCode(Cco.getPartnerVendorCode());
        CcoRecord.setPartnerName(Cco.getVendorName());
        CcoRecord.setStatus(Cco.getStatus());
        CcoRecord.setDutyAmount(Cco.getDutyAmount());
//        CcoRecord.setCcoDocList(this.getCcoDocListObject(Cco.getShipmentId()));

        return CcoRecord;
    }

//    private List<CcoDoc> getCcoDocListObject(BigDecimal shipmentId) {
//
//        List<CcoDoc> CcoDocList = null;
//
//        List<ShipDoc> list = this.btvlWkfBoeShipDocTblRepository.fetchShipmentDocByShipmentId(shipmentId);
//
//        if (list != null && !list.isEmpty()) {
//            CcoDocList = list.stream().map(this::getCcoDocObject).collect(Collectors.toList());
//        }
//
//        return CcoDocList;
//    }
//
//    private CcoDoc getCcoDocObject(ShipDoc shipDoc) {
//
//        CcoDoc CcoDoc = new CcoDoc();
//        CcoDoc.setDocName(shipDoc.getDocName());
//        CcoDoc.setContentId(shipDoc.getContentId());
//
//        return CcoDoc;
//    }

    @Override
    public BoeResponse<CcoActionData> ccoApprove(CcoActionRequest ccoActionRequest, UserDetailsImpl userDetails) {

        BoeResponse<CcoActionData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getCcoActionDataObject(ccoActionRequest));

        if (this.checkMandatoryFields(ccoActionRequest, boeResponse, CommonConstants.CCO_APPROVE_ERROR_CODE)) {
            this.approve(ccoActionRequest, userDetails);
        }

        return boeResponse;
    }

    private CcoActionData getCcoActionDataObject(CcoActionRequest ccoActionRequest) {

        CcoActionData ccoActionData = new CcoActionData();
        ccoActionData.setShipmentId(ccoActionRequest.getShipmentId());
        ccoActionData.setComment(ccoActionRequest.getComment());
        ccoActionData.setChaName(ccoActionRequest.getChaName());

        return ccoActionData;
    }

    private Boolean checkMandatoryFields(CcoActionRequest ccoActionRequest,
                                         BoeResponse<CcoActionData> boeResponse,
                                         String errorCode) {

        Boolean isValidationPassed = Boolean.TRUE;

        CcoActionData ccoActionData = boeResponse.getData();

        if (ccoActionRequest.getShipmentId() == null ||
                StringUtils.isBlank(ccoActionRequest.getShipmentId().toString())) {
            isValidationPassed = Boolean.FALSE;
            ccoActionData.setShipmentIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(ccoActionRequest.getComment())) {
            isValidationPassed = Boolean.FALSE;
            ccoActionData.setCommentErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("CcoServiceImpl :: method :: checkMandatoryFields :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, errorCode);
        }

        return isValidationPassed;
    }

    private void setErrorObject(BoeResponse boeResponse, String code) {
        com.airtel.buyer.airtelboe.dto.common.Error error = new Error();
        error.setCode(code);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }

    private void approve(CcoActionRequest ccoActionRequest, UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                this.btvlWkfBoeShipmentTblRepository.findByShipmentId(ccoActionRequest.getShipmentId());

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            if (this.isValidAction(bTVL_WKF_BOE_SHIPMENT_TBL, 18, userDetails.getUsername())) {
                this.ccoTransactionService.approveAction(ccoActionRequest, bTVL_WKF_BOE_SHIPMENT_TBL, userDetails);
            } else {
                log.info("Not a valid approve action for Shipment Id :: " + ccoActionRequest.getShipmentId());
                throw new BoeException("No record found", null, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + ccoActionRequest.getShipmentId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

    }

    private Boolean isValidAction(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL, Integer validBucketNo, String olmId) {

        Boolean isValid = Boolean.FALSE;

        Integer dbBucketNo = bTVL_WKF_BOE_SHIPMENT_TBL.getBucketNo().intValue();

        if (validBucketNo == dbBucketNo && olmId.equalsIgnoreCase(bTVL_WKF_BOE_SHIPMENT_TBL.getAssignedCcoId())) {
            isValid = Boolean.TRUE;
        }

        log.info("CcoServiceImpl :: method :: isValidAction :: " + bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId() + " :: " + isValid);
        return isValid;
    }

    @Override
    public BoeResponse<CcoActionData> ccoReject(CcoActionRequest ccoActionRequest, UserDetailsImpl userDetails) {

        BoeResponse<CcoActionData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getCcoActionDataObject(ccoActionRequest));

        if (this.checkMandatoryFields(ccoActionRequest, boeResponse, CommonConstants.CCO_REJECT_ERROR_CODE)) {
            this.reject(ccoActionRequest, userDetails);
        }

        return boeResponse;
    }

    private void reject(CcoActionRequest ccoActionRequest, UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                this.btvlWkfBoeShipmentTblRepository.findByShipmentId(ccoActionRequest.getShipmentId());

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            if (this.isValidAction(bTVL_WKF_BOE_SHIPMENT_TBL, 18, userDetails.getUsername())) {
                this.ccoTransactionService.rejectAction(ccoActionRequest, bTVL_WKF_BOE_SHIPMENT_TBL, userDetails);
            } else {
                log.info("Not a valid reject action for Shipment Id :: " + ccoActionRequest.getShipmentId());
                throw new BoeException("No record found", null, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + ccoActionRequest.getShipmentId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public BoeResponse<CcoActionData> ccoHold(CcoActionRequest ccoActionRequest, UserDetailsImpl userDetails) {

        BoeResponse<CcoActionData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getCcoActionDataObject(ccoActionRequest));

        if (this.checkMandatoryFields(ccoActionRequest, boeResponse, CommonConstants.CCO_HOLD_ERROR_CODE)) {
            this.hold(ccoActionRequest, userDetails);
        }

        return boeResponse;
    }

    private void hold(CcoActionRequest ccoActionRequest, UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                this.btvlWkfBoeShipmentTblRepository.findByShipmentId(ccoActionRequest.getShipmentId());

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            if (this.isValidAction(bTVL_WKF_BOE_SHIPMENT_TBL, 18, userDetails.getUsername())) {
                this.ccoTransactionService.holdAction(ccoActionRequest, bTVL_WKF_BOE_SHIPMENT_TBL, userDetails);
            } else {
                log.info("Not a valid hold action for Shipment Id :: " + ccoActionRequest.getShipmentId());
                throw new BoeException("No record found", null, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + ccoActionRequest.getShipmentId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public BoeResponse<CcoActionData> ccoUnHold(CcoActionRequest ccoActionRequest, UserDetailsImpl userDetails) {

        BoeResponse<CcoActionData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getCcoActionDataObject(ccoActionRequest));

        if (this.checkMandatoryFieldsForUnHold(ccoActionRequest, boeResponse)) {
            this.unHold(ccoActionRequest, userDetails);
        }

        return boeResponse;
    }

    private Boolean checkMandatoryFieldsForUnHold(CcoActionRequest ccoActionRequest,
                                                  BoeResponse<CcoActionData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        CcoActionData ccoActionData = boeResponse.getData();

        if (ccoActionRequest.getShipmentId() == null ||
                StringUtils.isBlank(ccoActionRequest.getShipmentId().toString())) {
            isValidationPassed = Boolean.FALSE;
            ccoActionData.setShipmentIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(ccoActionRequest.getChaName())) {
            isValidationPassed = Boolean.FALSE;
            ccoActionData.setChaNameErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("CcoServiceImpl :: method :: checkMandatoryFieldsForUnHold :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.CCO_UNHOLD_ERROR_CODE);
        }

        return isValidationPassed;
    }

    private void unHold(CcoActionRequest ccoActionRequest, UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                this.btvlWkfBoeShipmentTblRepository.findByShipmentId(ccoActionRequest.getShipmentId());

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            if (this.isValidAction(bTVL_WKF_BOE_SHIPMENT_TBL, 19, userDetails.getUsername())) {


                this.ccoTransactionService.unHoldAction(ccoActionRequest, bTVL_WKF_BOE_SHIPMENT_TBL, userDetails);

                this.sendMailToCoc(bTVL_WKF_BOE_SHIPMENT_TBL, ccoActionRequest.getChaName());

            } else {
                log.info("Not a valid unHold action for Shipment Id :: " + ccoActionRequest.getShipmentId());
                throw new BoeException("No record found", null, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + ccoActionRequest.getShipmentId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

    }

    private void sendMailToCoc(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL, String chaName) {

        String to = bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute5();
        String from = CommonConstants.AIRTEL_BOE_FROM_EMAIL;

        String subject = String.format(CommonConstants.BOE_SHIPMENT_SUBMIT_MAIL_SUBJECT_CCO,
                bTVL_WKF_BOE_SHIPMENT_TBL.getBoeNo(), bTVL_WKF_BOE_SHIPMENT_TBL.getPoNo());

        String body = BoeUtils.getCCoActionMailBody(bTVL_WKF_BOE_SHIPMENT_TBL, chaName, this.emailApprovalURL);

        log.info("---Sending mail---");
        log.info("To :: " + to);
        log.info("from :: " + from);
        log.info("subject :: " + subject);
        log.info("body :: " + body);
        EmailUtil.sendEmail(this.javaMailSender, to, null, from, subject, body);

    }

    @Override
    public void ccoExcel(FetchCcoRequest fetchCcoRequest, UserDetailsImpl userDetails) {

        Page<Cco> resultPage = null;
        String excelName = null;

        if (fetchCcoRequest.getBinNo() == 2) {
            resultPage = getRecord(fetchCcoRequest, 1, 1000, new BigDecimal(19), userDetails.getUsername());
            excelName = "CCOPutOnHoldReport";
        } else {
            resultPage = getRecord(fetchCcoRequest, 1, 1000, new BigDecimal(18), userDetails.getUsername());
            excelName = "CCOPendingApprovalReport";
        }

        List<Cco> ccoList = resultPage.getContent();

        List<String> headerTextList = this.getExcelHeaders();
        List<Map<String, String>> rowMapList = this.getExcelRowsData(ccoList);

        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("CCO Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", null, HttpStatus.OK);
        }

    }

    private List<String> getExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();
        headerTextList.add("OU NAME");
        headerTextList.add("BOE NO.");
        headerTextList.add("BOE DATE");
        headerTextList.add("AIRTEL BOE REF NO.");
        headerTextList.add("PO NO.");
        headerTextList.add("INVOICE NO.");
        headerTextList.add("CHA NAME");
        headerTextList.add("MODE OF SHIPMENT");
        headerTextList.add("SHIPMENT ARRIVAL DATE");
        headerTextList.add("PARTNER CODE");
        headerTextList.add("PARTNER NAME");
        headerTextList.add("STATUS");

        return headerTextList;
    }

    private List<Map<String, String>> getExcelRowsData(List<Cco> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (Cco row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("OU NAME", row.getOuName());
            map.put("BOE NO.", row.getBoeNo());
            map.put("BOE DATE", row.getBoeDate() != null ? row.getBoeDate().toString() : null);
            map.put("AIRTEL BOE REF NO.", String.valueOf(row.getShipmentId()));
            map.put("PO NO.", row.getPoNo());
            map.put("INVOICE NO.", row.getInvoiceNumbers());
            map.put("CHA NAME", row.getChaName());
            map.put("MODE OF SHIPMENT", row.getShipmentMode());
            map.put("SHIPMENT ARRIVAL DATE", row.getShipmentArrivalDate() != null ? row.getShipmentArrivalDate().toString() : null);
            map.put("PARTNER CODE", row.getPartnerVendorCode() != null ? row.getPartnerVendorCode().toString() : null);
            map.put("PARTNER NAME", row.getVendorName());
            map.put("STATUS", row.getStatusDesc());

            rowMapList.add(map);
        }

        return rowMapList;
    }

}
