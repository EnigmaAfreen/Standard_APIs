package com.airtel.buyer.airtelboe.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface WpcInfo {

    public String getOuName();

    public String getBoeNo();

    public LocalDate getBoeDate();

    public String getAwbBol();

    public String getPoNo();

    public BigDecimal getAirtelBoeRefNo();

    public String getInvoiceNumber();

    public String getVendorName();

    public String getIncoTerm();

    public String getShipmentMode();

    public LocalDate getShipmentArrivalDate();

    public String getDestinationPort();

    public String getAssignedChaId();

    public BigDecimal getStatus();

    public String getLob();

    public BigDecimal getOrgId();

    public LocalDateTime getCreationDate();

    public String getStatusDesc();

    public String getChaAgent();
}
