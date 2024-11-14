package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_CHK_H_TBL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface BtvlWkfBoeChkHTblRepository extends JpaRepository<BTVL_WKF_BOE_CHK_H_TBL, BigDecimal> {

    BTVL_WKF_BOE_CHK_H_TBL findByShipmentIdAndPurgeFlag(BigDecimal shipmentId, Integer purgeFlag);
}
