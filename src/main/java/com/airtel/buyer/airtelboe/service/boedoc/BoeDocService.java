package com.airtel.buyer.airtelboe.service.boedoc;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchboedoc.response.FetchBoeDocData;

import java.math.BigDecimal;
import java.util.List;

public interface BoeDocService {

    BoeResponse<List<FetchBoeDocData>> boeDocInformation(BigDecimal shipmentId);

}
