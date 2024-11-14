package com.airtel.buyer.airtelboe.repository.supplier;


import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_WPC_TBL;
import com.airtel.buyer.airtelboe.repository.WpcMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BtvlMstBoeWpcTblRepository extends JpaRepository<BTVL_MST_BOE_WPC_TBL, BigDecimal> {

    @Query(value = "SELECT\n" +
            "    bmbw.wpc_id as wpcId,\n" +
            "    bmbw.attribute1 as legalEntity,\n" +
            "    bmbw.attribute2 as licenseDate,\n" +
            "    bmbw.licence_no as licenceNo,\n" +
            "    bmbw.port_code as portCode,\n" +
            "    bmbw.item_code as itemCode,\n" +
            "    bmbw.wpc_qty as wpcQty,\n" +
            "    bmbw.purge_flag as purgeFlag,\n" +
            "    bmbw.licence_date as licenceDate,\n" +
            "    nvl((\n" +
            "        SELECT\n" +
            "            SUM(bwt.consumed_qty)\n" +
            "        FROM\n" +
            "            btvl_boe_wpc_transaction_tbl bwt\n" +
            "        WHERE\n" +
            "                bmbw.wpc_id = bwt.wpc_id\n" +
            "            AND bwt.boe_cancel_flag IS NULL\n" +
            "            AND bwt.status = 'VALIDATED'\n" +
            "    ),\n" +
            "        bmbw.balance_qty) balanceQty,\n" +
            "    bmbw.end_date as endDate\n" +
            "FROM\n" +
            "    btvl_mst_boe_wpc_tbl bmbw\n" +
            "WHERE\n" +
            "    bmbw.purge_flag = 0", nativeQuery = true)
    List<WpcMaster> fetchWpcRecord();

    BTVL_MST_BOE_WPC_TBL findByWpcIdAndPurgeFlag(BigDecimal wpcId, Integer purgeFlag);



 @Query(nativeQuery = true,
    value = "select t1.WPC_ID, \n" +
            "       t1.PO_NO, \n" +
            "\t   t1.OU_NO, \n" +
            "       t1.LICENCE_NO, \n" +
            "       t1.LE_NAME, \n" +
            "\t   t1.ITEM_CODE, \n" +
            "\t   t1.ITEM_DESCRIPTION, \n" +
            "\t   t1.PORT_CODE, \n" +
            "\t   t1.BALANCE_QTY, \n" +
            "\t   t1.END_DATE, \n" +
            "\t   t1.PURGE_FLAG, \n" +
            "\t   t1.ATTRIBUTE1, \n" +
            "\t   t1.ATTRIBUTE2, \n" +
            "\t   t1.ATTRIBUTE3, \n" +
            "\t   t1.ATTRIBUTE4, \n" +
            "\t   t1.ATTRIBUTE5, \n" +
            "\t   t1.ATTRIBUTE6, \n" +
            "\t   t1.ATTRIBUTE7, \n" +
            "\t   t1.ATTRIBUTE8, \n" +
            "\t   t1.ATTRIBUTE9, \n" +
            "\t   t1.ATTRIBUTE10, \n" +
            "\t   t1.ATTRIBUTE11, \n" +
            "\t   t1.ATTRIBUTE12, \n" +
            "\t   t1.ATTRIBUTE13, \n" +
            "\t   t1.ATTRIBUTE14, \n" +
            "\t   t1.ATTRIBUTE15, \n" +
            "\t   t1.CREATION_DATE, \n" +
            "\t   t1.CREATEDBY, \n" +
            "\t   t1.MODIFIED_DATE, \n" +
            "\t   t1.MODIFIEDBY, \n" +
            "\t   t1.LICENCE_DATE, \n" +
            "\t   t1.INVOICE_NO, \n" +
            "\t   t1.INVOICE_DATE, \n" +
            "\t   t1.LOB, \n" +
            "\t   t1.BOE_NO, \n" +
            "\t   t1.BOE_DATE, \n" +
            "\t   t1.CONSUME_QUANTITY, \n" +
            "\t   t1.WPC_QTY\n" +
            "From \n" +
            "    BTVL_MST_BOE_WPC_TBL t1\n" +
            "    where \n" +
            "     (:from_date IS NULL OR :to_date IS NULL OR \n" +
            "                     (TRUNC(t1.creation_date) >= :from_date AND TRUNC(t1.creation_date) <= :to_date)) \n" +
            "      AND (:lob IS NULL OR UPPER(:lob) = UPPER(t1.LOB))\n" +
            "       AND (:end_date IS NULL OR (:end_date)=(TRUNC(t1.END_DATE)))")
    List<BTVL_MST_BOE_WPC_TBL> getWpcData(String lob, LocalDate from_date, LocalDate to_date, LocalDate end_date);

}
