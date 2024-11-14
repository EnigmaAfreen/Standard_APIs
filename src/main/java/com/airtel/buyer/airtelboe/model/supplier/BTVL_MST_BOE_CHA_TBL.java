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
public class BTVL_MST_BOE_CHA_TBL {

    @Column(name = "LE_NAME")
    private String leName;

    @Column(name = "LOB")
    private String lob;

    @Column(name = "OU")
    private String ou;

    @Column(name = "PORT_CODE")
    private String portCode;

    @Column(name = "CHA_NAME")
    private String chaName;

    @Column(name = "CHA_CODE")
    private String chaCode;

    @Column(name = "CHA_EMAIL")
    private String chaEmail;

    @Column(name = "CHA_PHONE_NO")
    private String chaPhoneNo;

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

    @Column(name = "CIRCLE_NAME")
    private String circleName;

    @Column(name = "PORT_DESCRIPTION")
    private String portDescription;

    @Id
    @Column(name = "CHA_ID")
    private BigDecimal chaId;

    @Column(name = "PORT_NAME")
    private String portName;

    @Column(name = "SOB_PER")
    private BigDecimal sobPer;

    @Column(name = "AUTHORITY_NAME")
    private String authorityName;

    @Column(name = "AUTHORITY_CODE")
    private String authorityCode;

    @Column(name = "PAYMENT_TYPE")
    private  String paymentType;


}
