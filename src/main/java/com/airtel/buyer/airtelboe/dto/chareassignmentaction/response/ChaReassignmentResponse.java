package com.airtel.buyer.airtelboe.dto.chareassignmentaction.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChaReassignmentResponse {


    @JsonProperty("fromChaAgentErrMsg")
    private String fromChaAgentErrMsg;

    @JsonProperty("toChaAgentErrMsg")
    private String toChaAgentErrMsg;

    @JsonProperty("descriptionErrMsg")
    private String descriptionErrMsg;

    @JsonProperty("acknowledgementErrMsg")
    private String acknowledgementErrMsg;

    @JsonProperty("popUpMessage")
    private String popUpMessage;

    @JsonProperty("status")
    private String status;

}
