package com.airtel.buyer.airtelboe.dto.scm.stage4.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubmitStage4Response {

    private BigDecimal shipmentId;
    private String status;
}
