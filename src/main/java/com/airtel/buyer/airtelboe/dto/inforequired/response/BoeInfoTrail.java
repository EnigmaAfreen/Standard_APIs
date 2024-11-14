package com.airtel.buyer.airtelboe.dto.inforequired.response;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class BoeInfoTrail {

    private BigDecimal trailId;
    private BigDecimal shipmentId;
    private BigDecimal bucketNo;
    private BigDecimal status;
    private String commentHeader;
    private String commentLine;
    private String actionBy;
    private String creationDate;
}
