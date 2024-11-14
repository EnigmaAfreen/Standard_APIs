package com.airtel.buyer.airtelboe.dto.enddateitemmaster.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EndDateItemMasterData {

    @JsonProperty("itemId")
    private BigDecimal itemId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("itemIdErrMsg")
    private String itemIdErrMsg;

    @JsonProperty("endDateErrMsg")
    private String endDateErrMsg;

}
