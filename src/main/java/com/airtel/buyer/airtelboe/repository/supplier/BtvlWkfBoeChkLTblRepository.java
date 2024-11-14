package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_CHK_L_TBL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlWkfBoeChkLTblRepository extends JpaRepository<BTVL_WKF_BOE_CHK_L_TBL, BigDecimal> {

    List<BTVL_WKF_BOE_CHK_L_TBL> findByBoeInvHeaderIdAndPurgeFlag(BigDecimal boeInvHeaderId, Integer purgeFlag);

    List<BTVL_WKF_BOE_CHK_L_TBL> findByBoeInvHeaderIdInAndPurgeFlag(List<BigDecimal> boeInvHeaderIds, Integer purgeFlag);

}
