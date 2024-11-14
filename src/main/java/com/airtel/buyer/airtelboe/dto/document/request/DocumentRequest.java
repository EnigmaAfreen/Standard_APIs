package com.airtel.buyer.airtelboe.dto.document.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentRequest {

    @JsonProperty("fileName")
    private String fileName;

}
