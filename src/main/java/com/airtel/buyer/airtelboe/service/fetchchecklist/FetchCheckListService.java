package com.airtel.buyer.airtelboe.service.fetchchecklist;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchchecklist.response.FetchCheckListData;

import java.math.BigDecimal;

public interface FetchCheckListService {

    BoeResponse<FetchCheckListData> checkListInformation(BigDecimal shipmentId);
}
