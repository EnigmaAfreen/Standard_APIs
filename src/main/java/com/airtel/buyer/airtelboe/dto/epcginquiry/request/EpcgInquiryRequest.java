package com.airtel.buyer.airtelboe.dto.epcginquiry.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EpcgInquiryRequest {

    @JsonProperty("lob")
    private String lob;

    @JsonProperty("le")
    private String le;

    @JsonProperty("port")
    private String port;

    @JsonProperty("fromDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate fromDate;

    @JsonProperty("toDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate toDate;

    @JsonProperty("endDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate endDate;
}
