package com.airtel.buyer.airtelboe.model.supplier;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class BTVL_BOE_OU_LOB_MAPP_TBL {

    @Id
    @Column(name = "ORG_ID")
    private BigDecimal orgId;

    @Column(name = "OU_NAME")
    private String ouName;

    @Column(name = "LOB")
    private String lob;

    @Column(name = "LE_NAME")
    private String leName;

    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;

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

}
