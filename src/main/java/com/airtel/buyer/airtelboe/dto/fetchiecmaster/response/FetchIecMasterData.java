package com.airtel.buyer.airtelboe.dto.fetchiecmaster.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FetchIecMasterData {

    @JsonProperty("legalEntity")
    private String legalEntity;

    @JsonProperty("adCode")
    private String adCode;

    @JsonProperty("iecCode")
    private String iecCode;

    @JsonProperty("address")
    private String address;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("cityName")
    private String cityName;

    @JsonProperty("pan")
    private String pan;

    @JsonProperty("gstn")
    private String gstn;

    @JsonProperty("branchCode")
    private BigDecimal branchCode;

    @JsonProperty("state")
    private String state;

}
