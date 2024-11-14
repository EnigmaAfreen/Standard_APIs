package com.airtel.buyer.airtelboe.dto.fetchprotestedboe.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProtestedBoeRecord {

    @JsonProperty("ouNo")
    private String ouNo;

    @JsonProperty("supplierName")
    private String supplierName;

    @JsonProperty("poNo")
    private String poNo;

    @JsonProperty("invoiceNo")
    private String invoiceNo;

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("boeDate")
    private LocalDate boeDate;

    @JsonProperty("boeProtestedDate")
    private LocalDate boeProtestedDate;

    @JsonProperty("chaName")
    private String chaName;

    @JsonProperty("portCode")
    private String portCode;

    @JsonProperty("reasonForProtest")
    private String reasonForProtest;

    @JsonProperty("daysRemaining")
    private Integer daysRemaining;

    @JsonProperty("actionStatus")
    private String actionStatus;

    @JsonProperty("actionDate")
    private LocalDate actionDate;

}
