package com.airtel.buyer.airtelboe.dto.fetchchecklist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ShipmentInfoRes {

    @JsonProperty("countryOrigin")
    private String countryOrigin;

    @JsonProperty("countryOriginCode")
    private String countryOriginCode;

    @JsonProperty("countryConsignment")
    private String countryConsignment;

    @JsonProperty("countryConsignmentCode")
    private String countryConsignmentCode;

    @JsonProperty("portLoading")
    private String portLoading;

    @JsonProperty("grossShipmentWeigth")
    private BigDecimal grossShipmentWeigth;

    @JsonProperty("grossShipmentVolume")
    private String grossShipmentVolume;

    @JsonProperty("totalNoOfPackages")
    private BigDecimal totalNoOfPackages;

    @JsonProperty("vesselAgentName")
    private String vesselAgentName;

    @JsonProperty("rotationNo")
    private String rotationNo;

    @JsonProperty("rotationDate")
    private LocalDate rotationDate;

    @JsonProperty("awbBol")
    private String awbBol;

}
