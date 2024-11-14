package com.airtel.buyer.airtelboe.dto.fetchepcgmaster.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FetchEpcgMasterData {

    @JsonProperty("epcgId")
    private BigDecimal epcgId;

    @JsonProperty("licenseNo")
    private String licenseNo;

    @JsonProperty("legalEntity")
    private String legalEntity;

    @JsonProperty("lob")
    private String lob;

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

    @JsonProperty("licenseDate")
    private LocalDate licenseDate;

    @JsonProperty("endDate")
    private LocalDate endDate;

}
