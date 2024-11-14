package com.airtel.buyer.airtelboe.model.erp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class FND_CURRENCIES_VL {

    @Column(name = "DERIVE_FACTOR")
    private BigDecimal deriveFactor;

    @Column(name = "DERIVE_TYPE")
    private String deriveType;

    @Column(name = "DERIVE_EFFECTIVE")
    private LocalDate deriveEffective;

    @Column(name = "DESCRIPTION")
    private String description;

//    @Column(name = "ROW_ID")
//    private  rowId;

    @Column(name = "GLOBAL_ATTRIBUTE7")
    private String globalAttribute7;

    @Column(name = "GLOBAL_ATTRIBUTE8")
    private String globalAttribute8;

    @Column(name = "GLOBAL_ATTRIBUTE9")
    private String globalAttribute9;

    @Column(name = "GLOBAL_ATTRIBUTE10")
    private String globalAttribute10;

    @Column(name = "GLOBAL_ATTRIBUTE11")
    private String globalAttribute11;

    @Column(name = "GLOBAL_ATTRIBUTE12")
    private String globalAttribute12;

    @Column(name = "GLOBAL_ATTRIBUTE13")
    private String globalAttribute13;

    @Column(name = "GLOBAL_ATTRIBUTE14")
    private String globalAttribute14;

    @Column(name = "GLOBAL_ATTRIBUTE15")
    private String globalAttribute15;

    @Column(name = "GLOBAL_ATTRIBUTE16")
    private String globalAttribute16;

    @Column(name = "GLOBAL_ATTRIBUTE17")
    private String globalAttribute17;

    @Column(name = "GLOBAL_ATTRIBUTE18")
    private String globalAttribute18;

    @Column(name = "GLOBAL_ATTRIBUTE19")
    private String globalAttribute19;

    @Column(name = "GLOBAL_ATTRIBUTE20")
    private String globalAttribute20;

    @Id
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDate lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private BigDecimal lastUpdatedBy;

    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;

    @Column(name = "CREATED_BY")
    private BigDecimal createdBy;

    @Column(name = "LAST_UPDATE_LOGIN")
    private BigDecimal lastUpdateLogin;

    @Column(name = "ENABLED_FLAG")
    private String enabledFlag;

    @Column(name = "CURRENCY_FLAG")
    private String currencyFlag;

    @Column(name = "ISSUING_TERRITORY_CODE")
    private String issuingTerritoryCode;

    @Column(name = "PRECISION")
    private BigDecimal precision;

    @Column(name = "EXTENDED_PRECISION")
    private BigDecimal extendedPrecision;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "START_DATE_ACTIVE")
    private LocalDate startDateActive;

    @Column(name = "END_DATE_ACTIVE")
    private LocalDate endDateActive;

    @Column(name = "MINIMUM_ACCOUNTABLE_UNIT")
    private BigDecimal minimumAccountableUnit;

    @Column(name = "CONTEXT")
    private String context;

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

    @Column(name = "ISO_FLAG")
    private String isoFlag;

    @Column(name = "GLOBAL_ATTRIBUTE_CATEGORY")
    private String globalAttributeCategory;

    @Column(name = "GLOBAL_ATTRIBUTE1")
    private String globalAttribute1;

    @Column(name = "GLOBAL_ATTRIBUTE2")
    private String globalAttribute2;

    @Column(name = "GLOBAL_ATTRIBUTE3")
    private String globalAttribute3;

    @Column(name = "GLOBAL_ATTRIBUTE4")
    private String globalAttribute4;

    @Column(name = "GLOBAL_ATTRIBUTE5")
    private String globalAttribute5;

    @Column(name = "GLOBAL_ATTRIBUTE6")
    private String globalAttribute6;

    @Column(name = "NAME")
    private String name;

}
