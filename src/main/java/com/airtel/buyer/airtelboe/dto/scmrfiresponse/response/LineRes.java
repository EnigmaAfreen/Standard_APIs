package com.airtel.buyer.airtelboe.dto.scmrfiresponse.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LineRes {

    @JsonProperty("shipmentLineId")
    private BigDecimal shipmentLineId;

//    @JsonProperty("itemCode")
//    private String itemCode;
//
//    @JsonProperty("itemDescription")
//    private String itemDescription;
//
//    @JsonProperty("hsnCode")
//    private String hsnCode;

    @JsonProperty("countryOfOrigin")
    private String countryOfOrigin;

    @JsonProperty("isFtaChecked")
    private String isFtaChecked;

    @JsonProperty("isAntiDumpingChecked")
    private String isAntiDumpingChecked;

//    @JsonProperty("isWpcChecked")
//    private String isWpcChecked;
//
//    @JsonProperty("itemCodeErrMsg")
//    private String itemCodeErrMsg;
//
//    @JsonProperty("itemDescriptionErrMsg")
//    private String itemDescriptionErrMsg;
//
//    @JsonProperty("hsnCodeErrMsg")
//    private String hsnCodeErrMsg;

    @JsonProperty("shipmentLineIdErrMsg")
    private String shipmentLineIdErrMsg;

    @JsonProperty("countryOfOriginErrMsg")
    private String countryOfOriginErrMsg;

    @JsonProperty("isFtaCheckedErrMsg")
    private String isFtaCheckedErrMsg;

    @JsonProperty("isAntiDumpingCheckedErrMsg")
    private String isAntiDumpingCheckedErrMsg;

//    @JsonProperty("isWpcCheckedErrMsg")
//    private String isWpcCheckedErrMsg;

}
