package com.airtel.buyer.airtelboe.service.wpcmaster;

import com.airtel.buyer.airtelboe.dto.addwpcmaster.request.AddWpcMasterRequest;
import com.airtel.buyer.airtelboe.dto.addwpcmaster.response.AddWpcMasterData;
import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.editwpcmaster.request.EditWpcMasterRequest;
import com.airtel.buyer.airtelboe.dto.editwpcmaster.response.EditWpcMasterData;
import com.airtel.buyer.airtelboe.dto.enddatewpcmaster.request.EndDateWpcMasterRequest;
import com.airtel.buyer.airtelboe.dto.enddatewpcmaster.response.EndDateWpcMasterData;
import com.airtel.buyer.airtelboe.dto.fetchwpcmaster.response.FetchWpcMasterData;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;

import java.util.List;

public interface WpcMasterService {


    BoeResponse<List<FetchWpcMasterData>> wpcMasterInformation();

    BoeResponse<AddWpcMasterData> addWpcMaster(AddWpcMasterRequest addWpcMasterRequest, UserDetailsImpl userDetails);

    BoeResponse<EditWpcMasterData> editWpcMaster(EditWpcMasterRequest editWpcMasterRequest, UserDetailsImpl userDetails);

    BoeResponse<EndDateWpcMasterData> endDateWpcMaster(EndDateWpcMasterRequest endDateWpcMasterRequest, UserDetailsImpl userDetails);

    void wpcMasterExcel();
    
}
