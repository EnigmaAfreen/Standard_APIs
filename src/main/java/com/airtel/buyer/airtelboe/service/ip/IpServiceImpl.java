package com.airtel.buyer.airtelboe.service.ip;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.fetchip.request.FetchIpRequest;
import com.airtel.buyer.airtelboe.dto.fetchip.response.FetchIpData;
import com.airtel.buyer.airtelboe.dto.fetchip.response.IpFilter;
import com.airtel.buyer.airtelboe.dto.fetchip.response.IpRecord;
import com.airtel.buyer.airtelboe.dto.ipapproval.request.IpApprovalRequest;
import com.airtel.buyer.airtelboe.dto.ipapproval.response.IpApprovalData;
import com.airtel.buyer.airtelboe.dto.ipreject.request.IpRejectRequest;
import com.airtel.buyer.airtelboe.dto.ipreject.response.IpRejectData;
import com.airtel.buyer.airtelboe.dto.iprfi.request.IpRfiRequest;
import com.airtel.buyer.airtelboe.dto.iprfi.response.IpRfiData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_EMP_ROLE_MAPPING_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.repository.Ip;
import com.airtel.buyer.airtelboe.repository.erp.ErpDaoRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlEmpRoleMappingTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeChaTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipDocTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipmentTblRepository;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import com.airtel.buyer.airtelboe.util.EmailUtil;
import com.airtel.buyer.airtelboe.util.excel.ExcelUtil;
import com.airtel.qmt.util.QmtUtil;
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
public class IpServiceImpl implements IpService {

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlWkfBoeShipDocTblRepository btvlWkfBoeShipDocTblRepository;

    @Autowired
    private BtvlMstBoeChaTblRepository btvlMstBoeChaTblRepository;

    @Autowired
    private BtvlEmpRoleMappingTblRepository btvlEmpRoleMappingTblRepository;

    @Autowired
    private IpTransactionService ipTransactionService;

    @Autowired
    private ErpDaoRepository erpDaoRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Value("UPLOAD_TMP_DIRECTORY")
    private String uploadTmpDir;

    @Value("${SUPPLIER_LOGIN_URL}")
    public String supplierLoginUrl;

    @Value("${BUYER_LOGIN_URL}")
    public String buyerLoginUrl;


    @Value("${app.node}")
    public String appNode;

    @Override
    public BoeResponse<FetchIpData> ipInformation(FetchIpRequest fetchIpRequest, int page, int size) {

        BoeResponse<FetchIpData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchIpDataObject(fetchIpRequest, page, size));

