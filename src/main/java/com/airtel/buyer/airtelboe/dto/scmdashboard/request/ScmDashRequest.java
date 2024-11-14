package com.airtel.buyer.airtelboe.dto.scmdashboard.request;

import com.airtel.buyer.airtelboe.dto.scmdashboard.response.ScmFilterResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ScmDashRequest {

    @JsonProperty("bucket")
    private BigDecimal bucket;

    @JsonProperty("filterRequest")
    private ScmDashFilterRequest scmDashFilterRequest;
}
