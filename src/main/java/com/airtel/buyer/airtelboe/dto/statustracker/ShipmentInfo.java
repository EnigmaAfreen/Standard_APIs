package com.airtel.buyer.airtelboe.dto.statustracker;


import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIP_POC_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_TRAIL_TBL;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
public class ShipmentInfo {
    private BigDecimal ShipmentId;
    private String PoNo;
    private String InvoiceNumbers;
    private String IncoTerm;
    private String ShipmentMode;
    private String CountryLoading;
    private String PortLoading;
    private String DestinationCountry;
    private String DestinationPort;
    private BigDecimal GrossShipmentWeight;
    private BigDecimal GrossShipmentVolume;
    private LocalDate ExpectedArrivalDate;
    private BigDecimal NoOfPackages;
    private String AwbBol;
    private String FreightAmtCurrency;
    private BigDecimal FreightAmt;
    private String InsuranceAmtCurrency;
    private BigDecimal InsuranceAmt;
    private String BoeNo;
    private LocalDate BoeDate;
    private LocalDate ShipmentArrivalDate;
    private BigDecimal StageBucketNo;
    private BigDecimal Status;
    private String RfiBy;
    private BigDecimal IsProtestedDataErr;
    private String ProtestDataErrReason;
    private String ProtestDescription;
    private Integer PurgeFlag;
    private String Flag1;
    private String Flag2;
    private String Flag3;
    private String Flag4;
    private String Flag5;
    private String Attribute1;
    private String Attribute2;
    private String Attribute3;
    private String Attribute4;
    private String Attribute5;
    private String Attribute6;
    private String Attribute7;
    private String Attribute8;
    private String Attribute9;
    private String Attribute10;
    private String Attribute11;
    private String Attribute12;
    private String Attribute13;
    private String Attribute14;
    private String Attribute15;
    private LocalDateTime CreationDate;
    private String Createdby;
    private LocalDateTime ModifiedDate;
    private String Modifiedby;
    private LocalDate PaymentDate;
    private String AssignedChaId;
    private LocalDate ChaAssigmentDate;
    private String PartnerVendorCode;
    private BigDecimal PartnerVendorId;
    private BigDecimal Stage;
    private BigDecimal BucketNo;
    private String OuName;
    private String LeName;
    private String VendorName;
    private BigDecimal PoHeaderId;
    private String statusRef; //to store Actual value of Status
    private String LOB;
    private String GrossShipmentVolumeUom;
    private String GrossShipmentWeightUom;
    private String InvoiceNo;
    private String MrrNo;
    private String UtrNo;
    private BigDecimal orgId;
    private Date MrrDate;
    private String incoTermCategory;
    private LocalDate OutOfChargeDate;
    private LocalDate ShipmentCourierDate;
    private String ShipmentCourierRefNo;
    private String ChaAgent;
    private BigDecimal EfChargesAmt;
    private String assignedCcoId;

    private List<BigDecimal> invoiceHeaderIdArr;


    // List to hold all the lines obeject of shipment.
    private List<ShipmentLines> shipmentLineInfoList;
    //    //Field to hold Point of contact Data Object.
    private BTVL_WKF_BOE_SHIP_POC_TBL bTVL_WKF_BOE_SHIP_POC_TBL;
//    // Field to hold the list of docs object.
//    private List<BTVL_WKF_BOE_SHIP_DOC_TBL> shipmentDocList;
//    // Field to hoold the trail info obejct.
//    private BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL;
//
//    private List<BTVL_WKF_BOE_TRAIL_TBL> trailList;
//    private List<BTVL_WKF_BOE_SHIP_LINE_TBL> allShipmentLines;


//    @JsonProperty("shipmentLines")
//    private List<BTVL_WKF_BOE_SHIP_LINE_TBL> shipmentLineInfoList;

//    @JsonProperty("pointOfContactInfo")
//    private BTVL_WKF_BOE_SHIP_POC_TBL pointOfContactInfo;

    @JsonProperty("shipmentDocs")
    private List<ShipmentDoc> shipmentDocList;

    @JsonProperty("boeTrail")
    private List<BTVL_WKF_BOE_TRAIL_TBL> shipmentTrailList;


    // Error fields
    private String ShipmentIdErr;
    private String PoNoErr;
    private String InvoiceNoErr;
    private String IncoTermErr;
    private String ShipmentModeErr;
    private String CountryLoadingErr;
    private String PortLoadingErr;
    private String DestinationCountryErr;
    private String DestinationPortErr;
    private String GrossShipmentWeightErr;
    private String GrossShipmentVolumeErr;
    private String ExpectedArrivalDateErr;
    private String NoOfPackagesErr;
    private String AwbBolErr;
    private String FreightAmtCurrencyErr;
    private String FreightAmtErr;
    private String InsuranceAmtCurrencyErr;
    private String InsuranceAmtErr;
    private String BoeNoErr;
    private String BoeDateErr;
    private String ShipmentArrivalDateErr;
    private String StageBucketNoErr;
    private String StatusErr;
    private String RfiByErr;
    private String IsProtestedDataErrErr;
    private String ProtestDataErrReasonErr;
    private String ProtestDescriptionErr;
    private String PurgeFlagErr;
    private String Flag1Err;
    private String Flag2Err;
    private String Flag3Err;
    private String Flag4Err;
    private String Flag5Err;
    private String Attribute1Err;
    private String Attribute2Err;
    private String Attribute3Err;
    private String Attribute4Err;
    private String Attribute5Err;
    private String Attribute6Err;
    private String Attribute7Err;
    private String Attribute8Err;
    private String Attribute9Err;
    private String Attribute10Err;
    private String Attribute11Err;
    private String Attribute12Err;
    private String Attribute13Err;
    private String Attribute14Err;
    private String Attribute15Err;
    private String CreationDateErr;
    private String CreatedbyErr;
    private String ModifiedDateErr;
    private String ModifiedbyErr;
    private String PaymentDateErr;
    private String AssignedChaErr;
    private String AssignedChaEmailErr;
    private String AssignedChaNameErr;
    private Date ChaAssigmentDateErr;
    private String PartnerVendorCodeErr;
    private Number PartnerVendorIdErr;

    private String OutOfChargeDateErr;
    private String ShipmentCourierDateErr;
    private String ShipmentCourierRefNoErr;
    private String EfChargesAmtErr;


    private Boolean PoNoFlag;
    private Boolean InvoiceHeaderIdArrFlag;
    private Boolean IncoTermFlag;
    private Boolean ShipmentModeFlag;
    private Boolean CountryLoadingFlag;
    private Boolean PortLoadingFlag;
    private Boolean DestinationCountryFlag;
    private Boolean DestinationPortFlag;
    private Boolean GrossShipmentWeightFlag;
    private Boolean GrossShipmentVolumeFlag;
    private Boolean ExpectedArrivalDateFlag;
    private Boolean NoOfPackagesFlag;
    private Boolean AwbBolFlag;
    private Boolean FreightAmtCurrencyFlag;
    private Boolean FreightAmtFlag;
    private Boolean InsuranceAmtCurrencyFlag;
    private Boolean InsuranceAmtFlag;
    private Boolean BoeNoFlag;
    private Boolean BoeDateFlag;
    private Boolean ShipmentArrivalDateFlag;
    private Boolean FtaSectionFlag;
    private Boolean POCSectionFlag;
    private Boolean EfChargesAmtFlag;

}
