package com.airtel.buyer.airtelboe.dto.shipmentstatusreport.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ShipmentStatusReportRequest {

    @JsonProperty("ou")
    private BigDecimal ou;

    @JsonProperty("lob")
    private String lob;

    @JsonProperty("chaCode")
    private String chaCode;

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("airtelRefNo")
    private BigDecimal airtelRefNo;

    @JsonProperty("portCode")
    private String portCode;

    @JsonProperty("fromDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate fromDate;

    @JsonProperty("toDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate toDate;
}
