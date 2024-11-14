package com.airtel.buyer.airtelboe.service.fetchshipment;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchshipment.response.FetchShipmentData;

import java.math.BigDecimal;

public interface FetchShipmentService {

    BoeResponse<FetchShipmentData> shipmentInformation(BigDecimal shipmentId);
}
