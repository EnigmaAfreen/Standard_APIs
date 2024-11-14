package com.airtel.buyer.airtelboe.dto.fetchchaagentlist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChaAgentListData {

    @JsonProperty("fromChaAgentList")
    private List<ChaAgentList> fromChaAgentList;

    @JsonProperty("toChaAgentList")
    private List<ChaAgentList> toChaAgentList;

}
