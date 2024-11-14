package com.airtel.buyer.airtelboe.service.chamaster;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchchamaster.response.FetchChaMasterData;

import java.util.List;


public interface ChaMasterService {

    BoeResponse<List<FetchChaMasterData>> chaMasterInformation();

    void chaMasterExcel();

}
