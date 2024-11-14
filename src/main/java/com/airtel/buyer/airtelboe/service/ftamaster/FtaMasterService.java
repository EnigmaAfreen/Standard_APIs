package com.airtel.buyer.airtelboe.service.ftamaster;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchchamaster.response.FetchChaMasterData;
import com.airtel.buyer.airtelboe.dto.fetchftamaster.response.FetchFtaMasterData;

import java.util.List;

public interface FtaMasterService {

    BoeResponse<List<FetchFtaMasterData>> ftaMasterInformation();

    void ftaMasterExcel();
}
