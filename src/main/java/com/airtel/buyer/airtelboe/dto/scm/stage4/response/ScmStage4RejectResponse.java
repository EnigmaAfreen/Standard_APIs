package com.airtel.buyer.airtelboe.dto.scm.stage4.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ScmStage4RejectResponse {

    private BigDecimal shipmentId;
    private String rejectReason;
    private String rejectDescription;
    private String status;
}
