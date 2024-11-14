package com.airtel.buyer.airtelboe.dto.fetchchaagentlist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChaAgentList {

    @JsonProperty("chaCode")
    private String chaCode;

    @JsonProperty("chaName")
    private String chaName;

}
