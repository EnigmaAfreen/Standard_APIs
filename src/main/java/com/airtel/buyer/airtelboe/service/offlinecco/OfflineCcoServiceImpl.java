package com.airtel.buyer.airtelboe.service.offlinecco;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.fetchofflinecco.request.FetchOfflineCcoRequest;
import com.airtel.buyer.airtelboe.dto.fetchofflinecco.response.FetchOfflineCcoData;
import com.airtel.buyer.airtelboe.dto.fetchofflinecco.response.OfflineCcoFilter;
import com.airtel.buyer.airtelboe.dto.fetchofflinecco.response.OfflineCcoRecord;
import com.airtel.buyer.airtelboe.dto.offlineccoapproval.request.OfflineCcoApprovalRequest;
import com.airtel.buyer.airtelboe.dto.offlineccoapproval.response.OfflineCcoApprovalData;
import com.airtel.buyer.airtelboe.dto.offlineccoretrigger.request.OfflineCcoRetriggerRequest;
import com.airtel.buyer.airtelboe.dto.offlineccoretrigger.response.OfflineCcoRetriggerData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.repository.OfflineCco;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OfflineCcoServiceImpl implements OfflineCcoService {

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlWkfBoeShipDocTblRepository btvlWkfBoeShipDocTblRepository;

    @Autowired
    private OfflineCcoTransactionService offlineCcoTransactionService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${airtel.mailBody.emailApprovalURL}")
    private String emailApprovalURL;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("UPLOAD_TMP_DIRECTORY")
    private String uploadTmpDir;

    @Value("${app.node}")
    public String appNode;

    @Override
    public BoeResponse<FetchOfflineCcoData> offlineCcoInformation(FetchOfflineCcoRequest fetchOfflineCcoRequest, int page, int size) {

        BoeResponse<FetchOfflineCcoData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchOfflineCcoDataObject(fetchOfflineCcoRequest, page, size));

        return boeResponse;
    }

    private FetchOfflineCcoData getFetchOfflineCcoDataObject(FetchOfflineCcoRequest fetchOfflineCcoRequest, int page, int size) {

        FetchOfflineCcoData fetchOfflineCcoData = new FetchOfflineCcoData();
        fetchOfflineCcoData.setOfflineCcoFilter(this.getOfflineCcoFilterObject(fetchOfflineCcoRequest));

        this.getOverAllBucketData(fetchOfflineCcoRequest, page, size, fetchOfflineCcoData);
        this.getOnHoldBucketData(fetchOfflineCcoRequest, page, size, fetchOfflineCcoData);

        return fetchOfflineCcoData;
    }

    private OfflineCcoFilter getOfflineCcoFilterObject(FetchOfflineCcoRequest fetchOfflineCcoRequest) {

        OfflineCcoFilter offlineCcoFilter = new OfflineCcoFilter();
        offlineCcoFilter.setBinNo(fetchOfflineCcoRequest.getBinNo());
        offlineCcoFilter.setOu(fetchOfflineCcoRequest.getOu());
        offlineCcoFilter.setClearanceType(fetchOfflineCcoRequest.getClearanceType());
        offlineCcoFilter.setShipmentId(fetchOfflineCcoRequest.getShipmentId());
        offlineCcoFilter.setValidFrom(fetchOfflineCcoRequest.getValidFrom());
        offlineCcoFilter.setValidTo(fetchOfflineCcoRequest.getValidTo());

        return offlineCcoFilter;
    }

    private void getOverAllBucketData(FetchOfflineCcoRequest fetchOfflineCcoRequest,
                                      int page, int size, FetchOfflineCcoData fetchOfflineCcoData) {

        Page<OfflineCco> resultPage = this.getRecord(fetchOfflineCcoRequest, page, size, "18,20");

        if (fetchOfflineCcoRequest.getBinNo() == 1) {

            List<OfflineCco> dbList = resultPage.getContent();

            if (dbList != null && !dbList.isEmpty()) {

                fetchOfflineCcoData.setOverAllBucketList(
                        dbList.stream().map(this::getOfflineCcoRecordObject).collect(Collectors.toList())
                );
            }
        }

        fetchOfflineCcoData.setOverAllBucketCount((int) resultPage.getTotalElements());

    }

    private void getOnHoldBucketData(FetchOfflineCcoRequest fetchOfflineCcoRequest,
                                     int page, int size, FetchOfflineCcoData fetchOfflineCcoData) {

        Page<OfflineCco> resultPage = this.getRecord(fetchOfflineCcoRequest, page, size, "19");

        if (fetchOfflineCcoRequest.getBinNo() == 2) {

            List<OfflineCco> dbList = resultPage.getContent();

            if (dbList != null && !dbList.isEmpty()) {

                fetchOfflineCcoData.setOverAllBucketList(
                        dbList.stream().map(this::getOfflineCcoRecordObject).collect(Collectors.toList())
                );
            }
        }

        fetchOfflineCcoData.setOnHoldBucketCount((int) resultPage.getTotalElements());

    }

    private Page<OfflineCco> getRecord(FetchOfflineCcoRequest fetchOfflineCcoRequest, int page, int size, String bucketNo) {

        Page<OfflineCco> resultPage = this.btvlWkfBoeShipmentTblRepository.fetchOfflineCcoRecords(
                bucketNo,
                fetchOfflineCcoRequest.getShipmentId(),
                fetchOfflineCcoRequest.getOu(),
                fetchOfflineCcoRequest.getClearanceType(),
                fetchOfflineCcoRequest.getValidFrom(),
                fetchOfflineCcoRequest.getValidTo(),
                PageRequest.of(page - 1, size)
        );

        return resultPage;
    }

    private OfflineCcoRecord getOfflineCcoRecordObject(OfflineCco offlineCco) {

        OfflineCcoRecord offlineCcoRecord = new OfflineCcoRecord();
        offlineCcoRecord.setOuName(offlineCco.getOuName());
        offlineCcoRecord.setBoeNo(offlineCco.getBoeNo());
        offlineCcoRecord.setBoeDate(offlineCco.getBoeDate());
        offlineCcoRecord.setShipmentId(offlineCco.getShipmentId());
        offlineCcoRecord.setPoNo(offlineCco.getPoNo());
        offlineCcoRecord.setInvoiceNo(offlineCco.getInvoiceNumbers());
        offlineCcoRecord.setChaName(offlineCco.getChaName());
        offlineCcoRecord.setModeOfShipment(offlineCco.getShipmentMode());
        offlineCcoRecord.setShipmentArrivalDate(offlineCco.getShipmentArrivalDate());
        offlineCcoRecord.setPartnerCode(offlineCco.getPartnerVendorCode());
        offlineCcoRecord.setPartnerName(offlineCco.getVendorName());
        offlineCcoRecord.setStatus(offlineCco.getStatus());
        offlineCcoRecord.setDutyAmount(offlineCco.getDutyAmount());
//        offlineCcoRecord.setOfflineCcoDocList(this.getOfflineCcoDocListObject(offlineCco.getShipmentId()));

        return offlineCcoRecord;
    }

