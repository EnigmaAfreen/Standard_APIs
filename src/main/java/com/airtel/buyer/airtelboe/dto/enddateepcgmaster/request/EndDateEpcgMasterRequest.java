package com.airtel.buyer.airtelboe.dto.enddateepcgmaster.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EndDateEpcgMasterRequest {

    @JsonProperty("epcgId")
    private BigDecimal epcgId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("endDate")
    private LocalDate endDate;

}
