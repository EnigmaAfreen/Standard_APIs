package com.airtel.buyer.airtelboe.dto.enddatewpcmaster.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EndDateWpcMasterData {

    @JsonProperty("wpcId")
    private BigDecimal wpcId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("wpcIdErrMsg")
    private String wpcIdErrMsg;

    @JsonProperty("endDateErrMsg")
    private String endDateErrMsg;

}