package com.airtel.buyer.airtelboe.model.supplier;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class BTVL_MST_BOE_WPC_TBL {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oracle_sequence")
    @SequenceGenerator(name = "oracle_sequence", sequenceName = "BTVL_MST_BOE_WPC_TBL_SEQ", allocationSize = 1)
    @Column(name = "WPC_ID")
    private BigDecimal wpcId;

    @Column(name = "PO_NO")
    private String poNo;

    @Column(name = "OU_NO")
    private String ouNo;

    @Column(name = "PORT_CODE")
    private String portCode;

    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "LICENCE_NO")
    private String licenceNo;

    @Column(name = "WPC_QTY")
    private BigDecimal wpcQty;

    @Column(name = "BALANCE_QTY")
    private BigDecimal balanceQty;

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

    @Column(name = "CONSUME_QUANTITY")
    private BigDecimal consumeQuantity;

    @Column(name = "INVOICE_NO")
    private String invoiceNo;

    @Column(name = "INVOICE_DATE")
    private LocalDate invoiceDate;

    @Column(name = "LICENCE_DATE")
    private LocalDate licenceDate;

    @Column(name = "LE_NAME")
    private String leName;

    @Column(name = "LOB")
    private String lob;

    @Column(name = "BOE_NO")
    private String boeNo;

    @Column(name = "BOE_DATE")
    private LocalDate boeDate;

    @Column(name = "ITEM_DESCRIPTION")
    private String itemDescription;


}
