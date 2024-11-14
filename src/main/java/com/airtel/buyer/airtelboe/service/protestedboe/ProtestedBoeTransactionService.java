package com.airtel.buyer.airtelboe.service.protestedboe;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_PROTESTED_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeProtestedTblRepository;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ProtestedBoeTransactionService {

    @Autowired
    private BtvlWkfBoeProtestedTblRepository btvlWkfBoeProtestedTblRepository;

    @Transactional
    public void protestedAction(BTVL_WKF_BOE_PROTESTED_TBL bTVL_WKF_BOE_PROTESTED_TBL, UserDetailsImpl userDetails) {

        bTVL_WKF_BOE_PROTESTED_TBL.setActionStatus("CLOSE");
        bTVL_WKF_BOE_PROTESTED_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_PROTESTED_TBL.setModifiedby(userDetails.getUsername());
        bTVL_WKF_BOE_PROTESTED_TBL.setActionDate(LocalDate.now());

        this.btvlWkfBoeProtestedTblRepository.save(bTVL_WKF_BOE_PROTESTED_TBL);

    }

}
