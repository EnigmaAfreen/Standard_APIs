package com.airtel.buyer.airtelboe.dto.scm.stage2.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Fta {

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("invoiceHeaderId")
    private BigDecimal invoiceHeaderId;

    @JsonProperty("lines")
    private List<LineInfo> lineInfoList;
}
