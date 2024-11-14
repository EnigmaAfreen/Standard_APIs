package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIP_DOC_TBL;
import com.airtel.buyer.airtelboe.repository.ShipDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlWkfBoeShipDocTblRepository extends JpaRepository<BTVL_WKF_BOE_SHIP_DOC_TBL, BigDecimal> {

    @Query(value = "SELECT  (\n" +
            "        SELECT \n" +
            "             doc_name \n" +
            "                FROM \n" +
            "                 btvl_mst_partner_doc_tbl \n" +
            "                    WHERE \n" +
            "                        doc_id = t1.doc_id \n" +
            "                ) AS docName, doc_id AS docId, \n" +
            "                t1.attribute AS contentId \n" +
            "            FROM \n" +
            "                btvl_wkf_boe_ship_doc_tbl t1 \n" +
            "            WHERE \n" +
            "                t1.shipment_id = :bndShipmentId", nativeQuery = true)
    List<ShipDoc> fetchShipmentDocByShipmentId(BigDecimal bndShipmentId);


    @Query(value = "SELECT \n" +
            "    'INVOICE - '||inv_header.invoice_number  docName, \n" +
            "    doc.attribute  contentId \n" +
            "FROM \n" +
            "    btvl_wkf_invoice_doc_map_tbl doc, \n" +
            "    btvl_wkf_invoice_header_tbl inv_header \n" +
            "WHERE \n" +
            "    doc.invoice_header_id = inv_header.invoice_header_id \n" +
            "    AND doc.invoice_header_id IN ( \n" +
            "        SELECT DISTINCT \n" +
            "            ship.invoice_header_id \n" +
            "        FROM \n" +
            "            btvl_wkf_boe_ship_line_tbl ship \n" +
            "        WHERE \n" +
            "            ship.shipment_id =:bndShipmentId \n" +
            "    ) \n" +
            "    AND doc.doc_id = 0 \n" +
            "UNION ALL \n" +
            "SELECT \n" +
            "    partner_doc.doc_name docName, \n" +
            "    ship_doc.attribute contentId \n" +
            "FROM \n" +
            "    btvl_wkf_boe_ship_doc_tbl ship_doc, \n" +
            "    btvl_mst_partner_doc_tbl partner_doc \n" +
            "WHERE \n" +
            "    partner_doc.doc_id = ship_doc.doc_id \n" +
            "    AND ship_doc.shipment_id =:bndShipmentId", nativeQuery = true)
    List<ShipDoc> fetchMismatchDoc(BigDecimal bndShipmentId);

    List<BTVL_WKF_BOE_SHIP_DOC_TBL> findByShipmentId(BigDecimal shipmentId);
    List<BTVL_WKF_BOE_SHIP_DOC_TBL> findByShipmentIdAndPurgeFlag(BigDecimal shipmentId, Integer purgeFlag);
}
