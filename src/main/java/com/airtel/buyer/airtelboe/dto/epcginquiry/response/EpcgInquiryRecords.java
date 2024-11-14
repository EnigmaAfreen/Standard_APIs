package com.airtel.buyer.airtelboe.dto.epcginquiry.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EpcgInquiryRecords {

    @JsonProperty("licenceNo")
    private String licenceNo;

    @JsonProperty("leName")
    private String leName;

    @JsonProperty("lob")
    private String lob;

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("itemDesc")
    private String itemDesc;

    @JsonProperty("portCode")
    private String portCode;

    @JsonProperty("debitQty")
    private BigDecimal debitQty;

    @JsonProperty("licenceDate")
    private LocalDate licenceDate;

    @JsonProperty("dutySaved")
    private String dutySaved;

    @JsonProperty("invoiceNo")
    private String invoiceNo;

    @JsonProperty("invoiceDate")
    private LocalDate invoiceDate;

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("boeDate")
    private LocalDate boeDate;

    @JsonProperty("itemSerialNo")
    private String itemSerialNo;

    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("creationDate")
    private LocalDateTime creationDate;
}
