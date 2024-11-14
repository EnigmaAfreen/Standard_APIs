package com.airtel.buyer.airtelboe.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface EpcgMaster {

    public BigDecimal getEpcgId();

    public String getLicenceNo();

    public String getLeName();

    public String getPortCode();

    public String getItemCode();

    public BigDecimal getTotItemQty();

    public Integer getPurgeFlag();

    public BigDecimal getBalanceQty();

    public LocalDate getEndDate();

    public LocalDate getLicenceDate();

    public String getItemSerialNum();

    public String getItemDescription();

    public String getLob();

}
