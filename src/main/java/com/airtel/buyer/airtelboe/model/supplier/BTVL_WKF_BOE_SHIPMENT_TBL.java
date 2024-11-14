package com.airtel.buyer.airtelboe.model.supplier;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class BTVL_WKF_BOE_SHIPMENT_TBL {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oracle_sequence")
    @SequenceGenerator(name = "oracle_sequence", sequenceName = "BTVL_WKF_BOE_SHIPMENT_TBL_SEQ", allocationSize = 1)
    @Column(name = "SHIPMENT_ID")
    private BigDecimal shipmentId;

    @Column(name = "PO_NO")
    private String poNo;

    @Column(name = "INCO_TERM")
    private String incoTerm;

    @Column(name = "SHIPMENT_MODE")
    private String shipmentMode;

    @Column(name = "COUNTRY_LOADING")
    private String countryLoading;

    @Column(name = "PORT_LOADING")
    private String portLoading;

    @Column(name = "DESTINATION_COUNTRY")
    private String destinationCountry;

    @Column(name = "DESTINATION_PORT")
    private String destinationPort;

    @Column(name = "GROSS_SHIPMENT_WEIGHT")
    private BigDecimal grossShipmentWeight;

    @Column(name = "GROSS_SHIPMENT_VOLUME")
    private BigDecimal grossShipmentVolume;

    @Column(name = "EXPECTED_ARRIVAL_DATE")
    private LocalDate expectedArrivalDate;

    @Column(name = "NO_OF_PACKAGES")
    private BigDecimal noOfPackages;

    @Column(name = "AWB_BOL")
    private String awbBol;

    @Column(name = "FREIGHT_AMT_CURRENCY")
    private String freightAmtCurrency;

    @Column(name = "FREIGHT_AMT")
    private BigDecimal freightAmt;

    @Column(name = "INSURANCE_AMT_CURRENCY")
    private String insuranceAmtCurrency;

    @Column(name = "INSURANCE_AMT")
    private BigDecimal insuranceAmt;

    @Column(name = "BOE_NO")
    private String boeNo;

    @Column(name = "BOE_DATE")
    private LocalDate boeDate;

    @Column(name = "SHIPMENT_ARRIVAL_DATE")
    private LocalDate shipmentArrivalDate;

    @Column(name = "STAGE")
    private BigDecimal stage;

    @Column(name = "STATUS")
    private BigDecimal status;

    @Column(name = "IS_PROTESTED_DATA_ERR")
    private BigDecimal isProtestedDataErr;

    @Column(name = "PROTEST_DATA_ERR_REASON")
    private String protestDataErrReason;

    @Column(name = "PROTEST_DESCRIPTION")
    private String protestDescription;

    @Column(name = "PURGE_FLAG")
    private Integer purgeFlag;

    @Column(name = "FLAG1")
    private String flag1;

    @Column(name = "FLAG2")
    private String flag2;

    @Column(name = "FLAG3")
    private String flag3;

    @Column(name = "FLAG4")
    private String flag4;

    @Column(name = "FLAG5")
    private String flag5;

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

    @Column(name = "PAYMENT_DATE")
    private LocalDate paymentDate;

    @Column(name = "ASSIGNED_CHA_ID")
    private String assignedChaId;

    @Column(name = "CHA_ASSIGMENT_DATE")
    private LocalDate chaAssigmentDate;

    @Column(name = "UTR_NO")
    private String utrNo;

    @Column(name = "PARTNER_VENDOR_ID")
    private BigDecimal partnerVendorId;

    @Column(name = "PARTNER_VENDOR_CODE")
    private String partnerVendorCode;

    @Column(name = "BUCKET_NO")
    private BigDecimal bucketNo;

    @Column(name = "PO_HEADER_ID")
    private BigDecimal poHeaderId;

    @Column(name = "GROSS_SHIPMENT_WEIGHT_UOM")
    private String grossShipmentWeightUom;

    @Column(name = "GROSS_SHIPMENT_VOLUME_UOM")
    private String grossShipmentVolumeUom;

    @Column(name = "MRR_NO")
    private String mrrNo;

    @Column(name = "INVOICE_NUMBERS")
    private String invoiceNumbers;

    @Column(name = "OU_NAME")
    private String ouName;

    @Column(name = "VENDOR_NAME")
    private String vendorName;

    @Column(name = "LE_NAME")
    private String leName;

    @Column(name = "LOB")
    private String lob;

    @Column(name = "MRR_DATE")
    private LocalDate mrrDate;

    @Column(name = "ORG_ID")
    private BigDecimal orgId;

    @Column(name = "INCO_TERM_CATEGORY")
    private String incoTermCategory;

    @Column(name = "OUT_OF_CHARGE_DATE")
    private LocalDate outOfChargeDate;

    @Column(name = "SHIPMENT_COURIER_DATE")
    private LocalDate shipmentCourierDate;

    @Column(name = "SHIPMENT_COURIER_REF_NO")
    private String shipmentCourierRefNo;

    @Column(name = "EMAIL_STATUS")
    private String emailStatus;

    @Column(name = "EMAIL_COUNT")
    private BigDecimal emailCount;

    @Column(name = "EF_CHARGES_AMT")
    private BigDecimal efChargesAmt;

    @Column(name = "ASSIGNED_CCO_ID")
    private String assignedCcoId;

}
