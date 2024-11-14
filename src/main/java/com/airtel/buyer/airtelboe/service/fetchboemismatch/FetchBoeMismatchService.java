package com.airtel.buyer.airtelboe.service.fetchboemismatch;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchboemismatch.response.BoeMismatchData;

import java.math.BigDecimal;

public interface FetchBoeMismatchService {

    BoeResponse<BoeMismatchData> mismatchInformation(BigDecimal shipmentId);

}
