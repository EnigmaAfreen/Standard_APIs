package com.airtel.buyer.airtelboe.dto.fetchboemismatch.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MismatchDoc {

    @JsonProperty("docName")
    private String docName;

    @JsonProperty("contentId")
    private String contentId;

}
