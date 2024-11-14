package com.airtel.buyer.airtelboe.dto.fetchdutycategory.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FetchDutyCategoryData {

    @JsonProperty("dutyCategoryName")
    private String dutyCategoryName;

    @JsonProperty("dutyCategoryValue")
    private String dutyCategoryValue;

}
