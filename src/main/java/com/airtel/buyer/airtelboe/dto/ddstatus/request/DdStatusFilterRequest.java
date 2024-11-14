package com.airtel.buyer.airtelboe.dto.ddstatus.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DdStatusFilterRequest {

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal airtelBoeRefNo;

    @JsonProperty("fromDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate fromDate;

    @JsonProperty("toDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate toDate;

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("status")
    private String status;

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("awbNo")
    private String awbNo;

}
