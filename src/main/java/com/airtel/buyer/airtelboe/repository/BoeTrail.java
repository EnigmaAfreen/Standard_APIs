package com.airtel.buyer.airtelboe.repository;

import java.math.BigDecimal;


public interface BoeTrail {
    public BigDecimal getTrail_id();

    public BigDecimal getShipment_id();

    public String getAction_by();

    public String getRef_description();

    public BigDecimal getStatus();

    public String getAction_date();

    public String getAction();

    public BigDecimal getStage();

    public BigDecimal getBucket_no();

    public String getComment_header();

    public String getComment_line();

    public String getInvoice_submission_date();

    public String getCHA_CODE();

    public String getAttribute1();

    public String getAttribute2();

    public String getASSIGNED_CCO_ID();
}
