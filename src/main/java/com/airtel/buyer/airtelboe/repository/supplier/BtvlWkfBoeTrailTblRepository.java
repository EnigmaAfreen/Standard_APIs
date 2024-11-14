package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_TRAIL_TBL;
import com.airtel.buyer.airtelboe.repository.BoeInfoReq;
import com.airtel.buyer.airtelboe.repository.BoeTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlWkfBoeTrailTblRepository extends JpaRepository<BTVL_WKF_BOE_TRAIL_TBL, BigDecimal> {

    BTVL_WKF_BOE_TRAIL_TBL findFirstByShipmentIdAndActionByAndActionAllIgnoreCaseOrderByTrailIdDesc(BigDecimal shipmentId, String actionBy, String action);


    @Query(value = "SELECT            \n" +
            "    trail_id as trailId,            \n" +
            "    shipment_id as shipmentId,            \n" +
            "    BUCKET_NO as bucketNo,            \n" +
            "    status as status,            \n" +
            "    comment_header as commentHeader,            \n" +
            "    comment_line as commentLine,            \n" +
            "    action_by as actionBy,            \n" +
            "     CREATION_DATE as creationDate            \n" +
            "FROM            \n" +
            "    btvl_wkf_boe_trail_tbl            \n" +
            "WHERE            \n" +
            "    action in ('RFI', 'RFI RESPONSE') and BUCKET_NO in (13,8,7,17)            \n" +
            "    AND action_by IN (            \n" +
            "        'CHA',            \n" +
            "        'SCM',  \n" +
            "        'IP'  \n" +
            "    ) and shipment_id=:shipmentId           \n" +
            "    ORDER BY            \n" +
            "    TRAIL_ID ASC", nativeQuery = true)
    List<BoeInfoReq> fetchInfoReqDataForShipmentId(BigDecimal shipmentId);

    List<BTVL_WKF_BOE_TRAIL_TBL> findByShipmentId(BigDecimal shipmentId);


    @Query(value = "SELECT               \n" +
            "    bt.trail_id,               \n" +
            "    bt.shipment_id,               \n" +
            "    bt.action_by,               \n" +
            "    st.ref_description,               \n" +
            "    bt.status,               \n" +
            "    to_char(bt.modified_date,'dd/MM/YYYY') as action_date,               \n" +
            "    bt.action,               \n" +
            "    bt.stage,               \n" +
            "    bt.bucket_no,               \n" +
            "    bt.comment_header,               \n" +
            "    bt.comment_line,             \n" +
            "    to_char(insd.sdate,'dd/MM/YYYY') as invoice_submission_date,         \n" +
            "    t3.assigned_cha_id as CHA_CODE,      \n" +
            "    bt.attribute1,      \n" +
            "    bt.attribute2,    \n" +
            "    t3.assigned_cco_id  \"ASSIGNED_CCO_ID\"    \n" +
            "FROM               \n" +
            "    btvl_wkf_boe_trail_tbl bt               \n" +
            "left outer join                \n" +
            "    (SELECT msst.ref_no,msst.ref_description FROM btvl_mst_ref_tbl msst WHERE msst.ref_id = 12 ORDER BY msst.ref_id,msst.ref_no) st           \n" +
            "    on bt.status = st.ref_no               \n" +
            "left outer join            \n" +
            "    (select sl.shipment_id, max(inv.invoice_submission_date) as sdate from BTVL_WKF_BOE_SHIP_LINE_TBL sl           \n" +
            "inner join BTVL_WKF_INVOICE_HEADER_TBL inv           \n" +
            "on sl.INVOICE_HEADER_ID = inv.INVOICE_HEADER_ID           \n" +
            "group by sl.shipment_id) insd           \n" +
            "on bt.shipment_id = insd.shipment_id           \n" +
            "left outer join (select shipment_id, assigned_cha_id,assigned_cco_id from BTVL_WKF_BOE_SHIPMENT_TBL) t3         \n" +
            "on bt.shipment_id=t3.shipment_id \n" +
            "where bt.shipment_id=:bndShipmentId \n" +
            "order by bt.creation_date", nativeQuery = true)
    List<BoeTrail> fetchByShipmentId(BigDecimal bndShipmentId);

}
