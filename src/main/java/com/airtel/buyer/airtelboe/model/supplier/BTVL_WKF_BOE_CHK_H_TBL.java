package com.airtel.buyer.airtelboe.model.supplier;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class BTVL_WKF_BOE_CHK_H_TBL {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oracle_sequence")
    @SequenceGenerator(name = "oracle_sequence", sequenceName = "BTVL_WKF_BOE_CHK_H_TBL_SEQ", allocationSize = 1)
    @Column(name = "BOE_HEADER_ID")
    private BigDecimal boeHeaderId;

    @Column(name = "SHIPMENT_ID")
    private BigDecimal shipmentId;

    @Column(name = "CI_IEC_CODE")
    private String ciIecCode;

    @Column(name = "CI_BRANCH_CODE")
    private BigDecimal ciBranchCode;

    @Column(name = "CI_GSTN_NO")
    private String ciGstnNo;

    @Column(name = "CI_PAN_NO")
    private String ciPanNo;

    @Column(name = "CI_AD_CODE")
    private String ciAdCode;

    @Column(name = "CI_LEGAL_ENTITY")
    private String ciLegalEntity;

    @Column(name = "CI_IMPORTER_NAME")
    private String ciImporterName;

    @Column(name = "CI_SHIP_TO")
    private String ciShipTo;

    @Column(name = "SI_SUPP_EXP_NAME")
    private String siSuppExpName;

    @Column(name = "SI_SUPP_EXP_COUNTRY")
    private String siSuppExpCountry;

    @Column(name = "SHI_COUNTRY_ORIGIN")
    private String shiCountryOrigin;

    @Column(name = "SHI_COUNTRY_ORIGIN_CODE")
    private String shiCountryOriginCode;

    @Column(name = "SHI_COUNTRY_CONSIGNMENT")
    private String shiCountryConsignment;

    @Column(name = "SHI_COUNTRY_CONSIGNMENT_CODE")
    private String shiCountryConsignmentCode;

    @Column(name = "SHI_PORT_LOADING")
    private String shiPortLoading;

    @Column(name = "SHI_SHIPMENT_WEIGHT")
    private BigDecimal shiShipmentWeight;

    @Column(name = "SHI_SHIPMENT_WEIGHT_UOM")
    private String shiShipmentWeightUom;

    @Column(name = "SHI_TOTAL_NO_OF_PACKAGES")
    private BigDecimal shiTotalNoOfPackages;

    @Column(name = "SHI_VESSEL_AGENT_NAME")
    private String shiVesselAgentName;

    @Column(name = "CI_CHA_CODE")
    private String ciChaCode;

    @Column(name = "SHI_ROTATION_NO")
    private String shiRotationNo;

    @Column(name = "SHI_ROTATION_DATE")
    private LocalDate shiRotationDate;

    @Column(name = "CI_PD_BOND_NO")
    private BigDecimal ciPdBondNo;

    @Column(name = "SHI_AWB_BOL")
    private String shiAwbBol;

    @Column(name = "CI_BOE_TYPE")
    private String ciBoeType;

    @Column(name = "CI_STAMP_COL_FREE_NO_DT")
    private String ciStampColFreeNoDt;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "CREATEDBY")
    private String createdby;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @Column(name = "MODIFIEDBY")
    private String modifiedby;

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

    @Column(name = "BOE_INV_ID_ERP")
    private BigDecimal boeInvIdErp;

    @Column(name = "BOE_ID_ERP")
    private BigDecimal boeIdErp;

    @Column(name = "BOE_INVOICE_NO_ERP")
    private String boeInvoiceNoErp;

    @Column(name = "CI_IMPORTER_ADDRESS")
    private String ciImporterAddress;

    @Column(name = "SI_SUPP_EXP_ADDRESS")
    private String siSuppExpAddress;

    @Column(name = "DEMURRAGE_AMT")
    private BigDecimal demurrageAmt;

    @Column(name = "INTEREST_PENALTY_AMT")
    private BigDecimal interestPenaltyAmt;

}
