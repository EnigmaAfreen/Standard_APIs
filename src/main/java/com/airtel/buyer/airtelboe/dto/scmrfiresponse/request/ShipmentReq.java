package com.airtel.buyer.airtelboe.dto.scmrfiresponse.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ShipmentReq {

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

}
