package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_ANTI_DUMPING_TBL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlMstBoeAntiDumpingRepository extends JpaRepository<BTVL_MST_BOE_ANTI_DUMPING_TBL, BigDecimal> {
    //BTVL_MST_BOE_ANTI_DUMPING_TBL findByCountryCode(String countryCode);

    List<BTVL_MST_BOE_ANTI_DUMPING_TBL> findByCountryCode(String countryCode);
}
