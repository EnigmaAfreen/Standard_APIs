package com.airtel.buyer.airtelboe.dto.indiancity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IndianCityData {

    @JsonProperty("city")
    private String city;

    @JsonProperty("cityCode")
    private String cityCode;

}
