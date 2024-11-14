package com.airtel.buyer.airtelboe.dto.fetchitemmaster.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FetchItemMasterData {

    @JsonProperty("itemId")
    private BigDecimal itemId;

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("hsn")
    private String hsn;

    @JsonProperty("dutyCategory")
    private String dutyCategory;

    @JsonProperty("exemptionNotificationNo")
    private String exemptionNotificationNo;

    @JsonProperty("endDate")
    private LocalDate endDate;

}
