package com.airtel.buyer.airtelboe.dto.scmoulist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OuListResponseData {

    @JsonProperty("orgId")
    private BigDecimal orgId;

    @JsonProperty("ouName")
    private String ouName;
}
