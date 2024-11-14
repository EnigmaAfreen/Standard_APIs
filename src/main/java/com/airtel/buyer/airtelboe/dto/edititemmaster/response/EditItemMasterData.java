package com.airtel.buyer.airtelboe.dto.edititemmaster.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EditItemMasterData {

    @JsonProperty("itemId")
    private BigDecimal itemId;

    @JsonProperty("hsn")
    private String hsn;

    @JsonProperty("dutyCategory")
    private String dutyCategory;

    @JsonProperty("exemptionNotificationNo")
    private String exemptionNotificationNo;

    @JsonProperty("itemIdErrMsg")
    private String itemIdErrMsg;

    @JsonProperty("hsnErrMsg")
    private String hsnErrMsg;

    @JsonProperty("dutyCategoryErrMsg")
    private String dutyCategoryErrMsg;

    @JsonProperty("exemptionNotificationNoErrMsg")
    private String exemptionNotificationNoErrMsg;

}
