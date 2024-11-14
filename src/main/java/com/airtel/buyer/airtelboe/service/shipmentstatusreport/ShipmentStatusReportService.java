package com.airtel.buyer.airtelboe.service.shipmentstatusreport;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.shipmentstatusreport.request.ShipmentStatusReportRequest;
import com.airtel.buyer.airtelboe.dto.shipmentstatusreport.response.ShipmentStatusReportResponse;

public interface ShipmentStatusReportService {

    BoeResponse<ShipmentStatusReportResponse> shipmentStausReport(ShipmentStatusReportRequest shipmentStatusReportRequest, String email) ;
}
