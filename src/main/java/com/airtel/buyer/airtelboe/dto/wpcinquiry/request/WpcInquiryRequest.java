package com.airtel.buyer.airtelboe.dto.wpcinquiry.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WpcInquiryRequest {

    @JsonProperty("lob")
    private String lob;

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
