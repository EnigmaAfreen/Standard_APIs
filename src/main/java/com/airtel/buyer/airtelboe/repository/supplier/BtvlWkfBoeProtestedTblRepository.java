package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_PROTESTED_TBL;
import com.airtel.buyer.airtelboe.repository.ProtestedBoe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BtvlWkfBoeProtestedTblRepository extends JpaRepository<BTVL_WKF_BOE_PROTESTED_TBL, BigDecimal> {

    @Query(value = "SELECT\n" +
            "    btvl_wkf_boe_protested_tbl.shipment_id        AS shipmentId,\n" +
            "    btvl_wkf_boe_protested_tbl.po_no              AS poNo,\n" +
            "    btvl_wkf_boe_protested_tbl.port_code          AS portCode,\n" +
            "    btvl_wkf_boe_protested_tbl.reason_for_protest AS reasonForProtest,\n" +
            "    btvl_wkf_boe_protested_tbl.action_status      AS actionStatus,\n" +
            "    btvl_wkf_boe_protested_tbl.boe_no             AS boeNo,\n" +
            "    btvl_wkf_boe_protested_tbl.boe_date           AS boeDate,\n" +
            "    btvl_wkf_boe_protested_tbl.boe_protest_date   AS boeProtestDate,\n" +
//            "    btvl_wkf_boe_protested_tbl.purge_flag         AS purgeFlag,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute1         AS attribute1,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute2         AS attribute2,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute3         AS attribute3,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute4         AS attribute4,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute5         AS attribute5,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute6         AS attribute6,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute7         AS attribute7,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute8         AS attribute8,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute9         AS attribute9,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute10        AS attribute10,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute11        AS attribute11,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute12        AS attribute12,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute13        AS attribute13,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute14        AS attribute14,\n" +
//            "    btvl_wkf_boe_protested_tbl.attribute15        AS attribute15,\n" +
            "    btvl_wkf_boe_protested_tbl.creation_date      AS creationDate,\n" +
//            "    btvl_wkf_boe_protested_tbl.createdby          AS createdBy,\n" +
//            "    btvl_wkf_boe_protested_tbl.modified_date      AS modifiedDate,\n" +
//            "    btvl_wkf_boe_protested_tbl.modifiedby         AS modifiedBy,\n" +
//            "    btvl_wkf_boe_protested_tbl.assigned_cha_id    AS assignedChaId,\n" +
            "    btvl_wkf_boe_protested_tbl.action_date        AS actionDate,\n" +
            "    btvl_wkf_boe_protested_tbl.invoice_numbers    AS invoiceNumbers,\n" +
            "    btvl_wkf_boe_protested_tbl.vendor_name        AS vendorName,\n" +
            "    btvl_wkf_boe_protested_tbl.ou_name            AS ouName,\n" +
            "    (\n" +
            "        SELECT DISTINCT\n" +
            "            t2.cha_name\n" +
            "        FROM\n" +
            "            btvl_mst_boe_cha_tbl t2\n" +
            "        WHERE\n" +
            "            t2.cha_code = btvl_wkf_boe_protested_tbl.assigned_cha_id\n" +
            "    )                                             AS chaName\n" +
            "FROM\n" +
            "    btvl_wkf_boe_protested_tbl\n" +
            "WHERE\n" +
            "    btvl_wkf_boe_protested_tbl.purge_flag = 0\n" +
            "    AND ( :bndBoeNo IS NULL OR UPPER(btvl_wkf_boe_protested_tbl.boe_no) = UPPER(:bndBoeNo) )\n" +
            "    AND ( :bndShipmetId IS NULL OR btvl_wkf_boe_protested_tbl.shipment_id = :bndShipmetId )\n" +
            "    AND ( :bndStatus IS NULL OR UPPER(btvl_wkf_boe_protested_tbl.action_status) = UPPER(:bndStatus) )\n" +
            "    AND ( :bndValidFromDate IS NULL OR :bndValidToDate IS NULL OR \n" +
            "          (TRUNC(btvl_wkf_boe_protested_tbl.creation_date) >= :bndValidFromDate AND \n" +
            "           TRUNC(btvl_wkf_boe_protested_tbl.creation_date) <= :bndValidToDate) )\n" +
            "ORDER BY\n" +
            "    btvl_wkf_boe_protested_tbl.shipment_id DESC\n",
            countQuery = "select count(*) from btvl_wkf_boe_protested_tbl",
            nativeQuery = true)
    Page<ProtestedBoe> fetchProtestedBoeRecords(@Param("bndBoeNo") String bndBoeNo,
                                                @Param("bndShipmetId") BigDecimal bndShipmetId,
                                                @Param("bndStatus") String bndStatus,
                                                @Param("bndValidFromDate") LocalDate bndValidFromDate,
                                                @Param("bndValidToDate") LocalDate bndValidToDate,
                                                Pageable pageable);

    BTVL_WKF_BOE_PROTESTED_TBL findByShipmentIdAndPurgeFlag(BigDecimal shipmentId, Integer purgeFlag);

}
