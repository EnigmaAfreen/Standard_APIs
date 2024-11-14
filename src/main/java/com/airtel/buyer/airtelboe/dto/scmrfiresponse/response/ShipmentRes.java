package com.airtel.buyer.airtelboe.dto.scmrfiresponse.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ShipmentRes {

//    @JsonProperty("airtelBoeRefNo")
//    private BigDecimal shipmentId;

    @JsonProperty("shipmentMode")
    private String shipmentMode;

    @JsonProperty("countryLoading")
    private String countryLoading;

    @JsonProperty("portLoading")
    private String portLoading;

    @JsonProperty("destinationCountry")
    private String destinationCountry;

    @JsonProperty("destinationPort")
    private String destinationPort;

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

//    @JsonProperty("airtelBoeRefNoErrMsg")
//    private String shipmentIdErrMsg;

    @JsonProperty("shipmentModeErrMsg")
    private String shipmentModeErrMsg;

    @JsonProperty("countryLoadingErrMsg")
    private String countryLoadingErrMsg;

    @JsonProperty("portLoadingErrMsg")
    private String portLoadingErrMsg;

    @JsonProperty("destinationCountryErrMsg")
    private String destinationCountryErrMsg;

    @JsonProperty("destinationPortErrMsg")
    private String destinationPortErrMsg;

    @JsonProperty("grossShipmentWeightErrMsg")
    private String grossShipmentWeightErrMsg;

    @JsonProperty("grossShipmentVolumeErrMsg")
    private String grossShipmentVolumeErrMsg;

    @JsonProperty("expectedArrivalDateErrMsg")
    private String expectedArrivalDateErrMsg;

    @JsonProperty("noOfPackagesErrMsg")
    private String noOfPackagesErrMsg;

    @JsonProperty("awbBolErrMsg")
    private String awbBolErrMsg;

    @JsonProperty("freightAmtCurrencyErrMsg")
    private String freightAmtCurrencyErrMsg;

    @JsonProperty("freightAmtErrMsg")
    private String freightAmtErrMsg;

    @JsonProperty("insuranceAmtCurrencyErrMsg")
    private String insuranceAmtCurrencyErrMsg;

    @JsonProperty("insuranceAmtErrMsg")
    private String insuranceAmtErrMsg;

    @JsonProperty("efChargesAmtErrMsg")
    private String efChargesAmtErrMsg;

    @JsonProperty("boeNoErrMsg")
    private String boeNoErrMsg;

    @JsonProperty("boeDateErrMsg")
    private String boeDateErrMsg;

    @JsonProperty("shipmentArrivalDateErrMsg")
    private String shipmentArrivalDateErrMsg;
}
