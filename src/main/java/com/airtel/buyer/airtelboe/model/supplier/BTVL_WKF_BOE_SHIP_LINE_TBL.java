package com.airtel.buyer.airtelboe.model.supplier;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class BTVL_WKF_BOE_SHIP_LINE_TBL {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oracle_sequence")
    @SequenceGenerator(name = "oracle_sequence", sequenceName = "BTVL_WKF_BOE_SHIP_LINE_TBL_SEQ", allocationSize = 1)
    @Column(name = "SHIPMENT_LINE_ID")
    private BigDecimal shipmentLineId;

    @Column(name = "SHIPMENT_ID")
    private BigDecimal shipmentId;

    @Column(name = "INVOICE_HEADER_ID")
    private BigDecimal invoiceHeaderId;

    @Column(name = "INVOICE_LINE_ID")
    private BigDecimal invoiceLineId;

    @Column(name = "COUNTRY_OF_ORIGIN")
    private String countryOfOrigin;

    @Column(name = "IS_FTA")
    private String isFta;

    @Column(name = "IS_ANTI_DUMPING")
    private String isAntiDumping;

    @Column(name = "IS_WPC_REQUIREMNT")
    private String isWpcRequiremnt;

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

    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;

    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "ITEM_DESCRIPTION")
    private String itemDescription;

    @Column(name = "HSN_CODE")
    private String hsnCode;

}
