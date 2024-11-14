package com.airtel.buyer.airtelboe.dto.fetchlegalentity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FetchLegalEntityData {

    @JsonProperty("leName")
    private String leName;

    @JsonProperty("leValue")
    private String leValue;

}
