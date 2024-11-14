package com.airtel.buyer.airtelboe.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Error {

    @JsonProperty("code")
    private String code;

    @JsonProperty("trace_id")
    private String traceId;

}
