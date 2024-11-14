package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_EPCG_TBL;
import com.airtel.buyer.airtelboe.repository.EpcgMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface BtvlMstBoeEpcgTblRepository extends JpaRepository<BTVL_MST_BOE_EPCG_TBL, BigDecimal> {

    @Query(value = "SELECT\n" +
            "    bmbe.epcg_id as epcgId,\n" +
            "    bmbe.licence_no as licenceNo,\n" +
            "    bmbe.le_name as leName,\n" +
            "    bmbe.port_code as portCode,\n" +
            "    bmbe.item_code as itemCode,\n" +
            "    bmbe.tot_item_qty_epcg_lc as totItemQty,\n" +
            "    bmbe.purge_flag as purgeFlag,\n" +
            "    nvl(bmbe.tot_item_qty_epcg_lc, 0) - nvl((\n" +
            "        SELECT\n" +
            "            SUM(bet.consumed_qty)\n" +
            "        FROM\n" +
            "            btvl_boe_epcg_transaction_tbl bet\n" +
            "        WHERE\n" +
            "                bmbe.epcg_id = bet.epcg_id\n" +
            "            AND bet.boe_cancel_flag IS NULL\n" +
            "            AND bet.status = 'VALIDATED'\n" +
            "    ),\n" +
            "    bmbe.balance_qty) as balanceQty,\n" +
            "    bmbe.end_date as endDate,\n" +
            "    bmbe.licence_date as licenceDate,\n" +
            "    bmbe.attribute1 as  itemSerialNum,\n" +
            "    bmbe.item_decription as itemDescription,\n" +
            "    bmbe.attribute2 as lob\n" +
            "FROM\n" +
            "    btvl_mst_boe_epcg_tbl bmbe\n" +
            "WHERE\n" +
            "    bmbe.purge_flag = 0", nativeQuery = true)
    List<EpcgMaster> fetchEpcgRecord();

    BTVL_MST_BOE_EPCG_TBL findByEpcgIdAndPurgeFlag(BigDecimal epcgId, Integer purgeFlag);


 @Query(nativeQuery = true,
    value = "select t1.EPCG_ID, \n" +
            "\t   t1.LICENCE_NO, \n" +
            "\t   t1.LE_NAME, \n" +
            "\t   t1.ITEM_CODE, \n" +
            "\t   t1.ITEM_DECRIPTION, \n" +
            "\t   t1.PORT_CODE, \n" +
            "\t   t1.TOT_ITEM_QTY_EPCG_LC, \n" +
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
            "\t   t1.PORT_ISSUE, \n" +
            "\t   t1.DUTY_SAVED, \n" +
            "\t   t1.REG_NUM, \n" +
            "\t   t1.INVOICE_NUMBER, \n" +
            "\t   t1.INVOICE_DATE, \n" +
            "\t   t1.LOB, \n" +
            "\t   t1.BOE_NO, \n" +
            "\t   t1.BOE_DATE \n" +
//            "\t   (t1.TOT_ITEM_QTY_EPCG_LC - t1.BALANCE_QTY) AS DEBIT_QTY \n" +
            "From \n" +
            "    BTVL_MST_BOE_EPCG_TBL t1\n" +
            "      where \n" +
            "     (:from_date IS NULL OR :to_date IS NULL OR \n" +
            "                     (TRUNC(t1.CREATION_DATE) >= :from_date AND TRUNC(t1.CREATION_DATE) <= :to_date)) \n" +
            "      AND (:lob IS NULL OR UPPER(:lob) = UPPER(t1.LOB))\n" +
            "       AND (:end_date IS NULL OR (:end_date)=(TRUNC(t1.END_DATE)))\n" +
            "       AND (:le IS NULL OR UPPER(:le) = UPPER(t1.LE_NAME))\n" +
            "        AND (:port IS NULL OR UPPER(:port) = UPPER(t1.PORT_CODE))")
    List<BTVL_MST_BOE_EPCG_TBL> getData(String lob, LocalDate from_date, LocalDate to_date, LocalDate end_date,
                                        String le, String port);


}
