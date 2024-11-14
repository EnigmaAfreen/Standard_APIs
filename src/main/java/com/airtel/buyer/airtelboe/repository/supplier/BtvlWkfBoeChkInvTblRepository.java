package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_EMP_ROLE_MAPPING_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_CHK_INV_TBL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlWkfBoeChkInvTblRepository extends JpaRepository<BTVL_WKF_BOE_CHK_INV_TBL, BigDecimal> {

    List<BTVL_WKF_BOE_CHK_INV_TBL> findByBoeHeaderIdAndPurgeFlag(BigDecimal boeHeaderId, Integer purgeFlag);
}
