package com.airtel.buyer.airtelboe.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface WpcMaster {

    public BigDecimal getWpcId();

    public String getLegalEntity();

    public LocalDate getLicenseDate();

    public String getLicenseNo();

    public String getPortCode();

    public String getItemCode();

    public BigDecimal getWpcQty();

    public Integer getPurgeFlag();

    public String getLicenceDate();

    public BigDecimal getBalanceQty();

    public LocalDate getEndDate();

}
