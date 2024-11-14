package com.airtel.buyer.airtelboe.service.itemmaster;

import com.airtel.buyer.airtelboe.dto.additemmaster.request.AddItemMasterRequest;
import com.airtel.buyer.airtelboe.dto.edititemmaster.request.EditItemMasterRequest;
import com.airtel.buyer.airtelboe.dto.enddateitemmaster.request.EndDateItemMasterRequest;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_ITEM_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeItemTblRepository;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class ItemMasterTransactionService {

    @Autowired
    private BtvlMstBoeItemTblRepository btvlMstBoeItemTblRepository;

    @Transactional
    public void add(AddItemMasterRequest addItemMasterRequest, UserDetailsImpl userDetails) {

        BTVL_MST_BOE_ITEM_TBL bTVL_MST_BOE_ITEM_TBL =
                this.getMstBoeItemObject(addItemMasterRequest, userDetails.getUsername());
        this.btvlMstBoeItemTblRepository.save(bTVL_MST_BOE_ITEM_TBL);

    }

    private BTVL_MST_BOE_ITEM_TBL getMstBoeItemObject(AddItemMasterRequest addItemMasterRequest, String olmId) {

        BTVL_MST_BOE_ITEM_TBL bTVL_MST_BOE_ITEM_TBL = new BTVL_MST_BOE_ITEM_TBL();

        bTVL_MST_BOE_ITEM_TBL.setItemCode(addItemMasterRequest.getItemCode());
        bTVL_MST_BOE_ITEM_TBL.setHsn(addItemMasterRequest.getHsn());
        bTVL_MST_BOE_ITEM_TBL.setDutyCategory(addItemMasterRequest.getDutyCategory());
        bTVL_MST_BOE_ITEM_TBL.setExemptionNotifNo(addItemMasterRequest.getExemptionNotificationNo());
        bTVL_MST_BOE_ITEM_TBL.setCreatedby(olmId);
        bTVL_MST_BOE_ITEM_TBL.setModifiedby(olmId);
        bTVL_MST_BOE_ITEM_TBL.setCreationDate(LocalDateTime.now());
        bTVL_MST_BOE_ITEM_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_MST_BOE_ITEM_TBL.setPurgeFlag(0);

        return bTVL_MST_BOE_ITEM_TBL;
    }

    @Transactional
    public void edit(EditItemMasterRequest editItemMasterRequest, UserDetailsImpl userDetails) {

        BTVL_MST_BOE_ITEM_TBL existingRecord =
                this.btvlMstBoeItemTblRepository.findByItemIdAndPurgeFlag(editItemMasterRequest.getItemId(), 0);

        if (existingRecord == null) {
            log.info("No record found in BTVL_MST_BOE_EPCG_TBL for EPCG_ID :: " + editItemMasterRequest.getItemId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

        existingRecord.setModifiedby(userDetails.getUsername());
        existingRecord.setModifiedDate(LocalDateTime.now());
        existingRecord.setPurgeFlag(1);
        this.btvlMstBoeItemTblRepository.save(existingRecord);

        BTVL_MST_BOE_ITEM_TBL bTVL_MST_BOE_ITEM_TBL =
                this.getMstBoeItemObject(editItemMasterRequest, userDetails.getUsername(), existingRecord);
        this.btvlMstBoeItemTblRepository.save(bTVL_MST_BOE_ITEM_TBL);

    }

    private BTVL_MST_BOE_ITEM_TBL getMstBoeItemObject(EditItemMasterRequest editItemMasterRequest, String olmId,
                                                      BTVL_MST_BOE_ITEM_TBL existingRecord) {

        BTVL_MST_BOE_ITEM_TBL bTVL_MST_BOE_ITEM_TBL = new BTVL_MST_BOE_ITEM_TBL();

        bTVL_MST_BOE_ITEM_TBL.setHsn(editItemMasterRequest.getHsn());
        bTVL_MST_BOE_ITEM_TBL.setDutyCategory(editItemMasterRequest.getDutyCategory());
        bTVL_MST_BOE_ITEM_TBL.setExemptionNotifNo(editItemMasterRequest.getExemptionNotificationNo());
        bTVL_MST_BOE_ITEM_TBL.setCreatedby(olmId);
        bTVL_MST_BOE_ITEM_TBL.setModifiedby(olmId);
        bTVL_MST_BOE_ITEM_TBL.setCreationDate(LocalDateTime.now());
        bTVL_MST_BOE_ITEM_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_MST_BOE_ITEM_TBL.setPurgeFlag(0);
        bTVL_MST_BOE_ITEM_TBL.setAttribute1(existingRecord.getAttribute1());
        bTVL_MST_BOE_ITEM_TBL.setAttribute2(existingRecord.getAttribute2());
        bTVL_MST_BOE_ITEM_TBL.setAttribute3(existingRecord.getAttribute3());
        bTVL_MST_BOE_ITEM_TBL.setAttribute4(existingRecord.getAttribute4());
        bTVL_MST_BOE_ITEM_TBL.setAttribute5(existingRecord.getAttribute5());
        bTVL_MST_BOE_ITEM_TBL.setAttribute6(existingRecord.getAttribute6());
        bTVL_MST_BOE_ITEM_TBL.setAttribute7(existingRecord.getAttribute7());
        bTVL_MST_BOE_ITEM_TBL.setAttribute8(existingRecord.getAttribute8());
        bTVL_MST_BOE_ITEM_TBL.setAttribute9(existingRecord.getAttribute9());
        bTVL_MST_BOE_ITEM_TBL.setAttribute10(existingRecord.getAttribute10());
        bTVL_MST_BOE_ITEM_TBL.setAttribute11(existingRecord.getAttribute11());
        bTVL_MST_BOE_ITEM_TBL.setAttribute12(existingRecord.getAttribute12());
        bTVL_MST_BOE_ITEM_TBL.setAttribute13(existingRecord.getAttribute13());
        bTVL_MST_BOE_ITEM_TBL.setAttribute14(existingRecord.getAttribute14());
        bTVL_MST_BOE_ITEM_TBL.setAttribute15(existingRecord.getAttribute15());
        bTVL_MST_BOE_ITEM_TBL.setEndDate(existingRecord.getEndDate());
        bTVL_MST_BOE_ITEM_TBL.setItemCode(existingRecord.getItemCode());

        return bTVL_MST_BOE_ITEM_TBL;
    }

    @Transactional
    public void endDate(EndDateItemMasterRequest endDateItemMasterRequest, UserDetailsImpl userDetails) {

        BTVL_MST_BOE_ITEM_TBL existingRecord =
                this.btvlMstBoeItemTblRepository.findByItemIdAndPurgeFlag(endDateItemMasterRequest.getItemId(), 0);

        if (existingRecord == null) {
            log.info("No record found in BTVL_MST_BOE_EPCG_TBL for EPCG_ID :: " + endDateItemMasterRequest.getItemId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

        existingRecord.setModifiedby(userDetails.getUsername());
        existingRecord.setModifiedDate(LocalDateTime.now());
        existingRecord.setPurgeFlag(1);
        this.btvlMstBoeItemTblRepository.save(existingRecord);

        BTVL_MST_BOE_ITEM_TBL bTVL_MST_BOE_ITEM_TBL =
                this.getMstBoeItemObject(endDateItemMasterRequest.getEndDate(), userDetails.getUsername(), existingRecord);
        this.btvlMstBoeItemTblRepository.save(bTVL_MST_BOE_ITEM_TBL);

    }

    private BTVL_MST_BOE_ITEM_TBL getMstBoeItemObject(LocalDate endDate, String olmId,
                                                      BTVL_MST_BOE_ITEM_TBL existingRecord) {

        BTVL_MST_BOE_ITEM_TBL bTVL_MST_BOE_ITEM_TBL = new BTVL_MST_BOE_ITEM_TBL();

        bTVL_MST_BOE_ITEM_TBL.setHsn(existingRecord.getHsn());
        bTVL_MST_BOE_ITEM_TBL.setDutyCategory(existingRecord.getDutyCategory());
        bTVL_MST_BOE_ITEM_TBL.setExemptionNotifNo(existingRecord.getExemptionNotifNo());
        bTVL_MST_BOE_ITEM_TBL.setCreatedby(olmId);
        bTVL_MST_BOE_ITEM_TBL.setModifiedby(olmId);
        bTVL_MST_BOE_ITEM_TBL.setCreationDate(LocalDateTime.now());
        bTVL_MST_BOE_ITEM_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_MST_BOE_ITEM_TBL.setPurgeFlag(0);
        bTVL_MST_BOE_ITEM_TBL.setAttribute1(existingRecord.getAttribute1());
        bTVL_MST_BOE_ITEM_TBL.setAttribute2(existingRecord.getAttribute2());
        bTVL_MST_BOE_ITEM_TBL.setAttribute3(existingRecord.getAttribute3());
        bTVL_MST_BOE_ITEM_TBL.setAttribute4(existingRecord.getAttribute4());
        bTVL_MST_BOE_ITEM_TBL.setAttribute5(existingRecord.getAttribute5());
        bTVL_MST_BOE_ITEM_TBL.setAttribute6(existingRecord.getAttribute6());
        bTVL_MST_BOE_ITEM_TBL.setAttribute7(existingRecord.getAttribute7());
        bTVL_MST_BOE_ITEM_TBL.setAttribute8(existingRecord.getAttribute8());
        bTVL_MST_BOE_ITEM_TBL.setAttribute9(existingRecord.getAttribute9());
        bTVL_MST_BOE_ITEM_TBL.setAttribute10(existingRecord.getAttribute10());
        bTVL_MST_BOE_ITEM_TBL.setAttribute11(existingRecord.getAttribute11());
        bTVL_MST_BOE_ITEM_TBL.setAttribute12(existingRecord.getAttribute12());
        bTVL_MST_BOE_ITEM_TBL.setAttribute13(existingRecord.getAttribute13());
        bTVL_MST_BOE_ITEM_TBL.setAttribute14(existingRecord.getAttribute14());
        bTVL_MST_BOE_ITEM_TBL.setAttribute15(existingRecord.getAttribute15());
        bTVL_MST_BOE_ITEM_TBL.setEndDate(endDate);
        bTVL_MST_BOE_ITEM_TBL.setItemCode(existingRecord.getItemCode());

        return bTVL_MST_BOE_ITEM_TBL;
    }

}
