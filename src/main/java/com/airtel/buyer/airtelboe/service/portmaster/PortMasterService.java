package com.airtel.buyer.airtelboe.service.portmaster;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchportmaster.response.FetchPortMasterData;
import com.airtel.buyer.airtelboe.dto.port.response.PortData;

import java.util.List;

public interface PortMasterService {

    BoeResponse<List<FetchPortMasterData>> portMasterInformation();

    BoeResponse<List<PortData>> portList();

    void portMasterExcel();

    BoeResponse<List<PortData>> indianPortList();

}
