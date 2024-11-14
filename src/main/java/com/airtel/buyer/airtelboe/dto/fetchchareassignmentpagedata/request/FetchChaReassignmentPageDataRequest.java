package com.airtel.buyer.airtelboe.dto.fetchchareassignmentpagedata.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FetchChaReassignmentPageDataRequest {

    @JsonProperty("fromChaCode")
    private String fromChaCode;
    
//    @JsonProperty("checkListStatus")
//    private String checkListStatus;
}
