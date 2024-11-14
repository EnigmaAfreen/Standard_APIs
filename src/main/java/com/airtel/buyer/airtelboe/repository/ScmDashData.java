package com.airtel.buyer.airtelboe.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ScmDashData {

    public String getOuName();

    public BigDecimal getAirtelBoeRefNo();

    public String getBoeNo();

    public LocalDate getBoeDate();

    public String getAwbBlNo();

    public String getPoNo();

    public String getInvoiceNo();

    public String getPartnerName();

    public String getIncoTerm();

    public String getModeOfShipment();

    public LocalDate getShipmentArrivalDate();

    public String getDestinationPort();

    public String getChaAgent();

    public BigDecimal getStatus();

    public String getStatusDesc();

    public LocalDate getPaymentDate();

    public BigDecimal getBucketNo();

    public LocalDateTime getCreationDate();

    public BigDecimal getOrgId();

    public String getLob();

    public String getAssignedChaId();

    public String getDutyAmount();

}
