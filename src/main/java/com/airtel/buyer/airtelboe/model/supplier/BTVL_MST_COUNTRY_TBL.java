package com.airtel.buyer.airtelboe.model.supplier;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class BTVL_MST_COUNTRY_TBL {

    @Id
    @Column(name = "COUNTRY_ID")
    private BigDecimal countryId;

    @Column(name = "COUNTRY_NAME")
    private String countryName;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "COUNTRY_PH_CODE")
    private String countryPhCode;

    @Column(name = "PURGE_FLAG")
    private Integer purgeFlag;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "CREATEDBY")
    private String createdby;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @Column(name = "MODIFIEDBY")
    private String modifiedby;
}
