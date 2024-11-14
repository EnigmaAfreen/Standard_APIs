package com.airtel.buyer.airtelboe.dto.country.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CountryData {

    @JsonProperty("country")
    private String country;

    @JsonProperty("countryCode")
    private String countryCode;

}