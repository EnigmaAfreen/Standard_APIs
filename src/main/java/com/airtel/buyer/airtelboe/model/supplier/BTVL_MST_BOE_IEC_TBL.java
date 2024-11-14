package com.airtel.buyer.airtelboe.model.supplier;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class BTVL_MST_BOE_IEC_TBL {

    @Id
    @Column(name = "IEC_ID")
    private BigDecimal iecId;

    @Column(name = "LE_NAME")
    private String leName;

    @Column(name = "AD_CODE")
    private String adCode;

    @Column(name = "IEC_CODE")
    private String iecCode;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "ZIP")
    private String zip;

    @Column(name = "CITY")
    private String city;

    @Column(name = "PAN")
    private String pan;

    @Column(name = "END_DATE")
    private LocalDate endDate;

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

    @Column(name = "GSTIN_NUMBER")
    private String gstinNumber;

    @Column(name = "BRANCH_CODE")
    private BigDecimal branchCode;

    @Column(name = "STATE")
    private String state;

}
