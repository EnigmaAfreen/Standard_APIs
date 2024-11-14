package com.airtel.buyer.airtelboe.repository;

import java.math.BigDecimal;

public interface BoeInfoReq {

    public String getCreationDate();
    public String getActionBy();
    public String getCommentLine();
    public String getCommentHeader();
    public BigDecimal getStatus();
    public BigDecimal getBucketNo();
    public BigDecimal getShipmentId();
    public BigDecimal getTrailId();
}
