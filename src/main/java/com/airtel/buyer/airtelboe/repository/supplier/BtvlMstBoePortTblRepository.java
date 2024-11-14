package com.airtel.buyer.airtelboe.repository.supplier;


import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_PORT_TBL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlMstBoePortTblRepository extends JpaRepository<BTVL_MST_BOE_PORT_TBL, BigDecimal> {

    List<BTVL_MST_BOE_PORT_TBL> findByCountryCodeAndPurgeFlagAllIgnoreCase(String countryCode, Integer purgeFlag);

    List<BTVL_MST_BOE_PORT_TBL> findByPurgeFlag(Integer purgeFlag);

    List<BTVL_MST_BOE_PORT_TBL> findByCountryCodeIgnoreCase(String countryCode);

    List<BTVL_MST_BOE_PORT_TBL> findByPortCodeIgnoreCase(String portCode);

    List<BTVL_MST_BOE_PORT_TBL> findByPortCodeAndPurgeFlagAllIgnoreCase(String portCode, Integer purgeFlag);

}
