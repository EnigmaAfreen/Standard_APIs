package com.airtel.buyer.airtelboe.model.supplier;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class BTVL_WKF_BOE_PROTESTED_TBL {

    @Column(name = "SHIPMENT_ID")
    private BigDecimal shipmentId;

    @Column(name = "PO_NO")
    private String poNo;

    @Column(name = "PORT_CODE")
    private String portCode;

    @Column(name = "REASON_FOR_PROTEST")
    private String reasonForProtest;

    @Column(name = "ACTION_STATUS")
    private String actionStatus;

    @Column(name = "BOE_NO")
    private String boeNo;

    @Column(name = "BOE_DATE")
    private LocalDate boeDate;

    @Column(name = "BOE_PROTEST_DATE")
    private LocalDate boeProtestDate;

    @Column(name = "PURGE_FLAG")
    private Integer purgeFlag;

    @Column(name = "ATTRIBUTE1")
    private String attribute1;

    @Column(name = "ATTRIBUTE2")
    private String attribute2;

    @Column(name = "ATTRIBUTE3")
    private String attribute3;

    @Column(name = "ATTRIBUTE4")
    private String attribute4;

    @Column(name = "ATTRIBUTE5")
    private String attribute5;

    @Column(name = "ATTRIBUTE6")
    private String attribute6;

    @Column(name = "ATTRIBUTE7")
    private String attribute7;

    @Column(name = "ATTRIBUTE8")
    private String attribute8;

    @Column(name = "ATTRIBUTE9")
    private String attribute9;

    @Column(name = "ATTRIBUTE10")
    private String attribute10;

    @Column(name = "ATTRIBUTE11")
    private String attribute11;

    @Column(name = "ATTRIBUTE12")
    private String attribute12;

    @Column(name = "ATTRIBUTE13")
    private String attribute13;

    @Column(name = "ATTRIBUTE14")
    private String attribute14;

    @Column(name = "ATTRIBUTE15")
    private String attribute15;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "CREATEDBY")
    private String createdby;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @Column(name = "MODIFIEDBY")
    private String modifiedby;

    @Column(name = "ASSIGNED_CHA_ID")
    private String assignedChaId;

    @Column(name = "ACTION_DATE")
    private LocalDate actionDate;

    @Column(name = "INVOICE_NUMBERS")
    private String invoiceNumbers;

    @Column(name = "VENDOR_NAME")
    private String vendorName;

    @Column(name = "OU_NAME")
    private String ouName;

    @Id
    @Column(name = "PROTEST_ID")
    private BigDecimal protestId;

}
