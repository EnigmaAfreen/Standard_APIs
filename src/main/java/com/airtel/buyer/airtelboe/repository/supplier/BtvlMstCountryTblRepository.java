package com.airtel.buyer.airtelboe.repository.supplier;


import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_COUNTRY_TBL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface BtvlMstCountryTblRepository extends JpaRepository<BTVL_MST_COUNTRY_TBL, BigDecimal> {
}
