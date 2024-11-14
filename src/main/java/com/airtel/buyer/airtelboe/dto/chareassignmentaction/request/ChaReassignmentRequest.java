package com.airtel.buyer.airtelboe.dto.chareassignmentaction.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ChaReassignmentRequest {

    @JsonProperty("fromChaAgent")
    private String fromChaAgent;

    @JsonProperty("toChaAgent")
    private String toChaAgent;

    @JsonProperty("checkListStatus")
    private String checkListStatus;

    @JsonProperty("acknowledgement")
    private String acknowledgement;

    @JsonProperty("description")
    private String description;

    @JsonProperty("shipmentId")
    private List<BigDecimal> shipmentId;


}
