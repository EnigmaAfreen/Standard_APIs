package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIP_POC_TBL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface BtvlWkfBoeShipPocTblRepository extends JpaRepository<BTVL_WKF_BOE_SHIP_POC_TBL, BigDecimal> {

    BTVL_WKF_BOE_SHIP_POC_TBL findByShipmentId(BigDecimal shipmentId);
}
