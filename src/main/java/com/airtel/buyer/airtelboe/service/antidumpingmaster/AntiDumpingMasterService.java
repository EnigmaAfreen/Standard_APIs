package com.airtel.buyer.airtelboe.service.antidumpingmaster;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchantidumpingmaster.response.FetchAntiDumpingMasterData;

import java.util.List;


public interface AntiDumpingMasterService {

    BoeResponse<List<FetchAntiDumpingMasterData>> antiDumpingMasterInformation();

    void antiDumpingMasterExcel();

}
