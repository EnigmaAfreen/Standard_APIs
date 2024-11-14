package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIP_LINE_TBL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlWkfBoeShipLineTblRepository extends JpaRepository<BTVL_WKF_BOE_SHIP_LINE_TBL, BigDecimal> {

//    @Query(value = "SELECT\n" +
//            "    invoice_number as invoiceNumber,\n" +
//            "    invoice_header_id as invoiceHeaderId,\n" +
//            "    partner_vendor_id as partnerVendorId,\n" +
//            "    po_header_id as poHeaderId\n" +
//            "FROM\n" +
//            "    btvl_wkf_invoice_header_tbl\n" +
//            "WHERE\n" +
//            "    invoice_status IN ( 3, 4, 5, 8, 10,\n" +
//            "                        15, 16 )\n" +
//            "    AND po_header_id = :bndPoHeaderId\n" +
//            "    AND invoice_header_id NOT IN (\n" +
//            "        SELECT\n" +
//            "            invoice_header_id\n" +
//            "        FROM\n" +
//            "            btvl_wkf_boe_ship_line_tbl\n" +
//            "    )\n" +
//            "    AND partner_vendor_id = :bndPartnerVendorId", nativeQuery = true)
//    List<Invoice> loadInvoiceList(BigDecimal bndPoHeaderId, BigDecimal bndPartnerVendorId);

    List<BTVL_WKF_BOE_SHIP_LINE_TBL> findByShipmentId(BigDecimal shipmentId);

}
