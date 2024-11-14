package com.airtel.buyer.airtelboe.dto.fetchftamaster.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FetchFtaMasterData {

    @JsonProperty("country")
    private String country;

    @JsonProperty("countryCode")
    private String countryCode;

}
