package com.airtel.buyer.airtelboe.dto.fetchantidumpingmaster.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FetchAntiDumpingMasterData {

    @JsonProperty("country")
    private String country;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("supplierName")
    private String supplierName;

    @JsonProperty("supplierCode")
    private String supplierCode;

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("itemDescription")
    private String itemDescription;

    @JsonProperty("dutyPer")
    private String dutyPer;

}
