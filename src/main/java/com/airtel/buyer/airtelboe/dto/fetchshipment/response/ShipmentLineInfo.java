package com.airtel.buyer.airtelboe.dto.fetchshipment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ShipmentLineInfo {

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("invoiceHeaderId")
    private BigDecimal invoiceHeaderId;

    @JsonProperty("invoiceId")
    private String invoiceId;

    @JsonProperty("odType")
    private String odType;


//    @JsonProperty("lineCountry")
//    private String lineCountry;

    @JsonProperty("lines")
    private List<LineInfo> lineInfoList;

}
