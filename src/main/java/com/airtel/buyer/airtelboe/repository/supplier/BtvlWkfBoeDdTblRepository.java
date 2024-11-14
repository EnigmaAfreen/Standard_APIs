package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_DD_TBL;
import com.airtel.buyer.airtelboe.repository.DdStatusData;
import com.airtel.buyer.airtelboe.repository.ScmDashData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BtvlWkfBoeDdTblRepository extends JpaRepository<BTVL_WKF_BOE_DD_TBL, BigDecimal> {


    @Query(nativeQuery = true,
            value = "SELECT                \n" +
                    "    dd.dd_id as ddId,                \n" +
                    "    ship.boe_no as boeNo,                \n" +
                    "    dd.shipment_id as airtelReferrenceNo,                \n" +
                    "    ship.awb_bol as shipmentAwbNo,                \n" +
                    "    ship.invoice_numbers as invoiceNumber,                \n" +
                    "    dd.demand_draft_no as documentNo,                \n" +
                    "    dd.amount as amount,                \n" +
                    "    dd.payable_location as payable,                \n" +
                    "    dd.port as port,                \n" +
                    "    dd.dd_date as ddDate,                \n" +
                    "    dd.dd_status as status,                \n" +
                    "    dd.dd_reference_no as ddReferrenceNo,                \n" +
                    "    (                \n" +
                    "        SELECT DISTINCT                \n" +
                    "            t.cha_name                \n" +
                    "        FROM                \n" +
                    "            btvl_mst_boe_cha_tbl t                \n" +
                    "        WHERE                \n" +
                    "            t.cha_code = ship.assigned_cha_id                \n" +
                    "    ) chaName,                \n" +
                    "    ship.PARTNER_VENDOR_CODE as partnerVendorCode               \n" +
                    "FROM                \n" +
                    "    btvl_wkf_boe_dd_tbl dd,                \n" +
                    "    btvl_wkf_boe_shipment_tbl ship                \n" +
                    "WHERE                \n" +
                    "    dd.shipment_id = ship.shipment_id                \n" +
                    "    AND dd.purge_flag = 0                \n" +
                    "    AND dd.dd_status IS NOT NULL \n" +
                    "    AND (:airtel_boe_ref_no IS NULL OR UPPER(:airtel_boe_ref_no) = UPPER(dd.shipment_id))        \n" +
                    "    AND (:start_date IS NULL OR :end_date IS NULL OR (TRUNC(dd.creation_date) >= :start_date AND TRUNC(dd.creation_date) <= :end_date))        \n" +
                    "    AND (:invoice_num IS NULL OR UPPER(:invoice_num) = UPPER(ship.invoice_numbers))\n" +
                    "    AND (:awb_bol IS NULL OR UPPER(:awb_bol) = UPPER(ship.awb_bol))\n" +
                    "    AND (:boe_no IS NULL OR UPPER(:boe_no) = UPPER(ship.boe_no))\n" +
                    "    AND (:dd_status IS NULL OR UPPER(:dd_status) = UPPER(dd.dd_status))\n" +
                    "ORDER BY                \n" +
                    "    dd.shipment_id DESC")
    Page<DdStatusData> getDdStatusDashData(BigDecimal airtel_boe_ref_no, LocalDate start_date,
                                           LocalDate end_date, String invoice_num,
                                           String boe_no, String dd_status, String awb_bol, Pageable pageable);

}
