package com.airtel.buyer.airtelboe.dto.iprfi.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IpRfiRequest {

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("rfiReason")
    private String rfiReason;

    @JsonProperty("rfiDescription")
    private String rfiDescription;

}
