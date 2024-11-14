package com.airtel.buyer.airtelboe.dto.fetchitemcode.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FetchItemCodeData {

    @JsonProperty("itemCodeName")
    private String itemCodeName;

    @JsonProperty("itemCodeValue")
    private String itemCodeValue;

}
