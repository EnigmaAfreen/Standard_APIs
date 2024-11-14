package com.airtel.buyer.airtelboe.service.fetchboemismatch;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchboemismatch.response.BoeMismatchData;
import com.airtel.buyer.airtelboe.dto.fetchboemismatch.response.MismatchDoc;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_TRAIL_TBL;
import com.airtel.buyer.airtelboe.repository.ShipDoc;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipDocTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipmentTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeTrailTblRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FetchBoeMismatchServiceImpl implements FetchBoeMismatchService {

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlWkfBoeTrailTblRepository btvlWkfBoeTrailTblRepository;

    @Autowired
    private BtvlWkfBoeShipDocTblRepository btvlWkfBoeShipDocTblRepository;

    @Override
    public BoeResponse<BoeMismatchData> mismatchInformation(BigDecimal shipmentId) {

        BoeResponse<BoeMismatchData> boeResponse = new BoeResponse<>();

        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                this.btvlWkfBoeShipmentTblRepository.findByShipmentId(shipmentId);

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            boeResponse.setData(this.getBoeMismatchDataObject(bTVL_WKF_BOE_SHIPMENT_TBL));

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + shipmentId);
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

        return boeResponse;
    }

    private BoeMismatchData getBoeMismatchDataObject(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL) {

        BoeMismatchData boeMismatchData = new BoeMismatchData();
        boeMismatchData.setPartnerCode(bTVL_WKF_BOE_SHIPMENT_TBL.getPartnerVendorCode());

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL = this.btvlWkfBoeTrailTblRepository.
                findFirstByShipmentIdAndActionByAndActionAllIgnoreCaseOrderByTrailIdDesc(
                        bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId(),
                        "IP", "REJECT");

        if (bTVL_WKF_BOE_TRAIL_TBL != null) {
            boeMismatchData.setRejectionReason(bTVL_WKF_BOE_TRAIL_TBL.getCommentHeader());
            boeMismatchData.setActualAmount(bTVL_WKF_BOE_TRAIL_TBL.getAttribute1());
            boeMismatchData.setRejectionDesc(bTVL_WKF_BOE_TRAIL_TBL.getCommentLine());
        }

        boeMismatchData.setMismatchDocList(this.getMismatchDocListObject(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId()));

        return boeMismatchData;
    }

    private List<MismatchDoc> getMismatchDocListObject(BigDecimal shipmentId) {

        List<MismatchDoc> mismatchDocList = null;

        List<ShipDoc> list = this.btvlWkfBoeShipDocTblRepository.fetchMismatchDoc(shipmentId);

        if (list != null && !list.isEmpty()) {
            mismatchDocList = list.stream().map(this::getMismatchDocObject).collect(Collectors.toList());
        }

        return mismatchDocList;
    }

    private MismatchDoc getMismatchDocObject(ShipDoc shipDoc) {

        MismatchDoc mismatchDoc = new MismatchDoc();
        mismatchDoc.setDocName(shipDoc.getDocName());
        mismatchDoc.setContentId(shipDoc.getContentId());

        return mismatchDoc;
    }

}
