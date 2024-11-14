package com.airtel.buyer.airtelboe.service.inforequired;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.inforequired.response.InfoRequiredData;

import java.math.BigDecimal;

public interface BoeInfoRequiredService {

    BoeResponse<InfoRequiredData> fetchInfoRequiredData(BigDecimal shipmentId);
}
