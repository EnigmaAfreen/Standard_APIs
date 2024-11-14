package com.airtel.buyer.airtelboe.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Ip {

    public BigDecimal getShipmentId();

    public String getPoNo();

    public String getOuName();

    public String getBoeNo();

    public LocalDate getBoeDate();

    public String getStatus();

    public LocalDateTime getCreationDate();

    public String getAssignedChaId();

    public BigDecimal getBucketNo();

    public String getInvoiceNumbers();

    public String ouName();

    public String getVendorName();

    public BigDecimal getOrgId();

    public BigDecimal getPartnerVendorCode();

    public String getLob();

    public String getAttribute1();

    public String getAttribute2();

    public String getChaAgent();

    public String getAttribute3();

}
