package com.airtel.buyer.airtelboe.dto.fetchshipment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ShipmentInfo {

    @JsonProperty("poNo")
    private String poNo;

    @JsonProperty("destinationPort")
    private String destinationPort;

    @JsonProperty("incoTerm")
    private String incoTerm;

    @JsonProperty("shipmentMode")
    private String shipmentMode;

    @JsonProperty("destinationCountry")
    private String destinationCountry;

    @JsonProperty("invoiceNumbers")
    private String invoiceNumbers;

    @JsonProperty("countryLoading")
    private String countryLoading;

    @JsonProperty("portLoading")
    private String portLoading;

    @JsonProperty("grossShipmentWeight")
    private BigDecimal grossShipmentWeight;

    @JsonProperty("grossShipmentVolume")
    private BigDecimal grossShipmentVolume;

    @JsonProperty("expectedArrivalDate")
    private LocalDate expectedArrivalDate;

    @JsonProperty("noOfPackages")
    private BigDecimal noOfPackages;

    @JsonProperty("awbBol")
    private String awbBol;

    @JsonProperty("freightAmtCurrency")
    private String freightAmtCurrency;

    @JsonProperty("freightAmt")
    private BigDecimal freightAmt;

    @JsonProperty("insuranceAmtCurrency")
    private String insuranceAmtCurrency;

    @JsonProperty("insuranceAmt")
    private BigDecimal insuranceAmt;

    @JsonProperty("efChargesAmt")
    private BigDecimal efChargesAmt;

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("boeDate")
    private LocalDate boeDate;

    @JsonProperty("shipmentArrivalDate")
    private LocalDate shipmentArrivalDate;

    @JsonProperty("stage")
    private BigDecimal stage;

    @JsonProperty("status")
    private BigDecimal status;

    @JsonProperty("bucketNo")
    private BigDecimal bucketNo;

    @JsonProperty("portType")
    private String portType;

    @JsonProperty("clearanceType")
    private String clearanceType;

    @JsonProperty("isProtestedDataErr")
    private BigDecimal isProtestedDataErr;

    @JsonProperty("protestDataErrReason")
    private String protestDataErrReason;

    @JsonProperty("outOfChargeDate")
    private LocalDate outOfChargeDate;

    @JsonProperty("shipmentCourierDate")
    private LocalDate shipmentCourierDate;

    @JsonProperty("shipmentCourierRefNo")
    private String shipmentCourierRefNo;

    @JsonProperty("protestDescription")
    private String protestDescription;
}
