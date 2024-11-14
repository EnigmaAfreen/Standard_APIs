package com.airtel.buyer.airtelboe.service.chareassignmentaction;

import com.airtel.buyer.airtelboe.dto.chareassignmentaction.request.ChaReassignmentRequest;
import com.airtel.buyer.airtelboe.dto.chareassignmentaction.response.ChaReassignmentResponse;
import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_TRAIL_TBL;
import com.airtel.buyer.airtelboe.repository.ChaAssignment;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipmentTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeTrailTblRepository;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ChaReassignmentTransactionService {


    @Value("${app.node}")
    public String appNode;

    @Autowired
    BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    BtvlWkfBoeTrailTblRepository btvlWkfBoeTrailTblRepository;

    @Transactional
    public Boolean doActionForAssignmentNotFaild(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL, BoeResponse<ChaReassignmentResponse> boeResponse, ChaReassignmentRequest chaReassignmentRequest, UserDetailsImpl userDetails) {

        Boolean boeTrail = Boolean.FALSE;

        bTVL_WKF_BOE_SHIPMENT_TBL.setAssignedChaId(chaReassignmentRequest.getToChaAgent());
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(userDetails.getUsername());
        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL = this.getBoeTrailObj(bTVL_WKF_BOE_SHIPMENT_TBL, userDetails, chaReassignmentRequest);
        btvlWkfBoeShipmentTblRepository.save(bTVL_WKF_BOE_SHIPMENT_TBL);

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TB = this.btvlWkfBoeTrailTblRepository.save(bTVL_WKF_BOE_TRAIL_TBL);

        if (bTVL_WKF_BOE_TRAIL_TB != null && bTVL_WKF_BOE_TRAIL_TB.getTrailId() != null) {
            boeTrail = Boolean.TRUE;

        }

        return boeTrail;
    }

    public BTVL_WKF_BOE_TRAIL_TBL getBoeTrailObj(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL, UserDetailsImpl userDetails, ChaReassignmentRequest chaReassignmentRequest) {
        Boolean result = Boolean.FALSE;
        log.info("Inside method getBoeTrailObj ");


        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL = new BTVL_WKF_BOE_TRAIL_TBL();

        bTVL_WKF_BOE_TRAIL_TBL.setAction("CHA REASSIGNMENT");
        bTVL_WKF_BOE_TRAIL_TBL.setActionBy("SCM");
        bTVL_WKF_BOE_TRAIL_TBL.setShipmentId(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId());
        bTVL_WKF_BOE_TRAIL_TBL.setStatus(bTVL_WKF_BOE_SHIPMENT_TBL.getStatus() != null ?
                bTVL_WKF_BOE_SHIPMENT_TBL.getStatus() :
                null);
        bTVL_WKF_BOE_TRAIL_TBL.setBucketNo(bTVL_WKF_BOE_SHIPMENT_TBL.getBucketNo() != null ?
                bTVL_WKF_BOE_SHIPMENT_TBL.getBucketNo() :
                null);
        bTVL_WKF_BOE_TRAIL_TBL.setStage(bTVL_WKF_BOE_SHIPMENT_TBL.getStage() != null ?
                bTVL_WKF_BOE_SHIPMENT_TBL.getStage() :
                null);
        bTVL_WKF_BOE_TRAIL_TBL.setPurgeFlag(0);
        bTVL_WKF_BOE_TRAIL_TBL.setCommentHeader("CHA REASSIGNED TO - " + chaReassignmentRequest.getToChaAgent());
        bTVL_WKF_BOE_TRAIL_TBL.setCommentLine(chaReassignmentRequest.getDescription());
        bTVL_WKF_BOE_TRAIL_TBL.setCreatedby(userDetails.getUsername());
        bTVL_WKF_BOE_TRAIL_TBL.setCreationDate(LocalDateTime.now());
        bTVL_WKF_BOE_TRAIL_TBL.setModifiedby(userDetails.getUsername());
        bTVL_WKF_BOE_TRAIL_TBL.setModifiedDate(LocalDateTime.now());

        return bTVL_WKF_BOE_TRAIL_TBL;
    }

    @Transactional
    public Boolean doActionForAssignmentFaild(ChaAssignment cha,BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL, BoeResponse<ChaReassignmentResponse> boeResponse, ChaReassignmentRequest chaReassignmentRequest, UserDetailsImpl userDetails) {

        Boolean result = Boolean.FALSE;

        bTVL_WKF_BOE_SHIPMENT_TBL.setAssignedChaId(cha.getChaCode());
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(userDetails.getUsername());
//        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL = this.getBoeTrailObj(bTVL_WKF_BOE_SHIPMENT_TBL, userDetails, chaReassignmentRequest);
        btvlWkfBoeShipmentTblRepository.save(bTVL_WKF_BOE_SHIPMENT_TBL);
        result=Boolean.TRUE;
//        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TB = this.btvlWkfBoeTrailTblRepository.save(bTVL_WKF_BOE_TRAIL_TBL);
//
//        if (bTVL_WKF_BOE_TRAIL_TB != null && bTVL_WKF_BOE_TRAIL_TB.getTrailId() != null) {
//            boeTrail = Boolean.TRUE;
//
//        }

        return result;
    }

}
