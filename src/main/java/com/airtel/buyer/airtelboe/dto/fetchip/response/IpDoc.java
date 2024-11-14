package com.airtel.buyer.airtelboe.dto.fetchip.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IpDoc {

    @JsonProperty("docName")
    private String docName;

    @JsonProperty("contentId")
    private String contentId;

}
