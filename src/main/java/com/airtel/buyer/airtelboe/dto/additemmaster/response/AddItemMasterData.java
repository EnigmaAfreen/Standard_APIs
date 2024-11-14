package com.airtel.buyer.airtelboe.dto.additemmaster.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddItemMasterData {

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("hsn")
    private String hsn;

    @JsonProperty("dutyCategory")
    private String dutyCategory;

    @JsonProperty("exemptionNotificationNo")
    private String exemptionNotificationNo;

    @JsonProperty("itemCodeErrMsg")
    private String itemCodeErrMsg;

    @JsonProperty("hsnErrMsg")
    private String hsnErrMsg;

    @JsonProperty("dutyCategoryErrMsg")
    private String dutyCategoryErrMsg;

    @JsonProperty("exemptionNotificationNoErrMsg")
    private String exemptionNotificationNoErrMsg;

}