        return boeResponse;
    }

    private FetchIpData getFetchIpDataObject(FetchIpRequest fetchIpRequest, int page, int size) {

        FetchIpData fetchIpData = new FetchIpData();
        fetchIpData.setIpFilter(this.getIpFilterObject(fetchIpRequest));

        this.getOverAllBucketData(fetchIpRequest, page, size, fetchIpData);
        this.getRfiBucketData(fetchIpRequest, page, size, fetchIpData);

        return fetchIpData;
    }

    private IpFilter getIpFilterObject(FetchIpRequest fetchIpRequest) {

        IpFilter ipFilter = new IpFilter();
        ipFilter.setBinNo(fetchIpRequest.getBinNo());
        ipFilter.setOu(fetchIpRequest.getOu());
        ipFilter.setBoeNo(fetchIpRequest.getBoeNo());
        ipFilter.setShipmentId(fetchIpRequest.getShipmentId());
        ipFilter.setRms(fetchIpRequest.getRms());
        ipFilter.setValidFrom(fetchIpRequest.getValidFrom());
        ipFilter.setValidTo(fetchIpRequest.getValidTo());

        return ipFilter;
    }

    private void getOverAllBucketData(FetchIpRequest fetchIpRequest,
                                      int page, int size, FetchIpData fetchIpData) {

        Page<Ip> resultPage = this.getRecord(fetchIpRequest, page, size, "16,17");

        if (fetchIpRequest.getBinNo() == 1) {

            List<Ip> dbList = resultPage.getContent();

            if (dbList != null && !dbList.isEmpty()) {

                fetchIpData.setOverAllBucketList(
                        dbList.stream().map(this::getIpRecordObject).collect(Collectors.toList())
                );
            }
        }

        fetchIpData.setOverAllBucketCount((int) resultPage.getTotalElements());

    }

    private void getRfiBucketData(FetchIpRequest fetchIpRequest,
                                  int page, int size, FetchIpData fetchIpData) {

        Page<Ip> resultPage = this.getRecord(fetchIpRequest, page, size, "17");

        if (fetchIpRequest.getBinNo() == 2) {

            List<Ip> dbList = resultPage.getContent();

            if (dbList != null && !dbList.isEmpty()) {

                fetchIpData.setOverAllBucketList(
                        dbList.stream().map(this::getIpRecordObject).collect(Collectors.toList())
                );
            }
        }

        fetchIpData.setRfiBucketCount((int) resultPage.getTotalElements());

    }

    private Page<Ip> getRecord(FetchIpRequest fetchIpRequest, int page, int size, String bucketNo) {

        Page<Ip> resultPage = this.btvlWkfBoeShipmentTblRepository.fetchIpRecords(
                bucketNo,
                fetchIpRequest.getOu(),
                fetchIpRequest.getBoeNo(),
                fetchIpRequest.getShipmentId(),
                fetchIpRequest.getRms(),
                fetchIpRequest.getValidFrom(),
                fetchIpRequest.getValidTo(),
                PageRequest.of(page - 1, size)
        );

        return resultPage;
    }

    private IpRecord getIpRecordObject(Ip ip) {

        IpRecord ipRecord = new IpRecord();
        ipRecord.setBoeNo(ip.getBoeNo());
        ipRecord.setShipmentId(ip.getShipmentId());
        ipRecord.setOuNo(ip.getOuName());
        ipRecord.setPoNo(ip.getPoNo());
        ipRecord.setInvoiceNo(ip.getInvoiceNumbers());
        ipRecord.setBoeDate(ip.getBoeDate());
        ipRecord.setChaName(ip.getChaAgent());
        ipRecord.setPartnerName(ip.getVendorName());
        ipRecord.setPartnerCode(ip.getPartnerVendorCode());
        ipRecord.setDutyAmount(ip.getAttribute2());
//        ipRecord.setIpDocList(this.getIpDocListObject(ip.getShipmentId()));

        return ipRecord;
    }

