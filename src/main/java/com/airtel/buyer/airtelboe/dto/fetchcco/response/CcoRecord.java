package com.airtel.buyer.airtelboe.dto.fetchcco.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CcoRecord {

    @JsonProperty("ouName")
    private String ouName;

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("boeDate")
    private LocalDate boeDate;

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("poNo")
    private String poNo;

    @JsonProperty("invoiceNo")
    private String invoiceNo;

    @JsonProperty("chaName")
    private String chaName;

    @JsonProperty("modeOfShipment")
    private String modeOfShipment;

    @JsonProperty("shipmentArrivalDate")
    private LocalDate shipmentArrivalDate;

    @JsonProperty("partnerCode")
    private BigDecimal partnerCode;

    @JsonProperty("partnerName")
    private String partnerName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("dutyAmount")
    private String dutyAmount;

//    @JsonProperty("ccoDocs")
//    private List<CcoDoc> ccoDocList;

}
