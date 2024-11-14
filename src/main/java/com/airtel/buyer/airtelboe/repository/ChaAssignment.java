package com.airtel.buyer.airtelboe.repository;

import java.math.BigDecimal;

public interface ChaAssignment {
    public BigDecimal getChaId();
    public String getLeName();
    public String getOu();
    public String getPortCode();
    public String getChaCode();
    public String getChaEmail();
    public String getChaName();
    public BigDecimal getAssignedTasks();
}
