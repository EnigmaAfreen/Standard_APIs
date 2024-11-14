package com.airtel.buyer.airtelboe.model.supplier;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class BTVL_SPORTAL_LOOKUP_VALUES {

    @Column(name = "LOOKUP_TYPE")
    private String lookupType;

    @Id
    @Column(name = "LOOKUP_CODE")
    private String lookupCode;

    @Column(name = "MEANING")
    private String meaning;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ENABLED_FLAG")
    private String enabledFlag;

    @Column(name = "START_DATE_ACTIVE")
    private LocalDate startDateActive;

    @Column(name = "END_DATE_ACTIVE")
    private LocalDate endDateActive;

    @Column(name = "CREATED_BY")
    private BigDecimal createdBy;

    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;

    @Column(name = "LAST_UPDATED_BY")
    private BigDecimal lastUpdatedBy;

    @Column(name = "LAST_UPDATE_LOGIN")
    private BigDecimal lastUpdateLogin;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDate lastUpdateDate;

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

    @Column(name = "TAG")
    private String tag;

}
