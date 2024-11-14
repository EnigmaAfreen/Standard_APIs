package com.airtel.buyer.airtelboe.dto.statustracker.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseData {

    @JsonProperty("responsibility")
    private String actionBy;
    @JsonProperty("action")
    private String refDescription;
    @JsonProperty("actionDate")
    private String actionDate;
    @JsonProperty("status")
    private String action;
    @JsonProperty("rejectionReason")
    private String commentHeader;
    @JsonProperty("addInfo")
    private String attribute2;


}
