package com.airtel.buyer.airtelboe.dto.fetchchecklist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CheckListHeaderRes {

    @JsonProperty("customerInfo")
    private CustomerInfoRes customerInfoRes;

    @JsonProperty("supplierInfo")
    private SupplierInfoRes supplierInfoRes;

    @JsonProperty("shipmentInfo")
    private ShipmentInfoRes shipmentInfoRes;

    @JsonProperty("customInfo")
    private CustomInfoRes customInfoRes;

    @JsonProperty("totalDutyAmt")
    private String totalDutyAmt;

    @JsonProperty("miscCharges")
    private String miscCharges;

}
