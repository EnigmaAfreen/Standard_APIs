package com.airtel.buyer.airtelboe.dto.countryphonecode.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CountryPhoneCodeData {

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("countryPhCode")
    private String countryPhoneCode;

}
