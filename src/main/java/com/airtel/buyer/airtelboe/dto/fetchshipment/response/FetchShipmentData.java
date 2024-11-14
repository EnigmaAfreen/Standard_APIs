package com.airtel.buyer.airtelboe.dto.fetchshipment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FetchShipmentData {

    @JsonProperty("shipmentInfo")
    private ShipmentInfo shipmentInfo;

    @JsonProperty("shipmentLines")
    private List<ShipmentLineInfo> shipmentLineInfoList;

    @JsonProperty("pointOfContactInfo")
    private PointOfContactInfo pointOfContactInfo;

    @JsonProperty("shipmentDocs")
    private List<ShipmentDoc> shipmentDocList;

    @JsonProperty("checklistHeaderInfo")
    private CheckListHeaderInfo checkListHeaderInfo;
}
