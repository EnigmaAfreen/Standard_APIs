package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.repository.Cco;
import com.airtel.buyer.airtelboe.repository.Ip;
import com.airtel.buyer.airtelboe.repository.OfflineCco;
import com.airtel.buyer.airtelboe.repository.ChaAssignment;
import com.airtel.buyer.airtelboe.repository.ChaReAssgnmtData;
import com.airtel.buyer.airtelboe.repository.ScmDashData;
import com.airtel.buyer.airtelboe.repository.WpcInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BtvlWkfBoeShipmentTblRepository extends JpaRepository<BTVL_WKF_BOE_SHIPMENT_TBL, BigDecimal> {

    BTVL_WKF_BOE_SHIPMENT_TBL findByShipmentId(BigDecimal shipmentId);

    @Query(nativeQuery = true,
            value = "SELECT   \n" +
                    "    bs.ou_name AS ouName,    \n" +
                    "    bs.shipment_id AS airtelBoeRefNo,   \n" +
                    "    bs.boe_no AS boeNo, \n" +
                    "    bs.boe_date AS boeDate, \n" +
                    "    bs.awb_bol AS awbBlNo,  \n" +
                    "    bs.po_no AS poNo, \n" +
                    "    bs.invoice_numbers AS invoiceNo,  \n" +
                    "    bs.vendor_name AS partnerName,  \n" +
                    "    bs.inco_term AS incoTerm,  \n" +
                    "    bs.shipment_mode AS modeOfShipment,  \n" +
                    "    bs.shipment_arrival_date AS shipmentArrivalDate,                          \n" +
                    "    bs.destination_port AS destinationPort, \n" +
                    "    t2.cha_name AS chaAgent, \n" +
                    "    bs.status AS status,\n" +
                    "    ts.ref_description AS StatusDesc, \n" +
                    "    bs.payment_date AS paymentDate, \n" +
                    "    bs.bucket_no AS bucketNo, \n" +
                    "    bs.creation_date AS creationDate, \n" +
                    "    bs.org_id AS orgId, \n" +
                    "    bs.lob AS lob,  \n" +
                    "    bs.assigned_cha_id AS assignedChaId, \n" +
                    "    t3.attribute2 AS dutyAmount \n" +
                    "FROM   \n" +
                    "    btvl_wkf_boe_shipment_tbl bs  \n" +
                    "LEFT JOIN \n" +
                    "    (SELECT DISTINCT cha_code, cha_name \n" +
                    "     FROM btvl_mst_boe_cha_tbl) t2  \n" +
                    "ON  \n" +
                    "    t2.cha_code = bs.assigned_cha_id \n" +
                    "LEFT JOIN \n" +
                    "    (SELECT ref_id, ref_no, ref_description \n" +
                    "     FROM btvl_mst_ref_tbl \n" +
                    "     WHERE ref_id = 12) ts  \n" +
                    "ON  \n" +
                    "    ts.ref_no = bs.status \n" +
                    "LEFT JOIN \n" +
                    "    (SELECT t3.SHIPMENT_ID, MAX(t3.boe_header_id) AS boe_header_id, MAX(t3.attribute2) AS attribute2 \n" +
                    "     FROM BTVL_WKF_BOE_CHK_H_TBL t3 \n" +
                    "     GROUP BY t3.SHIPMENT_ID) t3  \n" +
                    "ON  \n" +
                    "    t3.SHIPMENT_ID = bs.SHIPMENT_ID \n" +
                    "    \n" +
                    "WHERE \n" +
                    "    (:bucket IS NULL OR UPPER(:bucket) = UPPER(bs.bucket_no)) \n" +
                    " AND (:lob IS NULL OR EXISTS (\n" +
                    "        SELECT 1\n" +
                    "        FROM (SELECT REGEXP_SUBSTR(:lob, '[^,]+', 1, LEVEL) AS lob_value\n" +
                    "              FROM DUAL\n" +
                    "              CONNECT BY REGEXP_SUBSTR(:lob, '[^,]+', 1, LEVEL) IS NOT NULL)\n" +
                    "        WHERE UPPER(lob_value) = UPPER(bs.lob)\n" +
                    "    ))AND\n" +
                    "    (:ou_name IS NULL OR UPPER(:ou_name) = UPPER(bs.ou_name)) AND\n" +
                    "    (:airtel_boe_ref_no IS NULL OR UPPER(:airtel_boe_ref_no) = UPPER(bs.shipment_id)) \n" +
                    "    AND (:start_date IS NULL OR :end_date IS NULL OR  \n" +
                    "         (TRUNC(bs.creation_date) >= :start_date AND TRUNC(bs.creation_date) <= :end_date)) \n" +
                    "    AND (:invoice_num IS NULL OR UPPER(:invoice_num) = UPPER(bs.invoice_numbers)) \n" +
                    "    AND (:partner_name IS NULL OR UPPER(:partner_name) = UPPER(bs.vendor_name)) \n" +
                    "    AND (:boe_num IS NULL OR UPPER(:boe_num) = UPPER(bs.boe_no)) \n" +
                    "    AND (:cha_name IS NULL OR UPPER(:cha_name) = UPPER(t2.cha_name)) \n" +
                    "    AND (:destination_port IS NULL OR UPPER(:destination_port) = UPPER(bs.destination_port)) \n" +
                    "ORDER BY  \n" +
                    "    bs.shipment_id DESC")
    Page<ScmDashData> getScmDashData(BigDecimal bucket, BigDecimal airtel_boe_ref_no, LocalDate start_date,
                                     LocalDate end_date, String invoice_num, String partner_name,
                                     String boe_num, String cha_name, String destination_port, String lob, String ou_name, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT  \n" +
                    "    bs.ou_name AS ouName,  \n" +
                    "    bs.shipment_id AS airtelBoeRefNo, \n" +
                    "    bs.boe_no AS boeNo, \n" +
                    "    bs.boe_date AS boeDate, \n" +
                    "    bs.awb_bol AS awbBlNo, \n" +
                    "    bs.po_no AS poNo, \n" +
                    "    bs.invoice_numbers AS invoiceNo, \n" +
                    "    bs.vendor_name AS partnerName,  \n" +
                    "    bs.inco_term AS incoTerm,  \n" +
                    "    bs.shipment_mode AS modeOfShipment,  \n" +
                    "    bs.shipment_arrival_date AS shipmentArrivalDate, \n" +
                    "    bs.destination_port AS destinationPort, \n" +
                    "    t2.cha_name AS chaAgent, \n" +
                    "    bs.status AS status,\n" +
                    "    ts.ref_description AS StatusDesc, \n" +
                    "    bs.payment_date AS paymentDate, \n" +
                    "    bs.bucket_no AS bucketNo, \n" +
                    "    bs.creation_date AS creationDate, \n" +
                    "    bs.org_id AS orgId, \n" +
                    "    bs.lob AS lob,  \n" +
                    "    bs.assigned_cha_id AS assignedChaId, \n" +
                    "    t3.attribute2 AS dutyAmount \n" +
                    "FROM  \n" +
                    "    btvl_wkf_boe_shipment_tbl bs  \n" +
                    "LEFT JOIN \n" +
                    "    (SELECT DISTINCT cha_code, cha_name \n" +
                    "     FROM btvl_mst_boe_cha_tbl) t2  \n" +
                    "ON  \n" +
                    "    t2.cha_code = bs.assigned_cha_id \n" +
                    "LEFT JOIN \n" +
                    "    (SELECT ref_id, ref_no, ref_description \n" +
                    "     FROM btvl_mst_ref_tbl \n" +
                    "     WHERE ref_id = 12) ts  \n" +
                    "ON  \n" +
                    "    ts.ref_no = bs.status \n" +
                    "LEFT JOIN \n" +
                    "    (SELECT t3.SHIPMENT_ID, MAX(t3.boe_header_id) AS boe_header_id, MAX(t3.attribute2) AS attribute2 \n" +
                    "     FROM BTVL_WKF_BOE_CHK_H_TBL t3 \n" +
                    "     GROUP BY t3.SHIPMENT_ID) t3  \n" +
                    "ON  \n" +
                    "    t3.SHIPMENT_ID = bs.SHIPMENT_ID \n" +
                    "WHERE \n" +
                    " (:lob IS NULL OR EXISTS (\n" +
                    "        SELECT 1\n" +
                    "        FROM (SELECT REGEXP_SUBSTR(:lob, '[^,]+', 1, LEVEL) AS lob_value\n" +
                    "              FROM DUAL\n" +
                    "              CONNECT BY REGEXP_SUBSTR(:lob, '[^,]+', 1, LEVEL) IS NOT NULL)\n" +
                    "        WHERE UPPER(lob_value) = UPPER(bs.lob)))\n" +
                    "ORDER BY  \n" +
                    "    bs.shipment_id DESC")
    List<ScmDashData> getBucketCount(String lob);

    @Query(value = "SELECT\n" +
            "    bs.ou_name AS ouName,\n" +
            "    bs.boe_no AS boeNo,\n" +
            "    bs.boe_date AS boeDate,\n" +
            "    bs.shipment_id AS shipmentId,\n" +
            "    bs.po_no AS poNo,\n" +
            "    bs.invoice_numbers AS invoiceNumbers,\n" +
            "    (\n" +
            "        SELECT DISTINCT\n" +
            "            t2.cha_name\n" +
            "        FROM\n" +
            "            btvl_mst_boe_cha_tbl t2\n" +
            "        WHERE\n" +
            "            t2.cha_code = bs.assigned_cha_id\n" +
            "    )              AS chaName,\n" +
            "    bs.shipment_mode AS shipmentMode,\n" +
            "    bs.shipment_arrival_date AS shipmentArrivalDate,\n" +
            "    bs.vendor_name AS vendorName,\n" +
            "    bs.org_id AS orgId,\n" +
            "    bs.status      AS status,\n" +
            "    (\n" +
            "        SELECT\n" +
            "            ts.ref_description\n" +
            "        FROM\n" +
            "            btvl_mst_ref_tbl ts\n" +
            "        WHERE\n" +
            "                ts.ref_id = 12\n" +
            "            AND ts.ref_no = bs.status\n" +
            "    )              AS statusDesc,\n" +
            "    bs.attribute1 AS attribute1,\n" +
            "    bs.attribute2 AS attribute2,\n" +
            "    bs.attribute3 AS attribute3,\n" +
            "    bs.attribute4 AS attribute4,\n" +
            "    bs.bucket_no AS bucketNo,\n" +
            "    bs.stage AS stage,\n" +
            "    bs.creation_date AS creationDate,\n" +
            "    bs.partner_vendor_code as partnerVendorCode,\n" +
            "    bch.attribute2 AS dutyAmount\n" +
            "FROM\n" +
            "    btvl_wkf_boe_shipment_tbl bs,\n" +
            "    btvl_wkf_boe_chk_h_tbl    bch\n" +
            "WHERE\n" +
            "        bs.shipment_id = bch.shipment_id\n" +
            "    AND bch.purge_flag = 0\n" +
            "    AND trunc(bs.creation_date) > trunc(sysdate - 180)\n" +
            "    AND bs.bucket_no IN (\n" +
            "        SELECT TRIM(REGEXP_SUBSTR(:bndBucketNo, '[^,]+', 1, LEVEL))\n" +
            "        FROM DUAL\n" +
            "        CONNECT BY REGEXP_SUBSTR(:bndBucketNo, '[^,]+', 1, LEVEL) IS NOT NULL\n" +
            "    )\n" +
            "    AND bs.purge_flag = 0\n" +
            "    AND ( :bndShipmentId IS NULL\n" +
            "          OR bs.shipment_id = :bndShipmentId )\n" +
            "    AND ( :bndOuName IS NULL\n" +
            "          OR bs.org_id = :bndOuName )\n" +
            "    AND ( :bndClearanceType IS NULL\n" +
            "          OR bs.attribute1 = :bndClearanceType )\n" +
            "    AND ( :bndValidFrom IS NULL\n" +
            "          OR :bndValidTo IS NULL\n" +
            "          OR ( trunc(bs.creation_date) >= :bndValidFrom\n" +
            "               AND trunc(bs.creation_date) <= :bndValidTo ) )",
            countQuery = "SELECT COUNT(*)\n" +
                    "FROM\n" +
                    "    btvl_wkf_boe_shipment_tbl bs,\n" +
                    "    btvl_wkf_boe_chk_h_tbl    bch\n" +
                    "WHERE\n" +
                    "        bs.shipment_id = bch.shipment_id\n" +
                    "    AND bch.purge_flag = 0\n" +
                    "    AND trunc(bs.creation_date) > trunc(sysdate - 180)\n" +
                    "    AND bs.bucket_no IN (\n" +
                    "        SELECT TRIM(REGEXP_SUBSTR(:bndBucketNo, '[^,]+', 1, LEVEL))\n" +
                    "        FROM DUAL\n" +
                    "        CONNECT BY REGEXP_SUBSTR(:bndBucketNo, '[^,]+', 1, LEVEL) IS NOT NULL\n" +
                    "    )\n" +
                    "    AND bs.purge_flag = 0\n" +
                    "    AND ( :bndShipmentId IS NULL\n" +
                    "          OR bs.shipment_id = :bndShipmentId )\n" +
                    "    AND ( :bndOuName IS NULL\n" +
                    "          OR bs.org_id = :bndOuName )\n" +
                    "    AND ( :bndClearanceType IS NULL\n" +
                    "          OR bs.attribute1 = :bndClearanceType )\n" +
                    "    AND ( :bndValidFrom IS NULL\n" +
                    "          OR :bndValidTo IS NULL\n" +
                    "          OR ( trunc(bs.creation_date) >= :bndValidFrom\n" +
                    "               AND trunc(bs.creation_date) <= :bndValidTo ) )",
            nativeQuery = true)
    Page<OfflineCco> fetchOfflineCcoRecords(
            @Param("bndBucketNo") String bndBucketNo,
            @Param("bndShipmentId") BigDecimal bndShipmentId,
            @Param("bndOuName") String bndOuName,
            @Param("bndClearanceType") String bndClearanceType,
            @Param("bndValidFrom") LocalDate bndValidFrom,
            @Param("bndValidTo") LocalDate bndValidTo,
            Pageable pageable);

    @Query(value = "SELECT\n" +
            "    bs.ou_name AS ouName,\n" +
            "    bs.boe_no AS boeNo,\n" +
            "    bs.boe_date AS boeDate,\n" +
            "    bs.shipment_id AS shipmentId,\n" +
            "    bs.po_no AS poNo,\n" +
            "    bs.invoice_numbers AS invoiceNumbers,\n" +
            "    (\n" +
            "        SELECT DISTINCT\n" +
            "            t2.cha_name\n" +
            "        FROM\n" +
            "            btvl_mst_boe_cha_tbl t2\n" +
            "        WHERE\n" +
            "            t2.cha_code = bs.assigned_cha_id\n" +
            "    )              AS chaName,\n" +
            "    bs.shipment_mode AS shipmentMode,\n" +
            "    bs.shipment_arrival_date AS shipmentArrivalDate,\n" +
            "    bs.vendor_name AS vendorName,\n" +
            "    bs.org_id AS orgId,\n" +
            "    bs.status      AS status,\n" +
            "    (\n" +
            "        SELECT\n" +
            "            ts.ref_description\n" +
            "        FROM\n" +
            "            btvl_mst_ref_tbl ts\n" +
            "        WHERE\n" +
            "                ts.ref_id = 12\n" +
            "            AND ts.ref_no = bs.status\n" +
            "    )              AS statusDesc,\n" +
            "    bs.attribute1 AS attribute1,\n" +
            "    bs.attribute2 AS attribute2,\n" +
            "    bs.attribute3 AS attribute3,\n" +
            "    bs.attribute4 AS attribute4,\n" +
            "    bs.bucket_no AS bucketNo,\n" +
            "    bs.stage AS stage,\n" +
            "    bs.creation_date AS creationDate,\n" +
            "    bs.partner_vendor_code as partnerVendorCode,\n" +
            "    bch.attribute2 AS dutyAmount\n" +
            "FROM\n" +
            "    btvl_wkf_boe_shipment_tbl bs,\n" +
            "    btvl_wkf_boe_chk_h_tbl    bch\n" +
            "WHERE\n" +
            "        bs.shipment_id = bch.shipment_id\n" +
            "    AND bch.purge_flag = 0\n" +
            "    AND trunc(bs.creation_date) > trunc(sysdate - 180)\n" +
//            "    AND bs.bucket_no IN (\n" +
//            "        SELECT TRIM(REGEXP_SUBSTR(:bndBucketNo, '[^,]+', 1, LEVEL))\n" +
//            "        FROM DUAL\n" +
//            "        CONNECT BY REGEXP_SUBSTR(:bndBucketNo, '[^,]+', 1, LEVEL) IS NOT NULL\n" +
//            "    )\n" +
            "    AND ( :bndBucketNo IS NULL\n" +
            "          OR bs.bucket_no = :bndBucketNo )\n" +
            "    AND bs.purge_flag = 0\n" +
            "    AND ( :bndShipmentId IS NULL\n" +
            "          OR bs.shipment_id = :bndShipmentId )\n" +
            "    AND ( :bndOuName IS NULL\n" +
            "          OR bs.org_id = :bndOuName )\n" +
            "    AND ( :bndClearanceType IS NULL\n" +
            "          OR bs.attribute1 = :bndClearanceType )\n" +
            "    AND ( :bndValidFrom IS NULL\n" +
            "          OR :bndValidTo IS NULL\n" +
            "          OR ( trunc(bs.creation_date) >= :bndValidFrom\n" +
            "               AND trunc(bs.creation_date) <= :bndValidTo ) )\n" +
            "    AND bs.assigned_cco_id = :bndCcoId",
            countQuery = "SELECT COUNT(*)\n" +
                    "FROM\n" +
                    "    btvl_wkf_boe_shipment_tbl bs,\n" +
                    "    btvl_wkf_boe_chk_h_tbl    bch\n" +
                    "WHERE\n" +
                    "        bs.shipment_id = bch.shipment_id\n" +
                    "    AND bch.purge_flag = 0\n" +
                    "    AND trunc(bs.creation_date) > trunc(sysdate - 180)\n" +
//                    "    AND bs.bucket_no IN (\n" +
//                    "        SELECT TRIM(REGEXP_SUBSTR(:bndBucketNo, '[^,]+', 1, LEVEL))\n" +
//                    "        FROM DUAL\n" +
//                    "        CONNECT BY REGEXP_SUBSTR(:bndBucketNo, '[^,]+', 1, LEVEL) IS NOT NULL\n" +
//                    "    )\n" +
                    "    AND ( :bndBucketNo IS NULL\n" +
                    "          OR bs.bucket_no = :bndBucketNo )\n" +
                    "    AND bs.purge_flag = 0\n" +
                    "    AND ( :bndShipmentId IS NULL\n" +
                    "          OR bs.shipment_id = :bndShipmentId )\n" +
                    "    AND ( :bndOuName IS NULL\n" +
                    "          OR bs.org_id = :bndOuName )\n" +
                    "    AND ( :bndClearanceType IS NULL\n" +
                    "          OR bs.attribute1 = :bndClearanceType )\n" +
                    "    AND ( :bndValidFrom IS NULL\n" +
                    "          OR :bndValidTo IS NULL\n" +
                    "          OR ( trunc(bs.creation_date) >= :bndValidFrom\n" +
                    "               AND trunc(bs.creation_date) <= :bndValidTo ) )\n" +
                    "    AND upper(bs.assigned_cco_id) = upper(:bndCcoId)",
            nativeQuery = true)
    Page<Cco> fetchCcoRecords(
            @Param("bndBucketNo") BigDecimal bndBucketNo,
            @Param("bndShipmentId") BigDecimal bndShipmentId,
            @Param("bndOuName") String bndOuName,
            @Param("bndClearanceType") String bndClearanceType,
            @Param("bndValidFrom") LocalDate bndValidFrom,
            @Param("bndValidTo") LocalDate bndValidTo,
            @Param("bndCcoId") String bndCcoId,
            Pageable pageable);

    @Query(value = "SELECT\n" +
            "    btvl_wkf_boe_shipment_tbl.shipment_id AS shipmentId,\n" +
            "    btvl_wkf_boe_shipment_tbl.po_no AS poNo,\n" +
            "    btvl_wkf_boe_shipment_tbl.boe_no AS boeNo,\n" +
            "    btvl_wkf_boe_shipment_tbl.boe_date AS boeDate,\n" +
            "    btvl_wkf_boe_shipment_tbl.status AS status,\n" +
            "    btvl_wkf_boe_shipment_tbl.creation_date AS creationDate,\n" +
            "    btvl_wkf_boe_shipment_tbl.assigned_cha_id AS assignedChaId,\n" +
            "    btvl_wkf_boe_shipment_tbl.bucket_no AS bucketNo,\n" +
            "    btvl_wkf_boe_shipment_tbl.invoice_numbers AS invoiceNumbers,\n" +
            "    btvl_wkf_boe_shipment_tbl.ou_name AS ouName,\n" +
            "    btvl_wkf_boe_shipment_tbl.vendor_name AS vendorName,\n" +
            "    btvl_wkf_boe_shipment_tbl.org_id AS orgId,\n" +
            "    btvl_wkf_boe_shipment_tbl.partner_vendor_code AS partnerVendorCode,\n" +
            "    btvl_wkf_boe_shipment_tbl.lob AS lob,\n" +
            "    btvl_wkf_boe_shipment_tbl.attribute1 AS attribute1,\n" +
            "    btvl_wkf_boe_chk_h_tbl.attribute2 AS attribute2,\n" +
            "    (\n" +
            "        SELECT DISTINCT\n" +
            "            btvl_mst_boe_cha_tbl.cha_name\n" +
            "        FROM\n" +
            "            btvl_mst_boe_cha_tbl\n" +
            "        WHERE\n" +
            "            btvl_wkf_boe_shipment_tbl.assigned_cha_id = btvl_mst_boe_cha_tbl.cha_code\n" +
            "    ) AS chaAgent,\n" +
            "    btvl_wkf_boe_shipment_tbl.attribute3 AS attribute3\n" +
            "FROM \n" +
            "    btvl_wkf_boe_shipment_tbl,\n" +
            "    btvl_wkf_boe_chk_h_tbl\n" +
            "WHERE\n" +
            "    btvl_wkf_boe_shipment_tbl.bucket_no IN (\n" +
            "        SELECT\n" +
            "            TRIM(regexp_substr(:bndBucketNo, '[^,]+', 1, level))\n" +
            "        FROM\n" +
            "            dual\n" +
            "        CONNECT BY\n" +
            "            regexp_substr(:bndBucketNo, '[^,]+', 1, level) IS NOT NULL\n" +
            "    )\n" +
            "    AND ( btvl_wkf_boe_chk_h_tbl.shipment_id = btvl_wkf_boe_shipment_tbl.shipment_id )\n" +
            "    AND ( btvl_wkf_boe_chk_h_tbl.purge_flag = 0 )\n" +
            "    AND ( :bndOuName IS NULL\n" +
            "          OR btvl_wkf_boe_shipment_tbl.org_id = :bndOuName )\n" +
            "    AND ( :bndBoeNo IS NULL\n" +
            "          OR upper(btvl_wkf_boe_shipment_tbl.boe_no) = upper(:bndBoeNo) )\n" +
            "    AND ( :bndShipmentId IS NULL\n" +
            "          OR btvl_wkf_boe_shipment_tbl.shipment_id = :bndShipmentId )\n" +
            "    AND ( :bndRms IS NULL\n" +
            "          OR btvl_wkf_boe_shipment_tbl.attribute1 = :bndRms )\n" +
            "    AND ( :bndValidFrom IS NULL\n" +
            "          OR :bndValidTo IS NULL\n" +
            "          OR ( trunc(btvl_wkf_boe_shipment_tbl.creation_date) >= :bndValidFrom\n" +
            "               AND trunc(btvl_wkf_boe_shipment_tbl.creation_date) <= :bndValidTo ) )\n" +
            "ORDER BY\n" +
            "    btvl_wkf_boe_shipment_tbl.shipment_id DESC\n",
            countQuery = "SELECT COUNT(*)\n" +
                    "FROM \n" +
                    "    btvl_wkf_boe_shipment_tbl\n" +
                    "JOIN \n" +
                    "    btvl_wkf_boe_chk_h_tbl ON btvl_wkf_boe_chk_h_tbl.shipment_id = btvl_wkf_boe_shipment_tbl.shipment_id\n" +
                    "WHERE\n" +
                    "    btvl_wkf_boe_shipment_tbl.bucket_no IN (\n" +
                    "        SELECT\n" +
                    "            TRIM(regexp_substr(:bndBucketNo, '[^,]+', 1, level))\n" +
                    "        FROM\n" +
                    "            dual\n" +
                    "        CONNECT BY\n" +
                    "            regexp_substr(:bndBucketNo, '[^,]+', 1, level) IS NOT NULL\n" +
                    "    )\n" +
                    "    AND ( btvl_wkf_boe_chk_h_tbl.purge_flag = 0 )\n" +
                    "    AND ( :bndOuName IS NULL\n" +
                    "          OR btvl_wkf_boe_shipment_tbl.org_id = :bndOuName )\n" +
                    "    AND ( :bndBoeNo IS NULL\n" +
                    "          OR upper(btvl_wkf_boe_shipment_tbl.boe_no) = upper(:bndBoeNo) )\n" +
                    "    AND ( :bndShipmentId IS NULL\n" +
                    "          OR btvl_wkf_boe_shipment_tbl.shipment_id = :bndShipmentId )\n" +
                    "    AND ( :bndRms IS NULL\n" +
                    "          OR btvl_wkf_boe_shipment_tbl.attribute1 = :bndRms )\n" +
                    "    AND ( :bndValidFrom IS NULL\n" +
                    "          OR :bndValidTo IS NULL\n" +
                    "          OR ( trunc(btvl_wkf_boe_shipment_tbl.creation_date) >= :bndValidFrom\n" +
                    "               AND trunc(btvl_wkf_boe_shipment_tbl.creation_date) <= :bndValidTo ) )",
            nativeQuery = true)
    Page<Ip> fetchIpRecords(
            @Param("bndBucketNo") String bndBucketNo,
            @Param("bndOuName") String bndOuName,
            @Param("bndBoeNo") String bndBoeNo,
            @Param("bndShipmentId") BigDecimal bndShipmentId,
            @Param("bndRms") String bndRms,
            @Param("bndValidFrom") LocalDate bndValidFrom,
            @Param("bndValidTo") LocalDate bndValidTo,
            Pageable pageable
    );

    @Query(value="SELECT\n" +
            "    t1.cha_id,\n" +
            "    t1.le_name,\n" +
            "    t1.ou,\n" +
            "    t1.port_code,\n" +
            "    t1.cha_code,\n" +
            "    t1.CHA_EMAIL,\n" +
            "    t1.CHA_NAME,\n" +
            "    NVL(t2.tasks,0) as ASSIGNED_TASKS\n" +
            "FROM\n" +
            "    btvl_mst_boe_cha_tbl t1\n" +
            "    LEFT OUTER JOIN (\n" +
            "        SELECT\n" +
            "            assigned_cha_id,\n" +
            "            le_name,\n" +
            "            ou_name,\n" +
            "            destination_port,\n" +
            "            COUNT(*) AS tasks\n" +
            "        FROM\n" +
            "            btvl_wkf_boe_shipment_tbl\n" +
            "        where assigned_cha_id is not null and assigned_cha_id <> 'Assignment Failed'\n" +
            "        and status < 11\n" +
            "        GROUP BY\n" +
            "            assigned_cha_id,le_name,ou_name,destination_port\n" +
            "    ) t2 ON t1.cha_code = t2.assigned_cha_id and t1.le_name=t2.le_name and t1.ou=t2.ou_name and t1.port_code=t2.destination_port\n" +
            "order by ASSIGNED_TASKS asc,t1.cha_id asc",nativeQuery = true)
    List<ChaAssignment> findChaAgentsByLeOuPortCode(String bndLeName, String bndOu, String bndPortCode);

  //query added by hari to get reassignment page data
    @Query(nativeQuery = true,
            value = "select shipment_id as shipmentId,         \n" +
                    "assigned_cha_id as assignedChaId,         \n" +
                    "stage as stage,         \n" +
                    "status as status,         \n" +
                    "bucket_no as bucketNo,              \n" +
                    "(Select distinct btvl_mst_boe_cha_tbl.cha_name from btvl_mst_boe_cha_tbl where BTVL_WKF_BOE_SHIPMENT_TBL.ASSIGNED_CHA_ID=btvl_mst_boe_cha_tbl.cha_code) as chaAgent,            \n" +
                    "(Select ref_description from btvl_mst_ref_tbl where BTVL_WKF_BOE_SHIPMENT_TBL.STATUS=btvl_mst_ref_tbl.ref_no and btvl_mst_ref_tbl.ref_id=12) AS statusRef,         \n" +
                    "LE_NAME as leName,         \n" +
                    "OU_NAME as ouName,         \n" +
                    "DESTINATION_PORT as destinationPort,       \n" +
                    "(select distinct PORT_NAME from BTVL_MST_BOE_PORT_TBL where PORT_CODE=DESTINATION_PORT) \"portName\",     \n" +
                    "INVOICE_NUMBERS as invoiceNumber ,    \n" +
                    "LOB as lob    \n" +
                    "from BTVL_WKF_BOE_SHIPMENT_TBL  \n" +
                    "where status <=4 and assigned_cha_id=:bndChaId")
    List<ChaReAssgnmtData> fetchChaReAssignmentPageData(String bndChaId);


    List<BTVL_WKF_BOE_SHIPMENT_TBL> findByShipmentIdIn(List<BigDecimal> shipmentId);

 @Query(nativeQuery = true,
            value = "    SELECT\n" +
                    "        bws.ou_name as ouName,\n" +
                    "        bws.boe_no as boeNo,\n" +
                    "        bws.boe_date as boeDate,\n" +
                    "        bws.awb_bol as awbBol,\n" +
                    "        bws.po_no as poNo,\n" +
                    "        bws.shipment_id as airtelBoeRefNo,\n" +
                    "        bws.invoice_numbers as invoiceNumber,\n" +
                    "        bws.vendor_name as vendorName,\n" +
                    "        bws.inco_term as incoTerm,\n" +
                    "        bws.shipment_mode as shipmentMode,\n" +
                    "        bws.shipment_arrival_date as shipmentArrivalDate,\n" +
                    "        bws.destination_port as destinationPort,\n" +
                    "        bws.assigned_cha_id as assignedChaId,\n" +
                    "        bws.status as status,\n" +
                    "        bws.lob as lob,\n" +
                    "        bws.org_id as orgId,\n" +
                    "        bws.creation_date as creationDate,\n" +
                    "        ts.ref_description AS \"StatusDesc\",\n" +
                    "        bca.cha_name AS chaAgent\n" +
                    "    FROM\n" +
                    "        btvl_wkf_boe_shipment_tbl bws\n" +
                    "    LEFT JOIN\n" +
                    "        btvl_mst_ref_tbl ts ON ts.ref_id = 12 AND ts.ref_no = bws.status\n" +
                    "    LEFT JOIN\n" +
                    "        btvl_mst_boe_cha_tbl bca ON bws.assigned_cha_id = bca.cha_id\n" +
                    "    WHERE\n" +
                    "        bws.flag1 IS NULL\n" +
                    "        AND bws.shipment_id IN (\n" +
                    "            SELECT\n" +
                    "                bsl.shipment_id\n" +
                    "            FROM\n" +
                    "                btvl_wkf_boe_ship_line_tbl bsl\n" +
                    "            WHERE\n" +
                    "                bsl.is_wpc_requiremnt = 'Y'\n" +
                    "        )\n" +
                    "        AND bws.assigned_cha_id <> 'Assignment Failed'\n" +
                    "        \n" +
                    "        AND (:lob IS NULL OR EXISTS (       \n" +
                    "                          SELECT 1       \n" +
                    "                          FROM (SELECT REGEXP_SUBSTR(:lob, '[^,]  ', 1, LEVEL) AS lob_value       \n" +
                    "                                FROM DUAL       \n" +
                    "                                CONNECT BY REGEXP_SUBSTR(:lob, '[^,]  ', 1, LEVEL) IS NOT NULL)       \n" +
                    "                          WHERE UPPER(lob_value) = UPPER(bws.lob)       \n" +
                    "                      ))AND       \n" +
                    "                      (:ou_name IS NULL OR UPPER(:ou_name) = UPPER(bws.ou_name)) AND       \n" +
                    "                      (:airtel_boe_ref_no IS NULL OR UPPER(:airtel_boe_ref_no) = UPPER(bws.shipment_id))        \n" +
                    "                      AND (:start_date IS NULL OR :end_date IS NULL OR         \n" +
                    "                           (TRUNC(bws.creation_date) >= :start_date AND TRUNC(bws.creation_date) <= :end_date))        \n" +
                    "                      AND (:invoice_num IS NULL OR UPPER(:invoice_num) = UPPER(bws.invoice_numbers))        \n" +
                    "                      AND (:partner_name IS NULL OR UPPER(:partner_name) = UPPER(bws.vendor_name))           \n" +
                    "        \n" +
                    "    ORDER BY\n" +
                    "        bws.shipment_id DESC")
    Page<WpcInfo> getWpcInfoDashData(BigDecimal airtel_boe_ref_no, LocalDate start_date,
                                     LocalDate end_date, String invoice_num, String partner_name, String lob, String ou_name, Pageable pageable);

}
