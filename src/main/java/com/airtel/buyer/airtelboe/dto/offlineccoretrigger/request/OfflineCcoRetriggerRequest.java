package com.airtel.buyer.airtelboe.dto.offlineccoretrigger.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfflineCcoRetriggerRequest {

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("chaName")
    private String chaName;

}
