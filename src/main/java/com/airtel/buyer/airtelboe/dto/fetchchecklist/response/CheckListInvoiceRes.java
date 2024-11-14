package com.airtel.buyer.airtelboe.dto.fetchchecklist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CheckListInvoiceRes {

    @JsonProperty("incoTerm")
    private String incoTerm;

    @JsonProperty("freightAmt")
    private String freightAmt;

    @JsonProperty("insuranceAmt")
    private String insuranceAmt;

    @JsonProperty("exchangeRate")
    private BigDecimal exchangeRate;

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("invoiceDate")
    private LocalDate invoiceDate;

    @JsonProperty("invoiceValue")
    private BigDecimal invoiceValue;

    @JsonProperty("currencyCode")
    private String currencyCode;

    @JsonProperty("checkListLines")
    private List<CheckListLineRes> checkListLineResList;

}
