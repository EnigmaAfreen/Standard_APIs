package com.airtel.buyer.airtelboe.model.supplier;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class BTVL_WKF_INVOICE_HEADER_TBL {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oracle_sequence")
    @SequenceGenerator(name = "oracle_sequence", sequenceName = "BTVL_WKF_INV_HEADER_ID_SEQ", allocationSize = 1)
    @Column(name = "INVOICE_HEADER_ID")
    private BigDecimal invoiceHeaderId;

    @Column(name = "PARTNER_VENDOR_ID")
    private BigDecimal partnerVendorId;

    @Column(name = "PURCHASE_ORDER")
    private String purchaseOrder;

    @Column(name = "PURCHASE_ORDER_DATE")
    private Timestamp purchaseOrderDate;

    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;

    @Column(name = "INVOICE_DATE")
    private Timestamp invoiceDate;

    @Column(name = "INVOICE_STATUS")
    private BigDecimal invoiceStatus;

    @Column(name = "DIGITALLY_SIGNED")
    private String digitallySigned;

    @Column(name = "OD_NUMBER")
    private String odNumber;

    @Column(name = "OD_TYPE")
    private BigDecimal odType;

    @Column(name = "OPERATING_UNIT")
    private String operatingUnit;

    @Column(name = "INVOICE_TYPE")
    private String invoiceType;

    @Column(name = "OD_LAST_UPDATE_DATE")
    private LocalDate odLastUpdateDate;

    @Column(name = "BUYER")
    private String buyer;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "AMOUNT_PAID")
    private BigDecimal amountPaid;

    @Column(name = "DUE_DATE")
    private LocalDate dueDate;

    @Column(name = "INVOICE_DESC")
    private String invoiceDesc;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "CUSTOMER_ADDRESS")
    private String customerAddress;

    @Column(name = "CUSTOMER_GST")
    private String customerGst;

    @Column(name = "CUST_CONT_PH_EM")
    private String custContPhEm;

    @Column(name = "CUSTOMER_BILL_NAME")
    private String customerBillName;

    @Column(name = "CUSTOMER_BILL_ADDRESS")
    private String customerBillAddress;

    @Column(name = "CUST_BILL_PIN_CODE")
    private String custBillPinCode;

    @Column(name = "CUST_BILL_CITY")
    private String custBillCity;

    @Column(name = "CUST_BILL_STATE")
    private String custBillState;

    @Column(name = "CUST_BILL_GST")
    private String custBillGst;

    @Column(name = "CUST_BILL_CONT_PH_EM")
    private String custBillContPhEm;

    @Column(name = "CUSTOMER_SHIP_NAME")
    private String customerShipName;

    @Column(name = "CUSTOMER_SHIP_ADDRESS")
    private String customerShipAddress;

    @Column(name = "CUST_SHIP_PIN_CODE")
    private String custShipPinCode;

    @Column(name = "CUST_SHIP_CITY")
    private String custShipCity;

    @Column(name = "CUST_SHIP_STATE")
    private String custShipState;

    @Column(name = "CUST_SHIP_GST")
    private String custShipGst;

    @Column(name = "CUST_SHIP_CONT_PH_EM")
    private String custShipContPhEm;

    @Column(name = "SUPPLIER_NAME")
    private String supplierName;

    @Column(name = "SUPPLIER_ADDRESS")
    private String supplierAddress;

    @Column(name = "SUPPLIER_REG_OFF_ADD")
    private String supplierRegOffAdd;

    @Column(name = "SUPPLIER_PAN")
    private String supplierPan;

    @Column(name = "SUPPLIER_GST_ISD")
    private String supplierGstIsd;

    @Column(name = "SUPPLIER_CONTACT")
    private String supplierContact;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "BANK_ADDRESS")
    private String bankAddress;

    @Column(name = "BANK_IFSC_CODE")
    private String bankIfscCode;

    @Column(name = "BANK_ACCOUNT_NO")
    private String bankAccountNo;

    @Column(name = "TERMS_CONDITIONS")
    private String termsConditions;

    @Column(name = "BILLING_PERIOD")
    private String billingPeriod;

    @Column(name = "PO_DESTINATION_TYPE")
    private String poDestinationType;

    @Column(name = "INV_MATERIAL_SERVICE")
    private BigDecimal invMaterialService;

    @Column(name = "INV_GENERATION_TYPE")
    private Integer invGenerationType;

    @Column(name = "SUPPLIER_SITE_NAME")
    private String supplierSiteName;

    @Column(name = "PAYMENT_TERMS")
    private String paymentTerms;

    @Column(name = "PO_SHIPMENT_DATE")
    private Timestamp poShipmentDate;

    @Column(name = "PO_EXPECTED_DATE")
    private Timestamp poExpectedDate;

    @Column(name = "DELIVERY_CHALLAN_NO")
    private String deliveryChallanNo;

    @Column(name = "DELIVERY_CHALLAN_DATE")
    private Timestamp deliveryChallanDate;

    @Column(name = "MODE_OF_DISPATCH")
    private String modeOfDispatch;

    @Column(name = "EXPORT_IMPORT_CODE")
    private String exportImportCode;

    @Column(name = "VEHICLE_NO")
    private String vehicleNo;

    @Column(name = "TRANSPORTER_DETAILS")
    private String transporterDetails;

    @Column(name = "GR_LR_NO")
    private String grLrNo;

    @Column(name = "EWAY_ROAD_PERMIT_NO")
    private String ewayRoadPermitNo;

    @Column(name = "DELIVERY_ADD_CONTACT")
    private String deliveryAddContact;

    @Column(name = "CHEQUE_DRAFT_NAME")
    private String chequeDraftName;

    @Column(name = "INVOICE_CREATION_DATE_EBS")
    private LocalDate invoiceCreationDateEbs;

    @Column(name = "INVOICE_CREATION_TYPE")
    private BigDecimal invoiceCreationType;

    @Column(name = "INVOICE_ID")
    private BigDecimal invoiceId;

    @Column(name = "INVOICE_SUB_STATUS")
    private String invoiceSubStatus;

    @Column(name = "APPROVAL_BYPASSED_DATE")
    private LocalDate approvalBypassedDate;

    @Column(name = "INVOICE_TYPE_EBS")
    private String invoiceTypeEbs;

    @Column(name = "PURGE_FLAG")
    private BigDecimal purgeFlag;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "CREATEDBY")
    private String createdby;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @Column(name = "MODIFIEDBY")
    private String modifiedby;

    @Column(name = "PO_H_PLACE_HOLDER1")
    private String poHPlaceHolder1;

    @Column(name = "PO_H_PLACE_HOLDER2")
    private String poHPlaceHolder2;

    @Column(name = "PO_H_PLACE_HOLDER3")
    private String poHPlaceHolder3;

    @Column(name = "PO_H_PLACE_HOLDER4")
    private String poHPlaceHolder4;

    @Column(name = "TAX_INVOICE_DETAILS")
    private String taxInvoiceDetails;

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

    @Column(name = "CUSTOMER_PINCODE")
    private String customerPincode;

    @Column(name = "CUSTOMER_STATE")
    private String customerState;

    @Column(name = "ASN_STATUS")
    private String asnStatus;

    @Column(name = "PO_HEADER_ID")
    private BigDecimal poHeaderId;

    @Column(name = "SUPPLIER_PINCODE")
    private String supplierPincode;

    @Column(name = "SUPPLIER_STATE")
    private String supplierState;

    @Column(name = "CUSTOMER_CITY")
    private String customerCity;

    @Column(name = "SUPPLIER_CITY")
    private String supplierCity;

    @Column(name = "NATURE_OF_ACTIVITY")
    private String natureOfActivity;

    @Column(name = "INCO_TERMS")
    private String incoTerms;

    @Column(name = "PROPOSED_INVOICE_DATE")
    private LocalDate proposedInvoiceDate;

    @Column(name = "NO_OF_PACKETS")
    private BigDecimal noOfPackets;

    @Column(name = "TEP_SERIAL_NO")
    private BigDecimal tepSerialNo;

    @Column(name = "BILLING_PERIOD_FROM")
    private String billingPeriodFrom;

    @Column(name = "BILLING_PERIOD_TO")
    private String billingPeriodTo;

    @Column(name = "FDOA_APPROVAL_PENDING_LEVEL")
    private BigDecimal fdoaApprovalPendingLevel;

    @Column(name = "FDOA_APPROVAL_PENDING_ON")
    private String fdoaApprovalPendingOn;

    @Column(name = "FDOA_APPROVED_BY")
    private String fdoaApprovedBy;

    @Column(name = "OTM_INVOICE_NO")
    private String otmInvoiceNo;

    @Column(name = "SHIPMENT_ID")
    private String shipmentId;

    @Column(name = "PACKING_LIST_NO")
    private String packingListNo;

    @Column(name = "SHIP_WEIGHT_GROSS")
    private BigDecimal shipWeightGross;

    @Column(name = "SHIP_WEIGHT_NET")
    private BigDecimal shipWeightNet;

    @Column(name = "SHIP_VOLUME")
    private BigDecimal shipVolume;

    @Column(name = "EXCHANGE_RATE")
    private BigDecimal exchangeRate;

    @Column(name = "CONTRACT_ID")
    private String contractId;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "FTWZ_LOA_NO")
    private String ftwzLoaNo;

    @Column(name = "CIN_NO")
    private String cinNo;

    @Column(name = "COUNTRY_ORIGIN")
    private String countryOrigin;

    @Column(name = "BILLING_OU")
    private String billingOu;

    @Column(name = "INVOICE_TAXABLE_AMT")
    private BigDecimal invoiceTaxableAmt;

    @Column(name = "INVOICE_TAX_AMT")
    private BigDecimal invoiceTaxAmt;

    @Column(name = "INVOICE_DISCOUNT_AMT")
    private BigDecimal invoiceDiscountAmt;

    @Column(name = "INVOICE_TYPE_FLAG")
    private String invoiceTypeFlag;

    @Column(name = "PHD_STATUS")
    private String phdStatus;

    @Column(name = "PHD_APPROVAL_DATE")
    private LocalDateTime phdApprovalDate;

    @Column(name = "CAPPING_ID")
    private BigDecimal cappingId;

    @Column(name = "INVOICE_SUBMISSION_DATE")
    private LocalDateTime invoiceSubmissionDate;

    @Column(name = "INVOICE_RECEIVED_LOC")
    private String invoiceReceivedLoc;

    @Column(name = "ORG_ID")
    private BigDecimal orgId;

    @Column(name = "VENDOR_CODE")
    private String vendorCode;

    @Column(name = "FDOA_NOTIFICATION_DATE")
    private LocalDate fdoaNotificationDate;

    @Column(name = "FDOA_APRROVER_ROLE")
    private String fdoaAprroverRole;

    @Column(name = "WITH_IN_TAT")
    private String withInTat;

    @Column(name = "RESCUER_CASE_ID")
    private String rescuerCaseId;

    @Column(name = "TICKET_STATUS")
    private String ticketStatus;

    @Column(name = "DESTINATION")
    private String destination;

    @Column(name = "IS_RCM")
    private String isRcm;

    @Column(name = "IS_BHARTI_SEZ")
    private String isBhartiSez;

    @Column(name = "IS_COMPOSITE")
    private String isComposite;

    @Column(name = "IRN_COMPLIANCE_STATUS")
    private String irnComplianceStatus;

    @Column(name = "IRN_NO")
    private String irnNo;

    @Column(name = "IS_IRN_FLOW")
    private String isIrnFlow;

    @Column(name = "QR_SCANNED_BASE64")
    private String qrScannedBase64;

    @Column(name = "LOCATER_CODE")
    private String locaterCode;

    @Column(name = "IRN_QR_CODE_PRESENT")
    private String irnQrCodePresent;

    @Column(name = "TCS")
    private BigDecimal tcs;

    @Column(name = "IRN_EFFECTIVE_DATE")
    private LocalDate irnEffectiveDate;

    @Column(name = "ALT_SUPPLIER_NAME")
    private String altSupplierName;

}
