package com.airtel.buyer.airtelboe.dto.fetchshipment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CheckListHeaderInfo {

    @JsonProperty("shiRotationDate")
    private LocalDate shiRotationDate;

    @JsonProperty("interestPenaltyAmt")
    private BigDecimal interestPenaltyAmt;

    @JsonProperty("demurrageAmt")
    private BigDecimal demurrageAmt;
}
