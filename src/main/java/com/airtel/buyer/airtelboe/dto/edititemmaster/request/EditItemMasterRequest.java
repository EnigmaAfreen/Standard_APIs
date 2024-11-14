package com.airtel.buyer.airtelboe.dto.edititemmaster.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EditItemMasterRequest {

    @JsonProperty("itemId")
    private BigDecimal itemId;

    @JsonProperty("hsn")
    private String hsn;

    @JsonProperty("dutyCategory")
    private String dutyCategory;

    @JsonProperty("exemptionNotificationNo")
    private String exemptionNotificationNo;

}
