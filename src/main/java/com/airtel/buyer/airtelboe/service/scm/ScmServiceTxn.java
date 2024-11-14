package com.airtel.buyer.airtelboe.service.scm;

import com.airtel.buyer.airtelboe.model.supplier.*;
import com.airtel.buyer.airtelboe.repository.supplier.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScmServiceTxn {

    @Autowired
    BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    BtvlWkfBoeShipLineTblRepository btvlWkfBoeShipLineTblRepository;

    @Autowired
    BtvlWkfBoeShipDocTblRepository btvlWkfBoeShipDocTblRepository;

    @Autowired
    BtvlWkfBoeTrailTblRepository btvlWkfBoeTrailTblRepository;


    @Autowired
    BtvlWkfBoeChkHTblRepository btvlWkfBoeChkHTblRepository;

    @Autowired
    BtvlWkfBoeChkInvTblRepository btvlWkfBoeChkInvTblRepository;

    @Autowired
    BtvlWkfBoeChkLTblRepository btvlWkfBoeChkLTblRepository;

    @Transactional
    public Boolean saveScmStage2Data(
            BTVL_WKF_BOE_SHIPMENT_TBL btvl_wkf_boe_shipment_tbl,
            List<BTVL_WKF_BOE_SHIP_LINE_TBL> btvl_wkf_boe_ship_line_tbls,
            List<BTVL_WKF_BOE_SHIP_DOC_TBL> btvl_wkf_boe_ship_doc_tbls,
            List<BTVL_WKF_BOE_TRAIL_TBL> btvl_wkf_boe_trail_tbls) {
        Boolean result = Boolean.FALSE;
//        try {

            btvlWkfBoeShipmentTblRepository.save(btvl_wkf_boe_shipment_tbl);
            log.info("Shipment object :: " + btvl_wkf_boe_shipment_tbl);
            log.info("Saved Shipment =========");


            btvlWkfBoeShipLineTblRepository.saveAll(btvl_wkf_boe_ship_line_tbls);
            btvl_wkf_boe_ship_line_tbls.forEach(System.out::println);
            log.info("Saved Shipment Line =========");

            //set shipmentId on each doc obj
            for (BTVL_WKF_BOE_SHIP_DOC_TBL doc : btvl_wkf_boe_ship_doc_tbls) {
                doc.setShipmentId(btvl_wkf_boe_shipment_tbl.getShipmentId());
            }
            btvlWkfBoeShipDocTblRepository.saveAll(btvl_wkf_boe_ship_doc_tbls);
            btvl_wkf_boe_ship_doc_tbls.forEach(System.out::println);
            log.info("Saved Shipment Docs =========");

            btvlWkfBoeTrailTblRepository.saveAll(btvl_wkf_boe_trail_tbls);
            btvl_wkf_boe_trail_tbls.forEach(System.out::println);
            log.info("Saved Shipment Trails =========");

            result = Boolean.TRUE;
//        } catch (Exception e) {
//            log.error("Exception raised while save Scm Stage2 Data - " + e.getMessage());
//            result = Boolean.FALSE;
//        }
        return result;
    }

    @Transactional
    public Boolean scmApproveStage4(BTVL_WKF_BOE_SHIPMENT_TBL btvl_wkf_boe_shipment_tbl,
                                    List<BTVL_WKF_BOE_TRAIL_TBL> btvl_wkf_boe_trail_tbls) {
        Boolean result = Boolean.FALSE;

        try {
            btvlWkfBoeShipmentTblRepository.save(btvl_wkf_boe_shipment_tbl);
            log.info("Shipment object :: " + btvl_wkf_boe_shipment_tbl);
            log.info("Saved Shipment =========");

            btvlWkfBoeTrailTblRepository.saveAll(btvl_wkf_boe_trail_tbls);
            btvl_wkf_boe_trail_tbls.forEach(System.out::println);
            log.info("Saved Shipment Trails =========");

            result = Boolean.TRUE;
        } catch (Exception e) {
            log.error("Exception raised while approve Scm Stage4 Data - " + e.getMessage());
            result = Boolean.FALSE;
        }
        return result;
    }

    @Transactional
    public Boolean scmRejectStage4(BTVL_WKF_BOE_SHIPMENT_TBL btvl_wkf_boe_shipment_tbl,
                                   List<BTVL_WKF_BOE_TRAIL_TBL> btvl_wkf_boe_trail_tbls) {

        Boolean result = Boolean.FALSE;

        try {
            btvlWkfBoeShipmentTblRepository.save(btvl_wkf_boe_shipment_tbl);
            log.info("Shipment object :: " + btvl_wkf_boe_shipment_tbl);
            log.info("Saved Shipment =========");

            btvlWkfBoeTrailTblRepository.saveAll(btvl_wkf_boe_trail_tbls);
            btvl_wkf_boe_trail_tbls.forEach(System.out::println);
            log.info("Saved Shipment Trails =========");

            BTVL_WKF_BOE_CHK_H_TBL btvl_wkf_boe_chk_h_tbl = btvlWkfBoeChkHTblRepository.findByShipmentIdAndPurgeFlag(btvl_wkf_boe_shipment_tbl.getShipmentId(), 0);
            btvl_wkf_boe_chk_h_tbl.setPurgeFlag(1);
            btvl_wkf_boe_chk_h_tbl.setModifiedby(btvl_wkf_boe_shipment_tbl.getModifiedby());
            btvl_wkf_boe_chk_h_tbl.setModifiedDate(btvl_wkf_boe_shipment_tbl.getModifiedDate());
            btvlWkfBoeChkHTblRepository.save(btvl_wkf_boe_chk_h_tbl);
            log.info("Saved Checklist Header =========");

            List<BTVL_WKF_BOE_CHK_INV_TBL> btvl_wkf_boe_chk_inv_tbls = btvlWkfBoeChkInvTblRepository.findByBoeHeaderIdAndPurgeFlag(btvl_wkf_boe_chk_h_tbl.getBoeHeaderId(), 0);

            List<BigDecimal> boeInvHeaderIds = new ArrayList<>();

            for (BTVL_WKF_BOE_CHK_INV_TBL inv : btvl_wkf_boe_chk_inv_tbls) {
                inv.setPurgeFlag(1);
                inv.setModifiedby(btvl_wkf_boe_shipment_tbl.getModifiedby());
                inv.setModifiedDate(btvl_wkf_boe_shipment_tbl.getModifiedDate());
                boeInvHeaderIds.add(inv.getBoeInvHeaderId());
            }
            btvlWkfBoeChkInvTblRepository.saveAll(btvl_wkf_boe_chk_inv_tbls);
            btvl_wkf_boe_chk_inv_tbls.forEach(System.out::println);
            log.info("Saved Checklist Invoices =========");

            List<BTVL_WKF_BOE_CHK_L_TBL> btvl_wkf_boe_chk_l_tbls = btvlWkfBoeChkLTblRepository.findByBoeInvHeaderIdInAndPurgeFlag(boeInvHeaderIds, 0);
            for (BTVL_WKF_BOE_CHK_L_TBL line : btvl_wkf_boe_chk_l_tbls) {
                line.setPurgeFlag(1);
                line.setModifiedby(btvl_wkf_boe_shipment_tbl.getModifiedby());
                line.setModifiedDate(btvl_wkf_boe_shipment_tbl.getModifiedDate());
            }
            btvlWkfBoeChkLTblRepository.saveAll(btvl_wkf_boe_chk_l_tbls);
            btvl_wkf_boe_chk_l_tbls.forEach(System.out::println);
            log.info("Saved Checklist Lines =========");

            result = Boolean.TRUE;
        } catch (Exception e) {
            log.error("Exception raised while reject Scm Stage4 Data - " + e.getMessage());
            result = Boolean.FALSE;
        }
        return result;
    }
}
