package com.airtel.buyer.airtelboe.dto.scm.stage2.request;

import com.airtel.buyer.airtelboe.dto.fetchshipment.response.ShipmentDoc;
import com.airtel.buyer.airtelboe.dto.fetchshipment.response.ShipmentLineInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class SubmitStage2Request {

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
    private List<ShipmentLineInfo> fta;

    @JsonProperty("docs")
    private List<ShipmentDoc> docs;
}
