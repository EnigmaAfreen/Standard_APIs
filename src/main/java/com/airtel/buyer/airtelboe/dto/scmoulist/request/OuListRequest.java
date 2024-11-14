package com.airtel.buyer.airtelboe.dto.scmoulist.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OuListRequest {

    @JsonProperty("lobName")
    private String lobName;
}
