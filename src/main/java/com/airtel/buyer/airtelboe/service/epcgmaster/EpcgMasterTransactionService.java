package com.airtel.buyer.airtelboe.service.epcgmaster;

import com.airtel.buyer.airtelboe.dto.addepcgmaster.request.AddEpcgMasterRequest;
import com.airtel.buyer.airtelboe.dto.editepcgmaster.request.EditEpcgMasterRequest;
import com.airtel.buyer.airtelboe.dto.enddateepcgmaster.request.EndDateEpcgMasterRequest;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_EPCG_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeEpcgTblRepository;
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
public class EpcgMasterTransactionService {

    @Autowired
    private BtvlMstBoeEpcgTblRepository btvlMstBoeEpcgTblRepository;

    @Transactional
    public void add(AddEpcgMasterRequest addEpcgMasterRequest, UserDetailsImpl userDetails) {

        BTVL_MST_BOE_EPCG_TBL bTVL_MST_BOE_EPCG_TBL =
                this.getMstBoeEpcgObject(addEpcgMasterRequest, userDetails.getUsername());
        this.btvlMstBoeEpcgTblRepository.save(bTVL_MST_BOE_EPCG_TBL);

    }

    private BTVL_MST_BOE_EPCG_TBL getMstBoeEpcgObject(AddEpcgMasterRequest addEpcgMasterRequest, String olmId) {

        BTVL_MST_BOE_EPCG_TBL bTVL_MST_BOE_EPCG_TBL = new BTVL_MST_BOE_EPCG_TBL();

        bTVL_MST_BOE_EPCG_TBL.setLicenceNo(addEpcgMasterRequest.getLicenseNo());
        bTVL_MST_BOE_EPCG_TBL.setLeName(addEpcgMasterRequest.getLegalEntity());
        bTVL_MST_BOE_EPCG_TBL.setAttribute1(addEpcgMasterRequest.getItemSerialNo());//ItemSerialNumber
        bTVL_MST_BOE_EPCG_TBL.setItemCode(addEpcgMasterRequest.getItemCode());
        bTVL_MST_BOE_EPCG_TBL.setItemDecription(addEpcgMasterRequest.getItemDescription());
        bTVL_MST_BOE_EPCG_TBL.setPortCode(addEpcgMasterRequest.getPort());
        bTVL_MST_BOE_EPCG_TBL.setTotItemQtyEpcgLc(addEpcgMasterRequest.getTotalItemQty());
        bTVL_MST_BOE_EPCG_TBL.setBalanceQty(addEpcgMasterRequest.getBalanceQty());
        bTVL_MST_BOE_EPCG_TBL.setLicenceDate(addEpcgMasterRequest.getLicenseDate());
        bTVL_MST_BOE_EPCG_TBL.setEndDate(addEpcgMasterRequest.getEndDate());
        bTVL_MST_BOE_EPCG_TBL.setCreatedby(olmId);
        bTVL_MST_BOE_EPCG_TBL.setModifiedby(olmId);
        bTVL_MST_BOE_EPCG_TBL.setCreationDate(LocalDateTime.now());
        bTVL_MST_BOE_EPCG_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_MST_BOE_EPCG_TBL.setPurgeFlag(0);

        return bTVL_MST_BOE_EPCG_TBL;
    }

