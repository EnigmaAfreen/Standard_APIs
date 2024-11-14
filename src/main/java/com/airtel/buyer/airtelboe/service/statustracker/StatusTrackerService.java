package com.airtel.buyer.airtelboe.service.statustracker;



import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.statustracker.response.BoeStatusTrackerData;

import java.math.BigDecimal;

public interface StatusTrackerService {

    BoeResponse<BoeStatusTrackerData> trackStatus(BigDecimal shipmentId);
}
