package com.airtel.buyer.airtelboe.dto.scm.stage2.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShipmentDoc {

    @JsonProperty("docId")
    private String docId;

    @JsonProperty("docName")
    private String docName;

    @JsonProperty("contentId")
    private String contentId;

    private String docErrMsg;
}
