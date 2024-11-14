package com.airtel.buyer.airtelboe.dto.fetchwpcmaster.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FetchWpcMasterData {

    @JsonProperty("wpcId")
    private BigDecimal wpcId;

    @JsonProperty("legalEntity")
    private String legalEntity;

    @JsonProperty("portCode")
    private String portCode;

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("licenseNo")
    private String licenseNo;

    @JsonProperty("wpcQuantity")
    private BigDecimal wpcQuantity;

    @JsonProperty("balanceQuantity")
    private BigDecimal balanceQuantity;

    @JsonProperty("licenseDate")
    private LocalDate licenseDate;

    @JsonProperty("endDate")
    private LocalDate endDate;

}
