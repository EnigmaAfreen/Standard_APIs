package com.airtel.buyer.airtelboe.dto.country.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class CheckFtaData {

    @JsonProperty("isFta")
    private Boolean isFta;

    @JsonProperty("isAntiDumping")
    private Boolean isAntiDumping;
}
