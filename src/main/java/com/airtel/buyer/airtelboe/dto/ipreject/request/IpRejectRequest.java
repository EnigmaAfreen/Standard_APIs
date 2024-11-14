package com.airtel.buyer.airtelboe.dto.ipreject.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IpRejectRequest {

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("chaName")
    private String chaName;

    @JsonProperty("rejectionReason")
    private String rejectionReason;

    @JsonProperty("rejectionActualAmount")
    private String rejectionActualAmount;

    @JsonProperty("rejectionDescription")
    private String rejectionDescription;

}
