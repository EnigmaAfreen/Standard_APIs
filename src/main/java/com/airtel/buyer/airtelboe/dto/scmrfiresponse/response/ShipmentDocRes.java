package com.airtel.buyer.airtelboe.dto.scmrfiresponse.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShipmentDocRes {

    @JsonProperty("docName")
    private String docName;

    @JsonProperty("docId")
    private BigDecimal docId;

    @JsonProperty("contentId")
    private String contentId;

    @JsonProperty("docNameErrMsg")
    private String docNameErrMsg;

    @JsonProperty("docIdErrMsg")
    private String docIdErrMsg;

    @JsonProperty("contentIdErrMsg")
    private String contentIdErrMsg;

}
