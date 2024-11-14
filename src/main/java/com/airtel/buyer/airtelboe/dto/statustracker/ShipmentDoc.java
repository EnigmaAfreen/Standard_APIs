package com.airtel.buyer.airtelboe.dto.statustracker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShipmentDoc {
    @JsonProperty("docName")
    private String docName;

    @JsonProperty("contentId")
    private String contentId;
}
