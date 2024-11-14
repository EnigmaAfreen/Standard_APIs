package com.airtel.buyer.airtelboe.dto.scmrfiresponse.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ShipmentLineReq {

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("invoiceHeaderId")
    private BigDecimal invoiceHeaderId;

    @JsonProperty("lines")
    private List<LineReq> lineReqList;

}
