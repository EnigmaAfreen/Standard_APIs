package com.airtel.buyer.airtelboe.dto.wpcinfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class WpcInfoResponse {

    @JsonProperty("filter")
    private WpcInfoFilterResponse wpcInfoFilterResponse;

    @JsonProperty("records")
    private List<WpcInfoDashRecords> wpcInfoDashRecords;

}