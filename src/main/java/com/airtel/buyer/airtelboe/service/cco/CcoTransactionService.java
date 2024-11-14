package com.airtel.buyer.airtelboe.service.cco;

import com.airtel.buyer.airtelboe.dto.ccoaction.request.CcoActionRequest;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_TRAIL_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipmentTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeTrailTblRepository;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.qmt.util.QmtUtil;
import com.airtel.qmt.util.pojo.CreateQmtCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
public class CcoTransactionService {

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlWkfBoeTrailTblRepository btvlWkfBoeTrailTblRepository;

    @Transactional
    public void approveAction(CcoActionRequest ccoActionRequest,
                              BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL,
                              UserDetailsImpl userDetails) {

        try {

            BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL =
                    this.getWkfBoeTrailObject(new BigDecimal(16), new BigDecimal(15),
                            null, "APPROVE", "CCO APPROVED",
                            ccoActionRequest.getShipmentId(),
                            ccoActionRequest.getComment(),
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
        bTVL_WKF_BOE_TRAIL_TBL.setActionBy("CCO");
        bTVL_WKF_BOE_TRAIL_TBL.setShipmentId(shipmentId);
        bTVL_WKF_BOE_TRAIL_TBL.setCommentLine(comment);
        bTVL_WKF_BOE_TRAIL_TBL.setPurgeFlag(0);
        bTVL_WKF_BOE_TRAIL_TBL.setCreatedby(olmId);
        bTVL_WKF_BOE_TRAIL_TBL.setCreationDate(LocalDateTime.now());
        bTVL_WKF_BOE_TRAIL_TBL.setModifiedby(olmId);
        bTVL_WKF_BOE_TRAIL_TBL.setModifiedDate(LocalDateTime.now());

        return bTVL_WKF_BOE_TRAIL_TBL;
    }

    @Transactional
    public void rejectAction(CcoActionRequest ccoActionRequest,
                             BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL,
                             UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL =
                this.getWkfBoeTrailObject(new BigDecimal(14), new BigDecimal(15),
                        new BigDecimal(3), "REJECT", "CCO REJECTED",
                        ccoActionRequest.getShipmentId(),
                        ccoActionRequest.getComment(),
                        userDetails.getUsername());
        this.btvlWkfBoeTrailTblRepository.save(bTVL_WKF_BOE_TRAIL_TBL);

        bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(new BigDecimal(14));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(new BigDecimal(15));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStage(new BigDecimal(3));
        bTVL_WKF_BOE_SHIPMENT_TBL.setAttribute4(null);
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(userDetails.getUsername());
        this.btvlWkfBoeShipmentTblRepository.save(bTVL_WKF_BOE_SHIPMENT_TBL);

    }

    @Transactional
    public void holdAction(CcoActionRequest ccoActionRequest,
                           BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL,
                           UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL =
                this.getWkfBoeTrailObject(new BigDecimal(19), new BigDecimal(15),
                        null, "HOLD", "CCO HOLD",
                        ccoActionRequest.getShipmentId(),
                        ccoActionRequest.getComment(),
                        userDetails.getUsername());
        this.btvlWkfBoeTrailTblRepository.save(bTVL_WKF_BOE_TRAIL_TBL);

        bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(new BigDecimal(19));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(new BigDecimal(15));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStage(new BigDecimal(5));
        bTVL_WKF_BOE_SHIPMENT_TBL.setAttribute4(null);
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(userDetails.getUsername());
        this.btvlWkfBoeShipmentTblRepository.save(bTVL_WKF_BOE_SHIPMENT_TBL);

    }

    @Transactional
    public void unHoldAction(CcoActionRequest ccoActionRequest,
                             BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL,
                             UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL =
                this.getWkfBoeTrailObject(new BigDecimal(8), new BigDecimal(15),
                        new BigDecimal(5), "UNHOLD", "CCO UNHOLD",
                        ccoActionRequest.getShipmentId(), null,
                        userDetails.getUsername());
        this.btvlWkfBoeTrailTblRepository.save(bTVL_WKF_BOE_TRAIL_TBL);

        String encryptedMailToken =
                BoeUtils.getCCoActionMailToken(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId(),
                        bTVL_WKF_BOE_SHIPMENT_TBL.getAssignedCcoId(),
                        bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute5());

        bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(new BigDecimal(18));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(new BigDecimal(15));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStage(new BigDecimal(5));
        bTVL_WKF_BOE_SHIPMENT_TBL.setAttribute4(encryptedMailToken);
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(userDetails.getUsername());
        this.btvlWkfBoeShipmentTblRepository.save(bTVL_WKF_BOE_SHIPMENT_TBL);

    }

}