//    private List<IpDoc> getIpDocListObject(BigDecimal shipmentId) {
//
//        List<IpDoc> IpDocList = null;
//
//        List<ShipDoc> list = this.btvlWkfBoeShipDocTblRepository.fetchShipmentDocByShipmentId(shipmentId);
//
//        if (list != null && !list.isEmpty()) {
//            IpDocList = list.stream().map(this::getIpDocObject).collect(Collectors.toList());
//        }
//
//        return IpDocList;
//    }
//
//    private IpDoc getIpDocObject(ShipDoc shipDoc) {
//
//        IpDoc IpDoc = new IpDoc();
//        IpDoc.setDocName(shipDoc.getDocName());
//        IpDoc.setContentId(shipDoc.getContentId());
//
//        return IpDoc;
//    }

    @Override
    public BoeResponse<IpApprovalData> ipApprove(IpApprovalRequest ipApprovalRequest, UserDetailsImpl userDetails) {

        BoeResponse<IpApprovalData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getIpApprovalDataObject(ipApprovalRequest));

        if (this.checkMandatoryFieldsForApprove(ipApprovalRequest, boeResponse)) {
            this.approve(ipApprovalRequest, userDetails);
        }

        return boeResponse;
    }

    private IpApprovalData getIpApprovalDataObject(IpApprovalRequest ipApprovalRequest) {

        IpApprovalData ipApprovalData = new IpApprovalData();
        ipApprovalData.setShipmentId(ipApprovalRequest.getShipmentId());
        ipApprovalData.setChaName(ipApprovalRequest.getChaName());

        return ipApprovalData;
    }

    private Boolean checkMandatoryFieldsForApprove(IpApprovalRequest ipApprovalRequest,
                                                   BoeResponse<IpApprovalData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        IpApprovalData ipApprovalData = boeResponse.getData();

        if (ipApprovalRequest.getShipmentId() == null ||
                StringUtils.isBlank(ipApprovalRequest.getShipmentId().toString())) {
            isValidationPassed = Boolean.FALSE;
            ipApprovalData.setShipmentIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(ipApprovalRequest.getChaName())) {
            isValidationPassed = Boolean.FALSE;
            ipApprovalData.setChaNameErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("IpServiceImpl :: method :: checkMandatoryFieldsForApprove :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.IP_APPROVE_ERROR_CODE);
        }

        return isValidationPassed;
    }

    private void setErrorObject(BoeResponse boeResponse, String code) {
        com.airtel.buyer.airtelboe.dto.common.Error error = new Error();
        error.setCode(code);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }

    private void approve(IpApprovalRequest ipApprovalRequest, UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                this.btvlWkfBoeShipmentTblRepository.findByShipmentId(ipApprovalRequest.getShipmentId());

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            if (this.isValidAction(bTVL_WKF_BOE_SHIPMENT_TBL, Arrays.asList(16, 17), userDetails.getUsername())) {

                this.ipTransactionService.approveAction(ipApprovalRequest, bTVL_WKF_BOE_SHIPMENT_TBL, userDetails);

                Boolean b = QmtUtil.resolveTicket(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute3(), "OK");
                log.info("QMT API invoked on Approve for Closing IP Team Ticket -" +
                        bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute3() + " : " + b);

                String status = this.erpDaoRepository.callIpApprovalPrc(ipApprovalRequest.getShipmentId());
                log.info("Status from Procedure :" + status);

            } else {
                log.info("Not a valid approve action for Shipment Id :: " + ipApprovalRequest.getShipmentId());
                throw new BoeException("No record found", null, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + ipApprovalRequest.getShipmentId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

    }

    private Boolean isValidAction(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL, List<Integer> validBucketList, String olmId) {

        Boolean isValid = Boolean.FALSE;

        Integer dbBucketNo = bTVL_WKF_BOE_SHIPMENT_TBL.getBucketNo().intValue();

        if (validBucketList.contains(dbBucketNo)) {
            isValid = Boolean.TRUE;
        }

        log.info("IpServiceImpl :: method :: isValidAction :: " + bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId() + " :: " + isValid);
        return isValid;
    }

    @Override
    public BoeResponse<IpRejectData> ipReject(IpRejectRequest ipRejectRequest, UserDetailsImpl userDetails) {

        BoeResponse<IpRejectData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getIpRejectDataObject(ipRejectRequest));

        if (this.checkMandatoryFieldsForReject(ipRejectRequest, boeResponse)) {
            this.reject(ipRejectRequest, userDetails);
        }

        return boeResponse;
    }

    private IpRejectData getIpRejectDataObject(IpRejectRequest ipRejectRequest) {

        IpRejectData ipRejectData = new IpRejectData();
        ipRejectData.setShipmentId(ipRejectRequest.getShipmentId());
        ipRejectData.setChaName(ipRejectRequest.getChaName());
        ipRejectData.setRejectionReason(ipRejectRequest.getRejectionReason());
        ipRejectData.setRejectionActualAmount(ipRejectRequest.getRejectionActualAmount());
        ipRejectData.setRejectionDescription(ipRejectRequest.getRejectionDescription());

        return ipRejectData;
    }

    private Boolean checkMandatoryFieldsForReject(IpRejectRequest ipRejectRequest,
                                                  BoeResponse<IpRejectData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        IpRejectData ipRejectData = boeResponse.getData();

        if (ipRejectRequest.getShipmentId() == null ||
                StringUtils.isBlank(ipRejectRequest.getShipmentId().toString())) {
            isValidationPassed = Boolean.FALSE;
            ipRejectData.setShipmentIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(ipRejectRequest.getChaName())) {
            isValidationPassed = Boolean.FALSE;
            ipRejectData.setChaNameErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(ipRejectRequest.getRejectionReason())) {
            isValidationPassed = Boolean.FALSE;
            ipRejectData.setRejectionReasonErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(ipRejectRequest.getRejectionActualAmount())) {
            isValidationPassed = Boolean.FALSE;
            ipRejectData.setRejectionActualAmountErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(ipRejectRequest.getRejectionDescription())) {
            isValidationPassed = Boolean.FALSE;
            ipRejectData.setRejectionDescriptionErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("IpServiceImpl :: method :: checkMandatoryFieldsForReject :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.IP_REJECT_ERROR_CODE);
        }

        return isValidationPassed;
    }

    private void reject(IpRejectRequest ipRejectRequest, UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                this.btvlWkfBoeShipmentTblRepository.findByShipmentId(ipRejectRequest.getShipmentId());

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            if (this.isValidAction(bTVL_WKF_BOE_SHIPMENT_TBL, Arrays.asList(16, 17), userDetails.getUsername())) {

                this.ipTransactionService.rejectAction(ipRejectRequest, bTVL_WKF_BOE_SHIPMENT_TBL, userDetails);

                this.sendMailToChaAndScm(ipRejectRequest, bTVL_WKF_BOE_SHIPMENT_TBL);

                Boolean b = QmtUtil.resolveTicket(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute3(), "OK");
                log.info("QMT API invoked on Reject for Closing IP Team Ticket -" +
                        bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute3() + " : " + b);

            } else {
                log.info("Not a valid reject action for Shipment Id :: " + ipRejectRequest.getShipmentId());
                throw new BoeException("No record found", null, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + ipRejectRequest.getShipmentId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }
    }

    private void sendMailToChaAndScm(IpRejectRequest ipRejectRequest, BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL) {

        String from = CommonConstants.AIRTEL_BOE_FROM_EMAIL;

        String subject =
                String.format(CommonConstants.BOE_IP_REJECT_MAIL_SUBJECT, bTVL_WKF_BOE_SHIPMENT_TBL.getBoeNo(),
                        ipRejectRequest.getChaName() != null ? ipRejectRequest.getChaName() : "");

        String mailBodyForScm =
                String.format(CommonConstants.BOE_IP_REJECT_MAIL_BODY,
                        this.supplierLoginUrl,
                        bTVL_WKF_BOE_SHIPMENT_TBL.getBoeNo(), bTVL_WKF_BOE_SHIPMENT_TBL.getInvoiceNumbers(),
                        bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId(), ipRejectRequest.getRejectionReason(),
                        ipRejectRequest.getRejectionDescription());

        String mailBodyForCha =
                String.format(CommonConstants.BOE_IP_REJECT_MAIL_BODY,
                        this.buyerLoginUrl,
                        bTVL_WKF_BOE_SHIPMENT_TBL.getBoeNo(), bTVL_WKF_BOE_SHIPMENT_TBL.getInvoiceNumbers(),
                        bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId(), ipRejectRequest.getRejectionReason(),
                        ipRejectRequest.getRejectionDescription());

        String chaEmail =
                this.btvlMstBoeChaTblRepository.findByChaCode(bTVL_WKF_BOE_SHIPMENT_TBL.getAssignedChaId()).get(0).getChaEmail();

        List<BTVL_EMP_ROLE_MAPPING_TBL> empList =
                this.btvlEmpRoleMappingTblRepository.findByEmpRoleIdInAndLob(Arrays.asList(13, 14), bTVL_WKF_BOE_SHIPMENT_TBL.getLob());

        String scmEmail = empList.stream().map(BTVL_EMP_ROLE_MAPPING_TBL::getEmpMail).reduce((a, b) -> a + ", " + b).get();

        log.info("---Sending mail---");
        log.info("chaEmail :: " + chaEmail);
        log.info("scmEmail :: " + scmEmail);
        log.info("from :: " + from);
        log.info("subject :: " + subject);
        log.info("chaBody :: " + mailBodyForCha);
        log.info("scmBody :: " + mailBodyForScm);

        EmailUtil.sendEmail(this.javaMailSender, chaEmail, null, from, subject, mailBodyForCha);

        EmailUtil.sendEmail(this.javaMailSender, scmEmail, null, from, subject, mailBodyForScm);

    }

    @Override
    public BoeResponse<IpRfiData> ipRfi(IpRfiRequest ipRfiRequest, UserDetailsImpl userDetails) {

        BoeResponse<IpRfiData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getIpRfiDataObject(ipRfiRequest));

        if (this.checkMandatoryFieldsForRfi(ipRfiRequest, boeResponse)) {
            this.rfi(ipRfiRequest, userDetails);
        }

        return boeResponse;
    }

    private IpRfiData getIpRfiDataObject(IpRfiRequest ipRfiRequest) {

        IpRfiData ipRfiData = new IpRfiData();
        ipRfiData.setShipmentId(ipRfiRequest.getShipmentId());
        ipRfiData.setRfiReason(ipRfiRequest.getRfiReason());
        ipRfiData.setRfiDescription(ipRfiRequest.getRfiDescription());

        return ipRfiData;
    }

    private Boolean checkMandatoryFieldsForRfi(IpRfiRequest ipRfiRequest,
                                               BoeResponse<IpRfiData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        IpRfiData ipRfiData = boeResponse.getData();

        if (ipRfiRequest.getShipmentId() == null ||
                StringUtils.isBlank(ipRfiRequest.getShipmentId().toString())) {
            isValidationPassed = Boolean.FALSE;
            ipRfiData.setShipmentIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(ipRfiRequest.getRfiReason())) {
            isValidationPassed = Boolean.FALSE;
            ipRfiData.setRfiReasonErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(ipRfiRequest.getRfiDescription())) {
            isValidationPassed = Boolean.FALSE;
            ipRfiData.setRfiDescriptionErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("IpServiceImpl :: method :: checkMandatoryFieldsForRfi :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse, CommonConstants.IP_RFI_ERROR_CODE);
        }

        return isValidationPassed;
    }

    private void rfi(IpRfiRequest ipRfiRequest, UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                this.btvlWkfBoeShipmentTblRepository.findByShipmentId(ipRfiRequest.getShipmentId());

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            if (this.isValidAction(bTVL_WKF_BOE_SHIPMENT_TBL, Arrays.asList(16, 17), userDetails.getUsername())) {

                this.ipTransactionService.rfiAction(ipRfiRequest, bTVL_WKF_BOE_SHIPMENT_TBL, userDetails);

                Boolean b = QmtUtil.putTicketOnHold(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute3());
                log.info("QMT API invoked on RFI to CHA to put IP Team Ticket on HOLD -" +
                        bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute3() + " : " + b);

            } else {
                log.info("Not a valid rfi action for Shipment Id :: " + ipRfiRequest.getShipmentId());
                throw new BoeException("No record found", null, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + ipRfiRequest.getShipmentId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public void ipExcel(FetchIpRequest fetchIpRequest) {

        Page<Ip> resultPage = null;
        String excelName = null;

        if (fetchIpRequest.getBinNo() == 2) {
            resultPage = getRecord(fetchIpRequest, 1, 1000, "17");
            excelName = "IPRfiReport";
        } else {
            resultPage = getRecord(fetchIpRequest, 1, 1000, "16,17");
            excelName = "IPOverallReport";
        }

        List<Ip> ipList = resultPage.getContent();

        List<String> headerTextList = this.getExcelHeaders();
        List<Map<String, String>> rowMapList = this.getExcelRowsData(ipList);

        String excelCheck = ExcelUtil.downloadExcel(headerTextList, rowMapList, excelName, httpServletResponse, this.uploadTmpDir);

        if (excelCheck.equalsIgnoreCase("y")) {
            log.info("IP Excel Generated successfully");
        } else {
            throw new BoeException("Something went wrong", null, HttpStatus.OK);
        }

    }

    private List<String> getExcelHeaders() {

        List<String> headerTextList = new ArrayList<>();
        headerTextList.add("BOE NO.");
        headerTextList.add("AIRTEL BOE REF NO.");
        headerTextList.add("OU NO.");
        headerTextList.add("PO NO.");
        headerTextList.add("INVOICE NO.");
        headerTextList.add("BOE DATE");
        headerTextList.add("CHA NAME");
        headerTextList.add("PARTNER NAME");
        headerTextList.add("PARTNER CODE");

        return headerTextList;
    }

    private List<Map<String, String>> getExcelRowsData(List<Ip> excelData) {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (Ip row : excelData) {
            Map<String, String> map = new LinkedHashMap<>();

            map.put("BOE NO.", row.getBoeNo());
            map.put("AIRTEL BOE REF NO.", String.valueOf(row.getShipmentId()));
            map.put("OU NO.", row.getOuName());
            map.put("PO NO.", row.getPoNo());
            map.put("INVOICE NO.", row.getInvoiceNumbers());
            map.put("BOE DATE", String.valueOf(row.getBoeDate()));
            map.put("CHA NAME", String.valueOf(row.getAssignedChaId()));
            map.put("PARTNER NAME", row.getVendorName());
            map.put("PARTNER CODE", String.valueOf(row.getPartnerVendorCode()));

            rowMapList.add(map);
        }

        return rowMapList;
    }

}
