package com.airtel.buyer.airtelboe.dto.ddstatus.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DdStatusRequest {

    @JsonProperty("filterRequest")
    private DdStatusFilterRequest ddStatusFilterRequest;
}
