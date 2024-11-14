package com.airtel.buyer.airtelboe.dto.fetchchecklist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomInfoRes {

    @JsonProperty("chaCode")
    private String chaCode;

    @JsonProperty("pdBondNo")
    private BigDecimal pdBondNo;

    @JsonProperty("boeType")
    private String boeType;

    @JsonProperty("stampColFreeNoDate")
    private String stampColFreeNoDate;

}
