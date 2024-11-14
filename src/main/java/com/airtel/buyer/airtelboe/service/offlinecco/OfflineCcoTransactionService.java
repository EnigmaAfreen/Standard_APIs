package com.airtel.buyer.airtelboe.service.offlinecco;

import com.airtel.buyer.airtelboe.dto.document.response.DocumentResponse;
import com.airtel.buyer.airtelboe.dto.offlineccoapproval.request.OfflineCcoApprovalRequest;
import com.airtel.buyer.airtelboe.dto.offlineccoretrigger.request.OfflineCcoRetriggerRequest;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_EMP_ROLE_MAPPING_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIP_DOC_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_TRAIL_TBL;
import com.airtel.buyer.airtelboe.repository.erp.ErpDaoRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlEmpRoleMappingTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipDocTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipmentTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeTrailTblRepository;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.DmsUtil;
import com.airtel.qmt.util.QmtUtil;
import com.airtel.qmt.util.pojo.CreateQmtCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OfflineCcoTransactionService {

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlWkfBoeTrailTblRepository btvlWkfBoeTrailTblRepository;

    @Autowired
    private BtvlWkfBoeShipDocTblRepository btvlWkfBoeShipDocTblRepository;

    @Autowired
    private BtvlEmpRoleMappingTblRepository btvlEmpRoleMappingTblRepository;

    @Autowired
    private ErpDaoRepository erpDaoRepository;

    @Value("${UPLOAD.DOC.TO.UCM.URL}")
    private String uploadUcmUrl;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Transactional
    public void approveAction(OfflineCcoApprovalRequest offlineCcoApprovalRequest,
                              BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL,
                              UserDetailsImpl userDetails) {

        try {

            List<BTVL_WKF_BOE_SHIP_DOC_TBL> shipDocList =
                    this.getWkfBoeShipDocListObject(offlineCcoApprovalRequest, userDetails.getUsername());
            this.btvlWkfBoeShipDocTblRepository.saveAll(shipDocList);

            BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL =
                    this.getWkfBoeTrailObject(new BigDecimal(16), new BigDecimal(15),
                            null, "APPROVE", "CCO APPROVED",
                            offlineCcoApprovalRequest.getShipmentId(),
                            offlineCcoApprovalRequest.getApproveComment(),
                            userDetails.getUsername());
            this.btvlWkfBoeTrailTblRepository.save(bTVL_WKF_BOE_TRAIL_TBL);

            bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(new BigDecimal(16));
            bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(new BigDecimal(15));
            bTVL_WKF_BOE_SHIPMENT_TBL.setStage(null);
            bTVL_WKF_BOE_SHIPMENT_TBL.setAttribute4(null);
            bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());
            bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(userDetails.getUsername());
            bTVL_WKF_BOE_SHIPMENT_TBL.setAttribute3(this.createQmtTicket(bTVL_WKF_BOE_SHIPMENT_TBL));
            this.btvlWkfBoeShipmentTblRepository.save(bTVL_WKF_BOE_SHIPMENT_TBL);

        } catch (Exception e) {
            e.printStackTrace();
            Boolean b = QmtUtil.resolveTicket(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute3(), "OK");
            log.info("Closed QMT Ticket as unable to save details in Partner Portal DB. Ticket No is :" +
                    bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute3() + " : " + b);
        }

    }

    private List<BTVL_WKF_BOE_SHIP_DOC_TBL> getWkfBoeShipDocListObject(OfflineCcoApprovalRequest offlineCcoApprovalRequest, String olmId) {

        List<BTVL_WKF_BOE_SHIP_DOC_TBL> shipDocList = new ArrayList<>();

        List<String> docsPathList = new ArrayList<>();

        docsPathList.add(offlineCcoApprovalRequest.getOfflineCcoAttachment1DocPath());

        if (!StringUtils.isBlank(offlineCcoApprovalRequest.getOfflineCcoAttachment2DocPath())) {
            docsPathList.add(offlineCcoApprovalRequest.getOfflineCcoAttachment2DocPath());
        }

        if (!StringUtils.isBlank(offlineCcoApprovalRequest.getOfflineCcoAttachment3DocPath())) {
            docsPathList.add(offlineCcoApprovalRequest.getOfflineCcoAttachment3DocPath());
        }

        if (!StringUtils.isBlank(offlineCcoApprovalRequest.getOfflineCcoAttachment4DocPath())) {
            docsPathList.add(offlineCcoApprovalRequest.getOfflineCcoAttachment4DocPath());
        }

        List<DocumentResponse> documentResponseList = this.uploadDocument(docsPathList);

        for (DocumentResponse documentResponse : documentResponseList) {

            if (documentResponse.getFileName().equalsIgnoreCase(offlineCcoApprovalRequest.getOfflineCcoAttachment1DocPath())) {
                shipDocList.add(this.getWkfBoeShipDocObject(offlineCcoApprovalRequest.getShipmentId(), new BigDecimal(39),
                        documentResponse.getContentId(), new BigDecimal(-1), documentResponse.getDocUrl(),
                        olmId));

            } else if (documentResponse.getFileName().equalsIgnoreCase(offlineCcoApprovalRequest.getOfflineCcoAttachment2DocPath())) {
                shipDocList.add(this.getWkfBoeShipDocObject(offlineCcoApprovalRequest.getShipmentId(), new BigDecimal(40),
                        documentResponse.getContentId(), new BigDecimal(-1), documentResponse.getDocUrl(),
                        olmId));

            } else if (documentResponse.getFileName().equalsIgnoreCase(offlineCcoApprovalRequest.getOfflineCcoAttachment3DocPath())) {
                shipDocList.add(this.getWkfBoeShipDocObject(offlineCcoApprovalRequest.getShipmentId(), new BigDecimal(41),
                        documentResponse.getContentId(), new BigDecimal(-1), documentResponse.getDocUrl(),
                        olmId));

            } else if (documentResponse.getFileName().equalsIgnoreCase(offlineCcoApprovalRequest.getOfflineCcoAttachment4DocPath())) {
                shipDocList.add(this.getWkfBoeShipDocObject(offlineCcoApprovalRequest.getShipmentId(), new BigDecimal(42),
                        documentResponse.getContentId(), new BigDecimal(-1), documentResponse.getDocUrl(),
                        olmId));

            }

        }

        return shipDocList;
    }

    private List<DocumentResponse> uploadDocument(List<String> docsPathList) {

        // upload Doc to UCM
        List<DocumentResponse> documentResponseList = DmsUtil.uploadDoc(docsPathList, this.uploadUcmUrl,
                this.httpServletResponse, this.httpServletRequest);

        if (documentResponseList != null && !documentResponseList.isEmpty()) {

            for (DocumentResponse documentResponse : documentResponseList) {
                if (documentResponse == null || StringUtils.isBlank(documentResponse.getContentId())
                        || !documentResponse.getContentId().startsWith("WC")) {
                    log.info("OfflineCcoTransactionService :: method :: uploadDocument :: doc uploaded to UCM :: Error");
                    throw new BoeException("Document upload fails", null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

        } else {
            throw new BoeException("Document upload fails", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return documentResponseList;
    }

    private BTVL_WKF_BOE_SHIP_DOC_TBL getWkfBoeShipDocObject(BigDecimal shipmentId, BigDecimal docId,
                                                             String contentId, BigDecimal stage,
                                                             String docUrl, String olmId) {

        BTVL_WKF_BOE_SHIP_DOC_TBL bTVL_WKF_BOE_SHIP_DOC_TBL = new BTVL_WKF_BOE_SHIP_DOC_TBL();
        bTVL_WKF_BOE_SHIP_DOC_TBL.setShipmentId(shipmentId);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setDocId(docId);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setAttribute(contentId);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setStage(stage);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setDocUrl(docUrl);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setPurgeFlag(0);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setCreatedby(olmId);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setCreationDate(LocalDateTime.now());
        bTVL_WKF_BOE_SHIP_DOC_TBL.setModifiedby(olmId);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setModifiedDate(LocalDateTime.now());

        return bTVL_WKF_BOE_SHIP_DOC_TBL;
    }

    private BTVL_WKF_BOE_TRAIL_TBL getWkfBoeTrailObject(BigDecimal bucketNo, BigDecimal status,
                                                        BigDecimal stage, String action,
                                                        String commentHeader, BigDecimal shipmentId,
                                                        String comment, String olmId) {

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL = new BTVL_WKF_BOE_TRAIL_TBL();
        bTVL_WKF_BOE_TRAIL_TBL.setBucketNo(bucketNo);
        bTVL_WKF_BOE_TRAIL_TBL.setStatus(status);
        bTVL_WKF_BOE_TRAIL_TBL.setStage(stage);
        bTVL_WKF_BOE_TRAIL_TBL.setAction(action);
        bTVL_WKF_BOE_TRAIL_TBL.setCommentHeader(commentHeader);
        bTVL_WKF_BOE_TRAIL_TBL.setActionBy("SCM");
        bTVL_WKF_BOE_TRAIL_TBL.setShipmentId(shipmentId);
        bTVL_WKF_BOE_TRAIL_TBL.setCommentLine(comment);
        bTVL_WKF_BOE_TRAIL_TBL.setPurgeFlag(0);
        bTVL_WKF_BOE_TRAIL_TBL.setCreatedby(olmId);
        bTVL_WKF_BOE_TRAIL_TBL.setCreationDate(LocalDateTime.now());
        bTVL_WKF_BOE_TRAIL_TBL.setModifiedby(olmId);
        bTVL_WKF_BOE_TRAIL_TBL.setModifiedDate(LocalDateTime.now());

        return bTVL_WKF_BOE_TRAIL_TBL;
    }

    private String createQmtTicket(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL) {

        CreateQmtCase qmtCaseObj = new CreateQmtCase();
        qmtCaseObj.setGroup("1");
        qmtCaseObj.setGeography("1");
        qmtCaseObj.setOperatingCircle("38");
        qmtCaseObj.setProcess("158");
        qmtCaseObj.setCategory("250");
        qmtCaseObj.setSubCategory("10065");
        qmtCaseObj.setSubject("IP Tickets");
        qmtCaseObj.setDescription(bTVL_WKF_BOE_SHIPMENT_TBL.getBoeNo());
        String qmtIpTicket = QmtUtil.createQMTTicket(qmtCaseObj);

        log.info("QMT Ticket created for Shipment id " + bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId() +
                " is : " + qmtIpTicket);
        return qmtIpTicket;
    }

    @Transactional
    public void retiggerAction(OfflineCcoRetriggerRequest offlineCcoRetriggerRequest,
                               BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL,
                               UserDetailsImpl userDetails) {

        Map<String, String> map = erpDaoRepository.getCco(bTVL_WKF_BOE_SHIPMENT_TBL.getOuName());

        String ccoOlmId = map.get("olmId");
        String ccoEmailId = map.get("emailId");
        String ccoError = map.get("error");

        if (StringUtils.isBlank(ccoError) && !StringUtils.isBlank(ccoOlmId) && !StringUtils.isBlank(ccoEmailId)) {

            String encryptedMailToken =
                    BoeUtils.getCCoActionMailToken(offlineCcoRetriggerRequest.getShipmentId(), ccoOlmId, ccoEmailId);

            bTVL_WKF_BOE_SHIPMENT_TBL.setAssignedCcoId(ccoOlmId);
            bTVL_WKF_BOE_SHIPMENT_TBL.setAttribute4(encryptedMailToken);
            bTVL_WKF_BOE_SHIPMENT_TBL.setAttribute5(ccoEmailId);
            bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(new BigDecimal(18));
            bTVL_WKF_BOE_SHIPMENT_TBL.setStage(new BigDecimal(5));
            bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(new BigDecimal(14));
            bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(userDetails.getUsername());
            bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());
            this.btvlWkfBoeShipmentTblRepository.save(bTVL_WKF_BOE_SHIPMENT_TBL);

            BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL =
                    this.getWkfBoeTrailObject(new BigDecimal(18), new BigDecimal(14),
                            new BigDecimal(5), "CCO ASSIGMENT", null,
                            offlineCcoRetriggerRequest.getShipmentId(),
                            null,
                            userDetails.getUsername());
            this.btvlWkfBoeTrailTblRepository.save(bTVL_WKF_BOE_TRAIL_TBL);

            List<BTVL_EMP_ROLE_MAPPING_TBL> empRoleList = this.btvlEmpRoleMappingTblRepository.findByEmpOlmIdAndEmpRoleIdAllIgnoreCase(ccoOlmId, 16);

            if (empRoleList == null || empRoleList.isEmpty()) {
                BTVL_EMP_ROLE_MAPPING_TBL bTVL_EMP_ROLE_MAPPING_TBL = this.getEmpRoleMappingObject(ccoOlmId,
                        ccoEmailId, userDetails.getUsername());
                this.btvlEmpRoleMappingTblRepository.save(bTVL_EMP_ROLE_MAPPING_TBL);
            }

        } else {

            bTVL_WKF_BOE_SHIPMENT_TBL.setAssignedCcoId("CCO ASSIGNMENT FAILED");
            bTVL_WKF_BOE_SHIPMENT_TBL.setAttribute4(null);
            bTVL_WKF_BOE_SHIPMENT_TBL.setAttribute5(null);
            bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(new BigDecimal(20));
            bTVL_WKF_BOE_SHIPMENT_TBL.setStage(new BigDecimal(5));
            bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(new BigDecimal(14));
            bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(userDetails.getUsername());
            bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());
            this.btvlWkfBoeShipmentTblRepository.save(bTVL_WKF_BOE_SHIPMENT_TBL);

            BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL =
                    this.getWkfBoeTrailObject(new BigDecimal(20), new BigDecimal(14),
                            new BigDecimal(5), "CCO ASSIGMENT", null,
                            offlineCcoRetriggerRequest.getShipmentId(),
                            null,
                            userDetails.getUsername());
            this.btvlWkfBoeTrailTblRepository.save(bTVL_WKF_BOE_TRAIL_TBL);

        }

    }

    private BTVL_EMP_ROLE_MAPPING_TBL getEmpRoleMappingObject(String empOlm, String empMail, String olm) {

        BTVL_EMP_ROLE_MAPPING_TBL bTVL_EMP_ROLE_MAPPING_TBL = new BTVL_EMP_ROLE_MAPPING_TBL();
        bTVL_EMP_ROLE_MAPPING_TBL.setEmpOlmId(empOlm);
        bTVL_EMP_ROLE_MAPPING_TBL.setEmpMail(empMail);
        bTVL_EMP_ROLE_MAPPING_TBL.setEmpRoleId(16);
        bTVL_EMP_ROLE_MAPPING_TBL.setPurgeFlag(0);
        bTVL_EMP_ROLE_MAPPING_TBL.setCreationDate(LocalDateTime.now());
        bTVL_EMP_ROLE_MAPPING_TBL.setCreatedby(olm);
        bTVL_EMP_ROLE_MAPPING_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_EMP_ROLE_MAPPING_TBL.setModifiedby(olm);

        return bTVL_EMP_ROLE_MAPPING_TBL;
    }

}
