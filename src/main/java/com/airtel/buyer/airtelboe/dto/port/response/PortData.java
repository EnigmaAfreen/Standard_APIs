package com.airtel.buyer.airtelboe.dto.port.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PortData {

    @JsonProperty("portName")
    private String portName;

    @JsonProperty("portCode")
    private String portCode;

    @JsonProperty("portType")
    private String portType;
}
