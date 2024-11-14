package com.airtel.buyer.airtelboe.dto.scmrfiresponse.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LineReq {

//    @JsonProperty("itemCode")
//    private String itemCode;
//
//    @JsonProperty("itemDescription")
//    private String itemDescription;
//
//    @JsonProperty("hsnCode")
//    private String hsnCode;

    @JsonProperty("SHIPMENT_LINE_ID")
    private BigDecimal shipmentLineId;

    @JsonProperty("countryOfOrigin")
    private String countryOfOrigin;

    @JsonProperty("isFtaChecked")
    private String isFtaChecked;

    @JsonProperty("isAntiDumpingChecked")
    private String isAntiDumpingChecked;

//    @JsonProperty("isWpcChecked")
//    private String isWpcChecked;

}
