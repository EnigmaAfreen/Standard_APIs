package com.airtel.buyer.airtelboe.service.wpcmaster;

import com.airtel.buyer.airtelboe.dto.addwpcmaster.request.AddWpcMasterRequest;
import com.airtel.buyer.airtelboe.dto.editwpcmaster.request.EditWpcMasterRequest;
import com.airtel.buyer.airtelboe.dto.enddatewpcmaster.request.EndDateWpcMasterRequest;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_WPC_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeWpcTblRepository;
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
public class WpcMasterTransactionService {

    @Autowired
    private BtvlMstBoeWpcTblRepository btvlMstBoeWpcTblRepository;

    @Transactional
    public void add(AddWpcMasterRequest addWpcMasterRequest, UserDetailsImpl userDetails) {

        BTVL_MST_BOE_WPC_TBL bTVL_MST_BOE_WPC_TBL =
                this.getMstBoeWpcObject(addWpcMasterRequest, userDetails.getUsername());
        this.btvlMstBoeWpcTblRepository.save(bTVL_MST_BOE_WPC_TBL);

    }

    private BTVL_MST_BOE_WPC_TBL getMstBoeWpcObject(AddWpcMasterRequest addWpcMasterRequest, String olmId) {

        BTVL_MST_BOE_WPC_TBL bTVL_MST_BOE_WPC_TBL = new BTVL_MST_BOE_WPC_TBL();

//        bTVL_MST_BOE_WPC_TBL.setLeName(addWpcMasterRequest.getLegalEntity());
        bTVL_MST_BOE_WPC_TBL.setAttribute1(addWpcMasterRequest.getLegalEntity());
        bTVL_MST_BOE_WPC_TBL.setPortCode(addWpcMasterRequest.getPortCode());
        bTVL_MST_BOE_WPC_TBL.setItemCode(addWpcMasterRequest.getItemCode());
        bTVL_MST_BOE_WPC_TBL.setLicenceNo(addWpcMasterRequest.getLicenseNo());
        bTVL_MST_BOE_WPC_TBL.setWpcQty(addWpcMasterRequest.getWpcQuantity());
        bTVL_MST_BOE_WPC_TBL.setLicenceDate(addWpcMasterRequest.getLicenseDate());
        bTVL_MST_BOE_WPC_TBL.setEndDate(addWpcMasterRequest.getEndDate());
        bTVL_MST_BOE_WPC_TBL.setCreatedby(olmId);
        bTVL_MST_BOE_WPC_TBL.setModifiedby(olmId);
        bTVL_MST_BOE_WPC_TBL.setCreationDate(LocalDateTime.now());
        bTVL_MST_BOE_WPC_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_MST_BOE_WPC_TBL.setPurgeFlag(0);

        return bTVL_MST_BOE_WPC_TBL;
    }

