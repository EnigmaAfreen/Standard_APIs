package com.airtel.buyer.airtelboe.service.ip;

import com.airtel.buyer.airtelboe.dto.ipapproval.request.IpApprovalRequest;
import com.airtel.buyer.airtelboe.dto.ipreject.request.IpRejectRequest;
import com.airtel.buyer.airtelboe.dto.iprfi.request.IpRfiRequest;
import com.airtel.buyer.airtelboe.model.supplier.*;
import com.airtel.buyer.airtelboe.repository.supplier.*;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IpTransactionService {

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlWkfBoeTrailTblRepository btvlWkfBoeTrailTblRepository;

    @Autowired
    private BtvlWkfBoeDdTblRepository btvlWkfBoeDdTblRepository;

    @Autowired
    private BtvlWkfBoeChkHTblRepository btvlWkfBoeChkHTblRepository;

    @Autowired
    private BtvlWkfBoeChkInvTblRepository btvlWkfBoeChkInvTblRepository;

    @Autowired
    private BtvlWkfBoeChkLTblRepository btvlWkfBoeChkLTblRepository;

    @Transactional
    public void approveAction(IpApprovalRequest ipApprovalRequest, BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL, UserDetailsImpl userDetails) {

        bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(new BigDecimal(5));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(new BigDecimal(8));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStage(new BigDecimal(6));
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(userDetails.getUsername());
        this.btvlWkfBoeShipmentTblRepository.save(bTVL_WKF_BOE_SHIPMENT_TBL);

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL =
                this.getWkfBoeTrailObject(new BigDecimal(5), new BigDecimal(8),
                        new BigDecimal(6), "APPROVE", null,
                        ipApprovalRequest.getShipmentId(), null,
                        userDetails.getUsername(), null);
        this.btvlWkfBoeTrailTblRepository.save(bTVL_WKF_BOE_TRAIL_TBL);

        BTVL_WKF_BOE_DD_TBL bTVL_WKF_BOE_DD_TBL =
                this.getWkfBoeDdObejct(ipApprovalRequest.getShipmentId(),
                        bTVL_WKF_BOE_SHIPMENT_TBL.getVendorName(),
                        ipApprovalRequest.getChaName(), userDetails.getUsername());
        this.btvlWkfBoeDdTblRepository.save(bTVL_WKF_BOE_DD_TBL);

    }

    private BTVL_WKF_BOE_TRAIL_TBL getWkfBoeTrailObject(BigDecimal bucketNo, BigDecimal status,
                                                        BigDecimal stage, String action,
                                                        String commentHeader, BigDecimal shipmentId,
                                                        String comment, String olmId,
                                                        String attribute1) {

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL = new BTVL_WKF_BOE_TRAIL_TBL();
        bTVL_WKF_BOE_TRAIL_TBL.setBucketNo(bucketNo);
        bTVL_WKF_BOE_TRAIL_TBL.setStatus(status);
        bTVL_WKF_BOE_TRAIL_TBL.setStage(stage);
        bTVL_WKF_BOE_TRAIL_TBL.setAction(action);
        bTVL_WKF_BOE_TRAIL_TBL.setCommentHeader(commentHeader);
        bTVL_WKF_BOE_TRAIL_TBL.setActionBy("IP");
        bTVL_WKF_BOE_TRAIL_TBL.setShipmentId(shipmentId);
        bTVL_WKF_BOE_TRAIL_TBL.setCommentLine(comment);
        bTVL_WKF_BOE_TRAIL_TBL.setPurgeFlag(0);
        bTVL_WKF_BOE_TRAIL_TBL.setCreatedby(olmId);
        bTVL_WKF_BOE_TRAIL_TBL.setCreationDate(LocalDateTime.now());
        bTVL_WKF_BOE_TRAIL_TBL.setModifiedby(olmId);
        bTVL_WKF_BOE_TRAIL_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_TRAIL_TBL.setAttribute1(attribute1);

        return bTVL_WKF_BOE_TRAIL_TBL;
    }

    private BTVL_WKF_BOE_DD_TBL getWkfBoeDdObejct(BigDecimal shipmentId, String vendorName,
                                                  String chaName, String olmId) {

        BTVL_WKF_BOE_DD_TBL bTVL_WKF_BOE_DD_TBL = new BTVL_WKF_BOE_DD_TBL();
        bTVL_WKF_BOE_DD_TBL.setShipmentId(shipmentId);
        bTVL_WKF_BOE_DD_TBL.setSupplierName(vendorName);
        bTVL_WKF_BOE_DD_TBL.setChaName(chaName);
        bTVL_WKF_BOE_DD_TBL.setPurgeFlag(0);
        bTVL_WKF_BOE_DD_TBL.setCreatedby(olmId);
        bTVL_WKF_BOE_DD_TBL.setCreationDate(LocalDateTime.now());
        bTVL_WKF_BOE_DD_TBL.setModifiedby(olmId);
        bTVL_WKF_BOE_DD_TBL.setModifiedDate(LocalDateTime.now());

        return bTVL_WKF_BOE_DD_TBL;
    }

    @Transactional
    public void rejectAction(IpRejectRequest ipRejectRequest, BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL, UserDetailsImpl userDetails) {

        BTVL_WKF_BOE_CHK_H_TBL bTVL_WKF_BOE_CHK_H_TBL = this.getWkfBoeChkHObject(ipRejectRequest.getShipmentId(), userDetails.getUsername());
        this.btvlWkfBoeChkHTblRepository.save(bTVL_WKF_BOE_CHK_H_TBL);

        List<BTVL_WKF_BOE_CHK_INV_TBL> invoiceList =
                this.getWkfBoeChkInvListObject(bTVL_WKF_BOE_CHK_H_TBL.getBoeHeaderId(), userDetails.getUsername());
        this.btvlWkfBoeChkInvTblRepository.saveAll(invoiceList);


        List<BigDecimal> boeInvHeaderIdList =
                invoiceList.stream().map(BTVL_WKF_BOE_CHK_INV_TBL::getBoeInvHeaderId).distinct().collect(Collectors.toList());

        List<BTVL_WKF_BOE_CHK_L_TBL> lineList =
                this.getWkfBoeChkLListObject(boeInvHeaderIdList, userDetails.getUsername());
        this.btvlWkfBoeChkLTblRepository.saveAll(lineList);

        bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(new BigDecimal(14));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(new BigDecimal(4));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStage(new BigDecimal(3));
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(userDetails.getUsername());
        this.btvlWkfBoeShipmentTblRepository.save(bTVL_WKF_BOE_SHIPMENT_TBL);

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL =
                this.getWkfBoeTrailObject(new BigDecimal(14), new BigDecimal(4),
                        null, "REJECT", ipRejectRequest.getRejectionReason(),
                        ipRejectRequest.getShipmentId(), ipRejectRequest.getRejectionDescription(),
                        userDetails.getUsername(), ipRejectRequest.getRejectionActualAmount());
        this.btvlWkfBoeTrailTblRepository.save(bTVL_WKF_BOE_TRAIL_TBL);

    }

    private BTVL_WKF_BOE_CHK_H_TBL getWkfBoeChkHObject(BigDecimal shipmentId, String olmId) {

        BTVL_WKF_BOE_CHK_H_TBL bTVL_WKF_BOE_CHK_H_TBL =
                this.btvlWkfBoeChkHTblRepository.findByShipmentIdAndPurgeFlag(shipmentId, 0);

        bTVL_WKF_BOE_CHK_H_TBL.setPurgeFlag(1);
        bTVL_WKF_BOE_CHK_H_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_CHK_H_TBL.setModifiedby(olmId);

        return bTVL_WKF_BOE_CHK_H_TBL;
    }

    private List<BTVL_WKF_BOE_CHK_INV_TBL> getWkfBoeChkInvListObject(BigDecimal boeHeaderId, String olmId) {

        List<BTVL_WKF_BOE_CHK_INV_TBL> invoiceList =
                this.btvlWkfBoeChkInvTblRepository.findByBoeHeaderIdAndPurgeFlag(boeHeaderId, 0);

        invoiceList.forEach(invoice -> {
            invoice.setPurgeFlag(0);
            invoice.setModifiedDate(LocalDateTime.now());
            invoice.setModifiedby(olmId);
        });

        return invoiceList;
    }

    private List<BTVL_WKF_BOE_CHK_L_TBL> getWkfBoeChkLListObject(List<BigDecimal> boeInvHeaderIdList, String olmId) {

        List<BTVL_WKF_BOE_CHK_L_TBL> lineList =
                this.btvlWkfBoeChkLTblRepository.findByBoeInvHeaderIdInAndPurgeFlag(boeInvHeaderIdList, 0);

        lineList.forEach(line -> {
            line.setPurgeFlag(0);
            line.setModifiedDate(LocalDateTime.now());
            line.setModifiedby(olmId);
        });

        return lineList;
    }

    @Transactional
    public void rfiAction(IpRfiRequest ipRfiRequest, BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL, UserDetailsImpl userDetails) {

        bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(new BigDecimal(7));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(new BigDecimal(7));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStage(null);
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(userDetails.getUsername());
        this.btvlWkfBoeShipmentTblRepository.save(bTVL_WKF_BOE_SHIPMENT_TBL);

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL =
                this.getWkfBoeTrailObject(new BigDecimal(7), new BigDecimal(7),
                        null, "RFI", ipRfiRequest.getRfiReason(),
                        ipRfiRequest.getShipmentId(), ipRfiRequest.getRfiDescription(),
                        userDetails.getUsername(), null);
        this.btvlWkfBoeTrailTblRepository.save(bTVL_WKF_BOE_TRAIL_TBL);

    }

}
