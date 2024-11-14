package com.airtel.buyer.airtelboe.dto.wpcinquiry.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class WpcInquiryRecords {

    @JsonProperty("portCode")
    private String portCode;

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("licenceNo")
    private String licenceNo;

    @JsonProperty("wpcQty")
    private BigDecimal wpcQty;

    @JsonProperty("debitQty")
    private BigDecimal debitQty;

    @JsonProperty("invoiceNo")
    private String invoiceNo;

    @JsonProperty("invoiceDate")
    private LocalDate invoiceDate;

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("boeDate")
    private LocalDate boeDate;

    @JsonProperty("lob")
    private String lob;

    @JsonProperty("licenceDate")
    private LocalDate licenceDate;

    @JsonProperty("leName")
    private String leName;

    @JsonProperty("itemDesc")
    private String itemDesc;

    @JsonProperty("endDate")
    private LocalDate endDate;
}