    @Transactional
    public void edit(EditWpcMasterRequest editWpcMasterRequest, UserDetailsImpl userDetails) {

        BTVL_MST_BOE_WPC_TBL existingRecord =
                this.btvlMstBoeWpcTblRepository.findByWpcIdAndPurgeFlag(editWpcMasterRequest.getWpcId(), 0);

        if (existingRecord == null) {
            log.info("No record found in BTVL_MST_BOE_EPCG_TBL for EPCG_ID :: " + editWpcMasterRequest.getWpcId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

        existingRecord.setModifiedby(userDetails.getUsername());
        existingRecord.setModifiedDate(LocalDateTime.now());
        existingRecord.setPurgeFlag(1);
        this.btvlMstBoeWpcTblRepository.save(existingRecord);

        BTVL_MST_BOE_WPC_TBL bTVL_MST_BOE_WPC_TBL =
                this.getMstBoeWpcObject(editWpcMasterRequest, userDetails.getUsername(), existingRecord);
        this.btvlMstBoeWpcTblRepository.save(bTVL_MST_BOE_WPC_TBL);

    }

    private BTVL_MST_BOE_WPC_TBL getMstBoeWpcObject(EditWpcMasterRequest editWpcMasterRequest, String olmId,
                                                    BTVL_MST_BOE_WPC_TBL existingRecord) {

        BTVL_MST_BOE_WPC_TBL bTVL_MST_BOE_WPC_TBL = new BTVL_MST_BOE_WPC_TBL();

//        bTVL_MST_BOE_WPC_TBL.setLeName(editWpcMasterRequest.getLegalEntity());
        bTVL_MST_BOE_WPC_TBL.setAttribute1(editWpcMasterRequest.getLegalEntity());
        bTVL_MST_BOE_WPC_TBL.setPortCode(editWpcMasterRequest.getPortCode());
        bTVL_MST_BOE_WPC_TBL.setItemCode(editWpcMasterRequest.getItemCode());
        bTVL_MST_BOE_WPC_TBL.setLicenceNo(editWpcMasterRequest.getLicenseNo());
        bTVL_MST_BOE_WPC_TBL.setWpcQty(editWpcMasterRequest.getWpcQuantity());
        bTVL_MST_BOE_WPC_TBL.setConsumeQuantity(editWpcMasterRequest.getConsumeQuantity());
        bTVL_MST_BOE_WPC_TBL.setEndDate(editWpcMasterRequest.getEndDate());
        bTVL_MST_BOE_WPC_TBL.setCreatedby(olmId);
        bTVL_MST_BOE_WPC_TBL.setModifiedby(olmId);
        bTVL_MST_BOE_WPC_TBL.setCreationDate(LocalDateTime.now());
        bTVL_MST_BOE_WPC_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_MST_BOE_WPC_TBL.setPurgeFlag(0);
        bTVL_MST_BOE_WPC_TBL.setPoNo(existingRecord.getPoNo());
        bTVL_MST_BOE_WPC_TBL.setOuNo(existingRecord.getOuNo());
        bTVL_MST_BOE_WPC_TBL.setLeName(existingRecord.getLeName());
        bTVL_MST_BOE_WPC_TBL.setAttribute2(existingRecord.getAttribute2());
        bTVL_MST_BOE_WPC_TBL.setAttribute3(existingRecord.getAttribute3());
        bTVL_MST_BOE_WPC_TBL.setAttribute4(existingRecord.getAttribute4());
        bTVL_MST_BOE_WPC_TBL.setAttribute5(existingRecord.getAttribute5());
        bTVL_MST_BOE_WPC_TBL.setAttribute6(existingRecord.getAttribute6());
        bTVL_MST_BOE_WPC_TBL.setAttribute7(existingRecord.getAttribute7());
        bTVL_MST_BOE_WPC_TBL.setAttribute8(existingRecord.getAttribute8());
        bTVL_MST_BOE_WPC_TBL.setAttribute9(existingRecord.getAttribute9());
        bTVL_MST_BOE_WPC_TBL.setAttribute10(existingRecord.getAttribute10());
        bTVL_MST_BOE_WPC_TBL.setAttribute11(existingRecord.getAttribute11());
        bTVL_MST_BOE_WPC_TBL.setAttribute12(existingRecord.getAttribute12());
        bTVL_MST_BOE_WPC_TBL.setAttribute13(existingRecord.getAttribute13());
        bTVL_MST_BOE_WPC_TBL.setAttribute14(existingRecord.getAttribute14());
        bTVL_MST_BOE_WPC_TBL.setAttribute15(existingRecord.getAttribute15());
        bTVL_MST_BOE_WPC_TBL.setLicenceDate(existingRecord.getLicenceDate());

        return bTVL_MST_BOE_WPC_TBL;
    }

    @Transactional
    public void endDate(EndDateWpcMasterRequest endDateWpcMasterRequest, UserDetailsImpl userDetails) {

        BTVL_MST_BOE_WPC_TBL existingRecord =
                this.btvlMstBoeWpcTblRepository.findByWpcIdAndPurgeFlag(endDateWpcMasterRequest.getWpcId(), 0);

        if (existingRecord == null) {
            log.info("No record found in BTVL_MST_BOE_EPCG_TBL for EPCG_ID :: " + endDateWpcMasterRequest.getWpcId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

        existingRecord.setModifiedby(userDetails.getUsername());
        existingRecord.setModifiedDate(LocalDateTime.now());
        existingRecord.setPurgeFlag(1);
        this.btvlMstBoeWpcTblRepository.save(existingRecord);

        BTVL_MST_BOE_WPC_TBL bTVL_MST_BOE_WPC_TBL =
                this.getMstBoeWpcObject(endDateWpcMasterRequest.getEndDate(), userDetails.getUsername(), existingRecord);
        this.btvlMstBoeWpcTblRepository.save(bTVL_MST_BOE_WPC_TBL);

    }

    private BTVL_MST_BOE_WPC_TBL getMstBoeWpcObject(LocalDate endDate, String olmId,
                                                    BTVL_MST_BOE_WPC_TBL existingRecord) {

        BTVL_MST_BOE_WPC_TBL bTVL_MST_BOE_WPC_TBL = new BTVL_MST_BOE_WPC_TBL();

        bTVL_MST_BOE_WPC_TBL.setLeName(existingRecord.getLeName());
        bTVL_MST_BOE_WPC_TBL.setPortCode(existingRecord.getPortCode());
        bTVL_MST_BOE_WPC_TBL.setItemCode(existingRecord.getItemCode());
        bTVL_MST_BOE_WPC_TBL.setLicenceNo(existingRecord.getLicenceNo());
        bTVL_MST_BOE_WPC_TBL.setWpcQty(existingRecord.getWpcQty());
        bTVL_MST_BOE_WPC_TBL.setConsumeQuantity(existingRecord.getConsumeQuantity());
        bTVL_MST_BOE_WPC_TBL.setEndDate(endDate);
        bTVL_MST_BOE_WPC_TBL.setCreatedby(olmId);
        bTVL_MST_BOE_WPC_TBL.setModifiedby(olmId);
        bTVL_MST_BOE_WPC_TBL.setCreationDate(LocalDateTime.now());
        bTVL_MST_BOE_WPC_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_MST_BOE_WPC_TBL.setPurgeFlag(0);
        bTVL_MST_BOE_WPC_TBL.setPoNo(existingRecord.getPoNo());
        bTVL_MST_BOE_WPC_TBL.setOuNo(existingRecord.getOuNo());
        bTVL_MST_BOE_WPC_TBL.setAttribute1(existingRecord.getAttribute1());
        bTVL_MST_BOE_WPC_TBL.setAttribute2(existingRecord.getAttribute2());
        bTVL_MST_BOE_WPC_TBL.setAttribute3(existingRecord.getAttribute3());
        bTVL_MST_BOE_WPC_TBL.setAttribute4(existingRecord.getAttribute4());
        bTVL_MST_BOE_WPC_TBL.setAttribute5(existingRecord.getAttribute5());
        bTVL_MST_BOE_WPC_TBL.setAttribute6(existingRecord.getAttribute6());
        bTVL_MST_BOE_WPC_TBL.setAttribute7(existingRecord.getAttribute7());
        bTVL_MST_BOE_WPC_TBL.setAttribute8(existingRecord.getAttribute8());
        bTVL_MST_BOE_WPC_TBL.setAttribute9(existingRecord.getAttribute9());
        bTVL_MST_BOE_WPC_TBL.setAttribute10(existingRecord.getAttribute10());
        bTVL_MST_BOE_WPC_TBL.setAttribute11(existingRecord.getAttribute11());
        bTVL_MST_BOE_WPC_TBL.setAttribute12(existingRecord.getAttribute12());
        bTVL_MST_BOE_WPC_TBL.setAttribute13(existingRecord.getAttribute13());
        bTVL_MST_BOE_WPC_TBL.setAttribute14(existingRecord.getAttribute14());
        bTVL_MST_BOE_WPC_TBL.setAttribute15(existingRecord.getAttribute15());
        bTVL_MST_BOE_WPC_TBL.setLicenceDate(existingRecord.getLicenceDate());

        return bTVL_MST_BOE_WPC_TBL;
    }

}