//    private List<OfflineCcoDoc> getOfflineCcoDocListObject(BigDecimal shipmentId) {
//
//        List<OfflineCcoDoc> offlineCcoDocList = null;
//
//        List<ShipDoc> list = this.btvlWkfBoeShipDocTblRepository.fetchShipmentDocByShipmentId(shipmentId);
//
//        if (list != null && !list.isEmpty()) {
//            offlineCcoDocList = list.stream().map(this::getOfflineCcoDocObject).collect(Collectors.toList());
//        }
//
//        return offlineCcoDocList;
//    }
//
//    private OfflineCcoDoc getOfflineCcoDocObject(ShipDoc shipDoc) {
//
//        OfflineCcoDoc offlineCcoDoc = new OfflineCcoDoc();
//        offlineCcoDoc.setDocName(shipDoc.getDocName());
//        offlineCcoDoc.setContentId(shipDoc.getContentId());
//
//        return offlineCcoDoc;
//    }

    @Override
    public BoeResponse<OfflineCcoApprovalData> offlineCcoApprove(OfflineCcoApprovalRequest offlineCcoApprovalRequest, UserDetailsImpl userDetails) {

        BoeResponse<OfflineCcoApprovalData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getOfflineCcoApprovalDataObject(offlineCcoApprovalRequest));

        if (this.checkMandatoryFieldsForApprove(offlineCcoApprovalRequest, boeResponse)) {
            this.approve(offlineCcoApprovalRequest, userDetails);
        }

        return boeResponse;
    }

    private OfflineCcoApprovalData getOfflineCcoApprovalDataObject(OfflineCcoApprovalRequest offlineCcoApprovalRequest) {

        OfflineCcoApprovalData offlineCcoApprovalData = new OfflineCcoApprovalData();
        offlineCcoApprovalData.setShipmentId(offlineCcoApprovalRequest.getShipmentId());
        offlineCcoApprovalData.setApproveComment(offlineCcoApprovalRequest.getApproveComment());
        offlineCcoApprovalData.setOfflineCcoAttachment1DocPath(offlineCcoApprovalRequest.getOfflineCcoAttachment1DocPath());
        offlineCcoApprovalData.setOfflineCcoAttachment2DocPath(offlineCcoApprovalRequest.getOfflineCcoAttachment2DocPath());
        offlineCcoApprovalData.setOfflineCcoAttachment3DocPath(offlineCcoApprovalRequest.getOfflineCcoAttachment3DocPath());
        offlineCcoApprovalData.setOfflineCcoAttachment4DocPath(offlineCcoApprovalRequest.getOfflineCcoAttachment4DocPath());

        return offlineCcoApprovalData;
    }

    private Boolean checkMandatoryFieldsForApprove(OfflineCcoApprovalRequest offlineCcoApprovalRequest,
                                                   BoeResponse<OfflineCcoApprovalData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        OfflineCcoApprovalData offlineCcoApprovalData = boeResponse.getData();

        if (offlineCcoApprovalRequest.getShipmentId() == null ||
                StringUtils.isBlank(offlineCcoApprovalRequest.getShipmentId().toString())) {
            isValidationPassed = Boolean.FALSE;
            offlineCcoApprovalData.setShipmentIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(offlineCcoApprovalRequest.getApproveComment())) {
            isValidationPassed = Boolean.FALSE;
            offlineCcoApprovalData.setApproveCommentErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(offlineCcoApprovalRequest.getOfflineCcoAttachment1DocPath())) {
            isValidationPassed = Boolean.FALSE;
            offlineCcoApprovalData.setOfflineCcoAttachment1DocPathErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("OfflineCcoServiceImpl :: method :: checkMandatoryFieldsForApprove :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.OFFLINE_CCO_APPROVE_ERROR_CODE);
        }

        return isValidationPassed;
    }

    private void setErrorObject(BoeResponse boeResponse, String code) {
        com.airtel.buyer.airtelboe.dto.common.Error error = new Error();
        error.setCode(code);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }

    private void approve(OfflineCcoApprovalRequest offlineCcoApprovalRequest, UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                this.btvlWkfBoeShipmentTblRepository.findByShipmentId(offlineCcoApprovalRequest.getShipmentId());

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            if (this.isValidAction(bTVL_WKF_BOE_SHIPMENT_TBL, Arrays.asList(18, 20))) {
                this.offlineCcoTransactionService.approveAction(offlineCcoApprovalRequest, bTVL_WKF_BOE_SHIPMENT_TBL, userDetails);
            } else {
                log.info("Not a valid approve action for Shipment Id :: " + offlineCcoApprovalRequest.getShipmentId());
                throw new BoeException("No record found", null, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + offlineCcoApprovalRequest.getShipmentId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

    }

    private Boolean isValidAction(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL, List<Integer> validActionList) {

        Boolean isValid = Boolean.FALSE;

        Integer bucketNo = bTVL_WKF_BOE_SHIPMENT_TBL.getBucketNo().intValue();

        if (validActionList.contains(bucketNo)) {
            isValid = Boolean.TRUE;
        }

        log.info("OfflineCcoServiceImpl :: method :: isValidAction :: " + bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId() + " :: " + isValid);
        return isValid;
    }

    @Override
    public BoeResponse<OfflineCcoRetriggerData> offlineCcoRetrigger(OfflineCcoRetriggerRequest offlineCcoRetriggerRequest, UserDetailsImpl userDetails) {

        BoeResponse<OfflineCcoRetriggerData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getOfflineCcoRetriggerDataObject(offlineCcoRetriggerRequest));

        if (this.checkMandatoryFieldsForRetrigger(offlineCcoRetriggerRequest, boeResponse)) {
            this.retigger(offlineCcoRetriggerRequest, userDetails);
        }

        return boeResponse;
    }

    private OfflineCcoRetriggerData getOfflineCcoRetriggerDataObject(OfflineCcoRetriggerRequest offlineCcoRetriggerRequest) {

        OfflineCcoRetriggerData offlineCcoRetriggerData = new OfflineCcoRetriggerData();
        offlineCcoRetriggerData.setShipmentId(offlineCcoRetriggerRequest.getShipmentId());
        offlineCcoRetriggerData.setChaName(offlineCcoRetriggerRequest.getChaName());

        return offlineCcoRetriggerData;
    }

    private Boolean checkMandatoryFieldsForRetrigger(OfflineCcoRetriggerRequest offlineCcoRetriggerRequest,
                                                     BoeResponse<OfflineCcoRetriggerData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        OfflineCcoRetriggerData offlineCcoRetriggerData = boeResponse.getData();

        if (offlineCcoRetriggerRequest.getShipmentId() == null ||
                StringUtils.isBlank(offlineCcoRetriggerRequest.getShipmentId().toString())) {
            isValidationPassed = Boolean.FALSE;
            offlineCcoRetriggerData.setShipmentIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(offlineCcoRetriggerRequest.getChaName())) {
            isValidationPassed = Boolean.FALSE;
            offlineCcoRetriggerData.setChaNameErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("OfflineCcoServiceImpl :: method :: checkMandatoryFieldsForRetrigger :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.OFFLINE_CCO_RETRIGGER_ERROR_CODE);
        }

        return isValidationPassed;
    }

    private void retigger(OfflineCcoRetriggerRequest offlineCcoRetriggerRequest, UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                this.btvlWkfBoeShipmentTblRepository.findByShipmentId(offlineCcoRetriggerRequest.getShipmentId());

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            if (this.isValidAction(bTVL_WKF_BOE_SHIPMENT_TBL, Arrays.asList(18, 20))) {

                this.offlineCcoTransactionService.retiggerAction(offlineCcoRetriggerRequest, bTVL_WKF_BOE_SHIPMENT_TBL, userDetails);

                if (!"CCO ASSIGNMENT FAILED".equalsIgnoreCase(bTVL_WKF_BOE_SHIPMENT_TBL.getAssignedCcoId())) {
                    this.sendMailToCoc(bTVL_WKF_BOE_SHIPMENT_TBL, offlineCcoRetriggerRequest.getChaName());
                }

            } else {
                log.info("Not a valid retrigger action for Shipment Id :: " + offlineCcoRetriggerRequest.getShipmentId());
                throw new BoeException("No record found", null, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + offlineCcoRetriggerRequest.getShipmentId());
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
    public void offlineCcoExcel(FetchOfflineCcoRequest fetchOfflineCcoRequest) {

        Page<OfflineCco> resultPage = null;
        String excelName = null;

        if (fetchOfflineCcoRequest.getBinNo() == 2) {
            resultPage = getRecord(fetchOfflineCcoRequest, 1, 1000, "19");
            excelName = "OfflineCCOPutOnHold";
        } else {
            resultPage = getRecord(fetchOfflineCcoRequest, 1, 1000, "18,20");
            excelName = "OfflineCCOPendingApprovalReport";
        }

        List<OfflineCco> offlineCcoList = resultPage.getContent();

        List<String> headerTextList = this.getExcelHeaders();
        List<Map<String, String>> rowMapList = this.getExcelRowsData(offlineCcoList);

        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("Port Master Excel Generated successfully");
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

    private List<Map<String, String>> getExcelRowsData(List<OfflineCco> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (OfflineCco row : excelData) {
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
