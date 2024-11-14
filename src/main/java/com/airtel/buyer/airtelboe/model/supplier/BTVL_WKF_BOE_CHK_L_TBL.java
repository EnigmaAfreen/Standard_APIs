package com.airtel.buyer.airtelboe.model.supplier;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class BTVL_WKF_BOE_CHK_L_TBL {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oracle_sequence")
    @SequenceGenerator(name = "oracle_sequence", sequenceName = "BTVL_WKF_BOE_CHK_L_TBL_SEQ", allocationSize = 1)
    @Column(name = "BOE_LINE_ID")
    private BigDecimal boeLineId;

    @Column(name = "EPCG_LICENCE_NO")
    private String epcgLicenceNo;

    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;

    @Column(name = "ITEM_CODE_AND_DESC")
    private String itemCodeAndDesc;

    @Column(name = "ITEM_QTY")
    private BigDecimal itemQty;

    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

    @Column(name = "HSN")
    private String hsn;

    @Column(name = "ASSESSABLE_VALUE")
    private BigDecimal assessableValue;

    @Column(name = "BASIC_CUSTOM_DUTY_PER")
    private BigDecimal basicCustomDutyPer;

    @Column(name = "SWS_PER")
    private BigDecimal swsPer;

    @Column(name = "IGST_PER")
    private BigDecimal igstPer;

    @Column(name = "BASIC_CUSTOM_DUTY")
    private BigDecimal basicCustomDuty;

    @Column(name = "SWS")
    private BigDecimal sws;

    @Column(name = "IGST")
    private BigDecimal igst;

    @Column(name = "COMPENSATION_CESS_ON_IGST")
    private BigDecimal compensationCessOnIgst;

    @Column(name = "DUTY_OTHER_THAN_GST")
    private BigDecimal dutyOtherThanGst;

    @Column(name = "TOTAL_DUTY")
    private BigDecimal totalDuty;

    @Column(name = "ANTI_DUMPING_AMT")
    private BigDecimal antiDumpingAmt;

    @Column(name = "EXEMPTION_NOTN")
    private String exemptionNotn;

    @Column(name = "WPC_LICENSE_NO")
    private String wpcLicenseNo;

    @Column(name = "ANTI_DUMPING_PER")
    private BigDecimal antiDumpingPer;

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

    @Column(name = "PURGE_FLAG")
    private Integer purgeFlag;

    @Column(name = "BOE_INV_HEADER_ID")
    private BigDecimal boeInvHeaderId;

}
