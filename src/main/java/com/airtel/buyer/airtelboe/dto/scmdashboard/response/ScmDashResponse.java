package com.airtel.buyer.airtelboe.dto.scmdashboard.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ScmDashResponse {

    @JsonProperty("bucket")
    private BigDecimal bucket;

    @JsonProperty("filter")
    private ScmFilterResponse scmFilterResponse;

    @JsonProperty("bucketData")
    private ScmBucketData scmBucketData;
}
