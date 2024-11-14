package com.airtel.buyer.airtelboe.dto.addepcgmaster.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AddEpcgMasterRequest {

    @JsonProperty("licenseNo")
    private String licenseNo;

    @JsonProperty("legalEntity")
    private String legalEntity;

    @JsonProperty("itemSerialNo")
    private String itemSerialNo;

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("itemDescription")
    private String itemDescription;

    @JsonProperty("port")
    private String port;

    @JsonProperty("totalItemQty")
    private BigDecimal totalItemQty;

    @JsonProperty("balanceQty")
    private BigDecimal balanceQty;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("licenseDate")
    private LocalDate licenseDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("endDate")
    private LocalDate endDate;

}