    @Transactional
    public void edit(EditEpcgMasterRequest editEpcgMasterRequest, UserDetailsImpl userDetails) {

        BTVL_MST_BOE_EPCG_TBL existingRecord =
                this.btvlMstBoeEpcgTblRepository.findByEpcgIdAndPurgeFlag(editEpcgMasterRequest.getEpcgId(), 0);

        if (existingRecord == null) {
            log.info("No record found in BTVL_MST_BOE_EPCG_TBL for EPCG_ID :: " + editEpcgMasterRequest.getEpcgId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

        existingRecord.setModifiedby(userDetails.getUsername());
        existingRecord.setModifiedDate(LocalDateTime.now());
        existingRecord.setPurgeFlag(1);
        this.btvlMstBoeEpcgTblRepository.save(existingRecord);

        BTVL_MST_BOE_EPCG_TBL bTVL_MST_BOE_EPCG_TBL =
                this.getMstBoeEpcgObject(editEpcgMasterRequest, userDetails.getUsername(), existingRecord);
        this.btvlMstBoeEpcgTblRepository.save(bTVL_MST_BOE_EPCG_TBL);

    }

    private BTVL_MST_BOE_EPCG_TBL getMstBoeEpcgObject(EditEpcgMasterRequest editEpcgMasterRequest, String olmId,
                                                      BTVL_MST_BOE_EPCG_TBL existingRecord) {

        BTVL_MST_BOE_EPCG_TBL bTVL_MST_BOE_EPCG_TBL = new BTVL_MST_BOE_EPCG_TBL();

        bTVL_MST_BOE_EPCG_TBL.setLeName(editEpcgMasterRequest.getLegalEntity());
        bTVL_MST_BOE_EPCG_TBL.setItemCode(editEpcgMasterRequest.getItemCode());
        bTVL_MST_BOE_EPCG_TBL.setItemDecription(editEpcgMasterRequest.getItemDescription());
        bTVL_MST_BOE_EPCG_TBL.setPortCode(editEpcgMasterRequest.getPort());
        bTVL_MST_BOE_EPCG_TBL.setEndDate(editEpcgMasterRequest.getEndDate());
        bTVL_MST_BOE_EPCG_TBL.setCreatedby(olmId);
        bTVL_MST_BOE_EPCG_TBL.setModifiedby(olmId);
        bTVL_MST_BOE_EPCG_TBL.setCreationDate(LocalDateTime.now());
        bTVL_MST_BOE_EPCG_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_MST_BOE_EPCG_TBL.setPurgeFlag(0);
        bTVL_MST_BOE_EPCG_TBL.setLicenceNo(existingRecord.getLicenceNo());
        bTVL_MST_BOE_EPCG_TBL.setTotItemQtyEpcgLc(existingRecord.getTotItemQtyEpcgLc());
        bTVL_MST_BOE_EPCG_TBL.setBalanceQty(existingRecord.getBalanceQty());
        bTVL_MST_BOE_EPCG_TBL.setAttribute1(existingRecord.getAttribute1());
        bTVL_MST_BOE_EPCG_TBL.setAttribute2(existingRecord.getAttribute2());
        bTVL_MST_BOE_EPCG_TBL.setAttribute3(existingRecord.getAttribute3());
        bTVL_MST_BOE_EPCG_TBL.setAttribute4(existingRecord.getAttribute4());
        bTVL_MST_BOE_EPCG_TBL.setAttribute5(existingRecord.getAttribute5());
        bTVL_MST_BOE_EPCG_TBL.setAttribute6(existingRecord.getAttribute6());
        bTVL_MST_BOE_EPCG_TBL.setAttribute7(existingRecord.getAttribute7());
        bTVL_MST_BOE_EPCG_TBL.setAttribute8(existingRecord.getAttribute8());
        bTVL_MST_BOE_EPCG_TBL.setAttribute9(existingRecord.getAttribute9());
        bTVL_MST_BOE_EPCG_TBL.setAttribute10(existingRecord.getAttribute10());
        bTVL_MST_BOE_EPCG_TBL.setAttribute11(existingRecord.getAttribute11());
        bTVL_MST_BOE_EPCG_TBL.setAttribute12(existingRecord.getAttribute12());
        bTVL_MST_BOE_EPCG_TBL.setAttribute13(existingRecord.getAttribute13());
        bTVL_MST_BOE_EPCG_TBL.setAttribute14(existingRecord.getAttribute14());
        bTVL_MST_BOE_EPCG_TBL.setAttribute15(existingRecord.getAttribute15());
        bTVL_MST_BOE_EPCG_TBL.setLicenceDate(existingRecord.getLicenceDate());
        bTVL_MST_BOE_EPCG_TBL.setPortIssue(existingRecord.getPortIssue());
        bTVL_MST_BOE_EPCG_TBL.setDutySaved(existingRecord.getDutySaved());
        bTVL_MST_BOE_EPCG_TBL.setRegNum(existingRecord.getRegNum());

        return bTVL_MST_BOE_EPCG_TBL;
    }

    @Transactional
    public void endDate(EndDateEpcgMasterRequest endDateEpcgMasterRequest, UserDetailsImpl userDetails) {

        BTVL_MST_BOE_EPCG_TBL existingRecord =
                this.btvlMstBoeEpcgTblRepository.findByEpcgIdAndPurgeFlag(endDateEpcgMasterRequest.getEpcgId(), 0);

        if (existingRecord == null) {
            log.info("No record found in BTVL_MST_BOE_EPCG_TBL for EPCG_ID :: " + endDateEpcgMasterRequest.getEpcgId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

        existingRecord.setModifiedby(userDetails.getUsername());
        existingRecord.setModifiedDate(LocalDateTime.now());
        existingRecord.setPurgeFlag(1);
        this.btvlMstBoeEpcgTblRepository.save(existingRecord);

        BTVL_MST_BOE_EPCG_TBL bTVL_MST_BOE_EPCG_TBL =
                this.getMstBoeEpcgObject(endDateEpcgMasterRequest.getEndDate(), userDetails.getUsername(), existingRecord);
        this.btvlMstBoeEpcgTblRepository.save(bTVL_MST_BOE_EPCG_TBL);

    }

    private BTVL_MST_BOE_EPCG_TBL getMstBoeEpcgObject(LocalDate endDate, String olmId,
                                                      BTVL_MST_BOE_EPCG_TBL existingRecord) {

        BTVL_MST_BOE_EPCG_TBL bTVL_MST_BOE_EPCG_TBL = new BTVL_MST_BOE_EPCG_TBL();

        bTVL_MST_BOE_EPCG_TBL.setLeName(existingRecord.getLeName());
        bTVL_MST_BOE_EPCG_TBL.setItemCode(existingRecord.getItemCode());
        bTVL_MST_BOE_EPCG_TBL.setItemDecription(existingRecord.getItemDecription());
        bTVL_MST_BOE_EPCG_TBL.setPortCode(existingRecord.getPortCode());
        bTVL_MST_BOE_EPCG_TBL.setEndDate(endDate);
        bTVL_MST_BOE_EPCG_TBL.setCreatedby(olmId);
        bTVL_MST_BOE_EPCG_TBL.setModifiedby(olmId);
        bTVL_MST_BOE_EPCG_TBL.setCreationDate(LocalDateTime.now());
        bTVL_MST_BOE_EPCG_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_MST_BOE_EPCG_TBL.setPurgeFlag(0);
        bTVL_MST_BOE_EPCG_TBL.setLicenceNo(existingRecord.getLicenceNo());
        bTVL_MST_BOE_EPCG_TBL.setTotItemQtyEpcgLc(existingRecord.getTotItemQtyEpcgLc());
        bTVL_MST_BOE_EPCG_TBL.setBalanceQty(existingRecord.getBalanceQty());
        bTVL_MST_BOE_EPCG_TBL.setAttribute1(existingRecord.getAttribute1());
        bTVL_MST_BOE_EPCG_TBL.setAttribute2(existingRecord.getAttribute2());
        bTVL_MST_BOE_EPCG_TBL.setAttribute3(existingRecord.getAttribute3());
        bTVL_MST_BOE_EPCG_TBL.setAttribute4(existingRecord.getAttribute4());
        bTVL_MST_BOE_EPCG_TBL.setAttribute5(existingRecord.getAttribute5());
        bTVL_MST_BOE_EPCG_TBL.setAttribute6(existingRecord.getAttribute6());
        bTVL_MST_BOE_EPCG_TBL.setAttribute7(existingRecord.getAttribute7());
        bTVL_MST_BOE_EPCG_TBL.setAttribute8(existingRecord.getAttribute8());
        bTVL_MST_BOE_EPCG_TBL.setAttribute9(existingRecord.getAttribute9());
        bTVL_MST_BOE_EPCG_TBL.setAttribute10(existingRecord.getAttribute10());
        bTVL_MST_BOE_EPCG_TBL.setAttribute11(existingRecord.getAttribute11());
        bTVL_MST_BOE_EPCG_TBL.setAttribute12(existingRecord.getAttribute12());
        bTVL_MST_BOE_EPCG_TBL.setAttribute13(existingRecord.getAttribute13());
        bTVL_MST_BOE_EPCG_TBL.setAttribute14(existingRecord.getAttribute14());
        bTVL_MST_BOE_EPCG_TBL.setAttribute15(existingRecord.getAttribute15());
        bTVL_MST_BOE_EPCG_TBL.setLicenceDate(existingRecord.getLicenceDate());
        bTVL_MST_BOE_EPCG_TBL.setPortIssue(existingRecord.getPortIssue());
        bTVL_MST_BOE_EPCG_TBL.setDutySaved(existingRecord.getDutySaved());
        bTVL_MST_BOE_EPCG_TBL.setRegNum(existingRecord.getRegNum());

        return bTVL_MST_BOE_EPCG_TBL;
    }

}
