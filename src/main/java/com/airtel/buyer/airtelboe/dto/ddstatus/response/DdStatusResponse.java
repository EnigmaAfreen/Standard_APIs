package com.airtel.buyer.airtelboe.dto.ddstatus.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DdStatusResponse {

    @JsonProperty("filterResponse")
    private DdStatusFilterResponse ddStatusFilterResponse;

    @JsonProperty("records")
    private List<DdStatusRecords> recordsList;

}
