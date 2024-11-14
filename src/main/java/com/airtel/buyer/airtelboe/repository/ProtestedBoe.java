package com.airtel.buyer.airtelboe.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ProtestedBoe {

    public BigDecimal getShipmentId();

    public String getPoNo();

    public String getPortCode();

    public String getReasonForProtest();

    public String getActionStatus();

    public String getBoeNo();

    public LocalDate getBoeDate();

    public LocalDate getboeProtestDate();

    public LocalDate getActionDate();

    public String getChaName();

    public String getOuName();

    public String getVendorName();

    public String getInvoiceNumbers();

    public LocalDateTime getCreationDate();

}
