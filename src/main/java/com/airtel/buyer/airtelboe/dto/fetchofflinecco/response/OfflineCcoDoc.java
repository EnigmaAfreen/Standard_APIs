package com.airtel.buyer.airtelboe.dto.fetchofflinecco.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OfflineCcoDoc {

    @JsonProperty("docName")
    private String docName;

    @JsonProperty("contentId")
    private String contentId;

}
