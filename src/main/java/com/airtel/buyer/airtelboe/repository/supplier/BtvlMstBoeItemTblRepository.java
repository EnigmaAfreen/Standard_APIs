package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_ITEM_TBL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlMstBoeItemTblRepository extends JpaRepository<BTVL_MST_BOE_ITEM_TBL, BigDecimal> {

    List<BTVL_MST_BOE_ITEM_TBL> findByPurgeFlag(Integer purgeFlag);

    BTVL_MST_BOE_ITEM_TBL findByItemIdAndPurgeFlag(BigDecimal itemId, Integer purgeFlag);
}
