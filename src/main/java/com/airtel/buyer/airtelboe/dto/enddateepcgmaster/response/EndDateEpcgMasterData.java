package com.airtel.buyer.airtelboe.dto.enddateepcgmaster.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EndDateEpcgMasterData {

    @JsonProperty("epcgId")
    private BigDecimal epcgId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("epcgIdErrMsg")
    private String epcgIdErrMsg;

    @JsonProperty("endDateErrMsg")
    private String endDateErrMsg;

}
