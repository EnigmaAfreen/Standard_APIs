package com.airtel.buyer.airtelboe.dto.scmrfiresponse.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShipmentDocReq {

    @JsonProperty("docName")
    private String docName;

    @JsonProperty("docId")
    private BigDecimal docId;

    @JsonProperty("contentId")
    private String contentId;

}
