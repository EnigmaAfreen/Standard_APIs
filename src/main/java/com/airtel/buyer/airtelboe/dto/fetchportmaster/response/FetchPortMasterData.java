package com.airtel.buyer.airtelboe.dto.fetchportmaster.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FetchPortMasterData {

    @JsonProperty("portCode")
    private String portCode;

    @JsonProperty("portName")
    private String portName;

    @JsonProperty("portType")
    private String portType;

    @JsonProperty("country")
    private String country;

    @JsonProperty("countryCode")
    private String countryCode;

}
