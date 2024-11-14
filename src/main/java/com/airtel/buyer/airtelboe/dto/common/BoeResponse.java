package com.airtel.buyer.airtelboe.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BoeResponse<T> {

    @JsonProperty("error")
    private Error error;

    @JsonProperty("data")
    private T data;

}
