package com.airtel.buyer.airtelboe.service.epcgmaster;

import com.airtel.buyer.airtelboe.dto.addepcgmaster.request.AddEpcgMasterRequest;
import com.airtel.buyer.airtelboe.dto.addepcgmaster.response.AddEpcgMasterData;
import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.editepcgmaster.request.EditEpcgMasterRequest;
import com.airtel.buyer.airtelboe.dto.editepcgmaster.response.EditEpcgMasterData;
import com.airtel.buyer.airtelboe.dto.enddateepcgmaster.request.EndDateEpcgMasterRequest;
import com.airtel.buyer.airtelboe.dto.enddateepcgmaster.response.EndDateEpcgMasterData;
import com.airtel.buyer.airtelboe.dto.fetchepcgmaster.response.FetchEpcgMasterData;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;

import java.util.List;

public interface EpcgMasterService {

    BoeResponse<List<FetchEpcgMasterData>> epcgMasterInformation();

    BoeResponse<AddEpcgMasterData> addEpcgMaster(AddEpcgMasterRequest addEpcgMasterRequest, UserDetailsImpl userDetails);

    BoeResponse<EditEpcgMasterData> editEpcgMaster(EditEpcgMasterRequest editEpcgMasterRequest, UserDetailsImpl userDetails);

    BoeResponse<EndDateEpcgMasterData> endDateEpcgMaster(EndDateEpcgMasterRequest endDateEpcgMasterRequest, UserDetailsImpl userDetails);

    void epcgMasterExcel();

}
