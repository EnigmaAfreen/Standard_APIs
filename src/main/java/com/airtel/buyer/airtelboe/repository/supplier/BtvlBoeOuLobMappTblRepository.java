package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_BOE_OU_LOB_MAPP_TBL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlBoeOuLobMappTblRepository extends JpaRepository<BTVL_BOE_OU_LOB_MAPP_TBL, BigDecimal> {

    @Query(nativeQuery = true,
    value = "select DISTINCT LOB from BTVL_BOE_OU_LOB_MAPP_TBL")
    List<String> findDistinctLobs();

    List<BTVL_BOE_OU_LOB_MAPP_TBL> findByLobAllIgnoreCase(String lob);

    @Query(nativeQuery = true,
            value = "select DISTINCT LE_NAME from BTVL_BOE_OU_LOB_MAPP_TBL")
    List<String> findDistinctLeNames();
}
