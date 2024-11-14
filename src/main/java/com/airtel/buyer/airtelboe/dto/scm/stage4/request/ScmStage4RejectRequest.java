package com.airtel.buyer.airtelboe.dto.scm.stage4.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ScmStage4RejectRequest {

    private BigDecimal shipmentId;
    private String rejectReason;
    private String rejectDescription;
}
