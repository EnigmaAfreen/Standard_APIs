package com.airtel.buyer.airtelboe.dto.scm.stage2.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SubmitStage2Response {

    private BigDecimal shipmentId;
    private String shipmentMode;
    private String countryLoading;
    private String portLoading;
    private String destinationCountry;
    private String destinationPort;
    private BigDecimal grossShipmentWeight;
    private BigDecimal grossShipmentVolume;
    private String expectedArrivalDate;
    private String awbBol;
    private String freightAmtCurrency;
    private BigDecimal freightAmt;
    private String insuranceAmtCurrency;
    private BigDecimal insuranceAmt;
    private BigDecimal efCharges;
    private String shipmentArrivalDate;

    @JsonProperty("fta")
    private List<Fta> fta;

    @JsonProperty("docs")
    private List<ShipmentDoc> docs;

    private String headerErrMsg;
    private String shipmentModeErrMsg;
    private String countryLoadingErrMsg;
    private String portLoadingErrMsg;
    private String destinationCountryErrMsg;
    private String destinationPortErrMsg;
    private String grossShipmentWeightErrMsg;
    private String grossShipmentVolumeErrMsg;
    private String expectedArrivalDateErrMsg;
    private String awbBolErrMsg;
    private String freightAmtCurrencyErrMsg;
    private String freightAmtErrMsg;
    private String insuranceAmtCurrencyErrMsg;
    private String insuranceAmtErrMsg;
    private String efChargesErrMsg;
    private String shipmentArrivalDateErrMsg;

    private String ftaErrMsg;
    private String docsErrMsg;
}
