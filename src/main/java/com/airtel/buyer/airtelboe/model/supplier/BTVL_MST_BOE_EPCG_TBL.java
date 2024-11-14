package com.airtel.buyer.airtelboe.model.supplier;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class BTVL_MST_BOE_EPCG_TBL {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oracle_sequence")
    @SequenceGenerator(name = "oracle_sequence", sequenceName = "BTVL_MST_BOE_EPCG_TBL_SEQ", allocationSize = 1)
    @Column(name = "EPCG_ID")
    private BigDecimal epcgId;

    @Column(name = "LICENCE_NO")
    private String licenceNo;

    @Column(name = "LE_NAME")
    private String leName;

    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "ITEM_DECRIPTION")
    private String itemDecription;

    @Column(name = "PORT_CODE")
    private String portCode;

    @Column(name = "TOT_ITEM_QTY_EPCG_LC")
    private BigDecimal totItemQtyEpcgLc;

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

    @Column(name = "LICENCE_DATE")
    private LocalDate licenceDate;

    @Column(name = "PORT_ISSUE")
    private String portIssue;

    @Column(name = "DUTY_SAVED")
    private String dutySaved;

    @Column(name = "REG_NUM")
    private BigDecimal regNum;

    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;

    @Column(name = "INVOICE_DATE")
    private LocalDate invoiceDate;

    @Column(name = "LOB")
    private String lob;

    @Column(name = "BOE_NO")
    private String boeNo;

    @Column(name = "BOE_DATE")
    private LocalDate boeDate;
}
