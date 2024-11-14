package com.airtel.buyer.airtelboe.dto.scmrfiresponse.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ShipmentLineRes {

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("invoiceHeaderId")
    private BigDecimal invoiceHeaderId;

    @JsonProperty("lines")
    private List<LineRes> lineResList;

    @JsonProperty("invoiceNumberErrMsg")
    private String invoiceNumberErrMsg;

    @JsonProperty("invoiceHeaderIdErrMsg")
    private String invoiceHeaderIdErrMsg;

    @JsonProperty("linesErrMsg")
    private String lineResListErrMsg;

}
