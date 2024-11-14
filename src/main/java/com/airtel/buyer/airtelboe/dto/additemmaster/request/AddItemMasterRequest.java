package com.airtel.buyer.airtelboe.dto.additemmaster.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddItemMasterRequest {

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("hsn")
    private String hsn;

    @JsonProperty("dutyCategory")
    private String dutyCategory;

    @JsonProperty("exemptionNotificationNo")
    private String exemptionNotificationNo;

}
