package com.airtel.buyer.airtelboe.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface OfflineCco {

    public String getOuName();

    public String getBoeNo();

    public LocalDate getBoeDate();

    public BigDecimal getShipmentId();

    public String getPoNo();

    public String getInvoiceNumbers();

    public String getChaName();

    public String getShipmentMode();

    public LocalDate getShipmentArrivalDate();

    public String getVendorName();

    public BigDecimal getOrgId();

    public String getStatus();

    public String getStatusDesc();

    public String getAttribute1();

    public String getAttribute2();

    public String getAttribute3();

    public String getAttribute4();

    public BigDecimal getBucketNo();

    public BigDecimal getStage();

    public LocalDateTime getCreationDate();

    public BigDecimal getPartnerVendorCode();

    public String getDutyAmount();

}
