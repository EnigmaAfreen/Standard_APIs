package com.airtel.buyer.airtelboe.dto.fetchboedoc.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FetchBoeDocData {

    @JsonProperty("docName")
    private String docName;

    @JsonProperty("contentId")
    private String contentId;

}
