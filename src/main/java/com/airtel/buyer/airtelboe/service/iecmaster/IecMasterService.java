package com.airtel.buyer.airtelboe.service.iecmaster;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchiecmaster.response.FetchIecMasterData;

import java.util.List;

public interface IecMasterService {

    BoeResponse<List<FetchIecMasterData>> iecMasterInformation();

    void iecMasterExcel();

}
