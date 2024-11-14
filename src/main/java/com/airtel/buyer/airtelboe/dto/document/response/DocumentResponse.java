package com.airtel.buyer.airtelboe.dto.document.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentResponse {

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("contentId")
    private String contentId;

    @JsonProperty("docUrl")
    private String docUrl;
}
