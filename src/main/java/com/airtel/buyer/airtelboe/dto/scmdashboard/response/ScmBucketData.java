package com.airtel.buyer.airtelboe.dto.scmdashboard.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ScmBucketData {

    @JsonProperty("allShipmentCount")
    private long allShipmentCount = 0;

    @JsonProperty("ddpShipmentCount")
    private long ddpShipmentCount = 0;

    @JsonProperty("ffAssignmentCount")
    private long ffAssignmentCount = 0;

    @JsonProperty("protestedBoeApprovalCount")
    private long protestedBoeApprovalCount = 0;

    @JsonProperty("rfiCount")
    private long rfiCount = 0;

    @JsonProperty("boeMisMatchCount")
    private long boeMisMatchCount = 0;

    @JsonProperty("boeRejectedByScmCount")
    private long boeRejectedByScmCount = 0;

    @JsonProperty("records")
    private List<ScmDashRecords> recordsList;
}
