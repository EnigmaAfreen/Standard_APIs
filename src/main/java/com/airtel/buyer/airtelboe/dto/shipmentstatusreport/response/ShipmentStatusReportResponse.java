package com.airtel.buyer.airtelboe.dto.shipmentstatusreport.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ShipmentStatusReportResponse {

    @JsonProperty("errMsg")
    private String errMsg;

    @JsonProperty("status")
    private String status;
}
