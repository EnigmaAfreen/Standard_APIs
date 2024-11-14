package com.airtel.buyer.airtelboe.dto.ccoaction.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CcoActionRequest {

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("chaName")
    private String chaName;

}
