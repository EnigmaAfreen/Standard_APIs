package com.airtel.buyer.airtelboe.repository;


import java.math.BigDecimal;
import java.util.Date;

public interface DdStatusData {

    public BigDecimal getPartnerVendorCode();

    public String getChaName();

    public String getDdReferrenceNo();

    public String getStatus();

    public Date getDdDate();

    public String getPort();

    public String getPayable();

    public BigDecimal getAmount();

    public String getDocumentNo();

    public String getInvoiceNumber();

    public String getShipmentAwbNo();

    public BigDecimal getAirtelReferrenceNo();

    public String getBoeNo();

    public BigDecimal getDdId();

}
