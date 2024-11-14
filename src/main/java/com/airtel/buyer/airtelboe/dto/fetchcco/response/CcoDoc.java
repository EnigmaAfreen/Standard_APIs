package com.airtel.buyer.airtelboe.dto.fetchcco.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CcoDoc {

    @JsonProperty("docName")
    private String docName;

    @JsonProperty("contentId")
    private String contentId;

}
