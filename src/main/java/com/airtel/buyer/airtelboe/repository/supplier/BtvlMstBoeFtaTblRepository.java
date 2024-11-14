package com.airtel.buyer.airtelboe.repository.supplier;


import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_FTA_TBL;
import com.airtel.buyer.airtelboe.repository.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlMstBoeFtaTblRepository extends JpaRepository<BTVL_MST_BOE_FTA_TBL, BigDecimal> {

    List<BTVL_MST_BOE_FTA_TBL> findByPurgeFlag(Integer purgeFlag);

    @Query(value = "SELECT    \n" +
            "    t1.COUNTRY as country,    \n" +
            "    t1.COUNTRY_CODE as countryCode    \n" +
            "FROM    \n" +
            "    btvl_mst_boe_fta_tbl t1    \n" +
            "UNION ALL    \n" +
            "SELECT    \n" +
            "    COUNTRY,    \n" +
            "    COUNTRY_CODE    \n" +
            "FROM    \n" +
            "    (    \n" +
            "        SELECT    \n" +
            "            t2.territory_short_name AS country,    \n" +
            "            t2.territory_code AS country_code    \n" +
            "        FROM    \n" +
            "            btvl_fnd_territories_mv t2    \n" +
            "        WHERE    \n" +
            "            t2.territory_code NOT IN (    \n" +
            "                SELECT    \n" +
            "                    t3.country_code    \n" +
            "                FROM    \n" +
            "                    btvl_mst_boe_fta_tbl t3    \n" +
            "            )    \n" +
            "        ORDER BY    \n" +
            "            t2.territory_short_name ASC    \n" +
            "    )", nativeQuery = true)
    List<Country> fetchFtaCountryList();

    //BTVL_MST_BOE_FTA_TBL findByCountryCode(String countryCode);

    List<BTVL_MST_BOE_FTA_TBL> findByCountryCode(String countryCode);
}
