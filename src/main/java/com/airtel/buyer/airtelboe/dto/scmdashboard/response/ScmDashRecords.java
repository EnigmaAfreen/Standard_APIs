package com.airtel.buyer.airtelboe.dto.scmdashboard.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ScmDashRecords {

    @JsonProperty("ouName")
    private String ouName;

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal airtelBoeRefNo;

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("boeDate")
    private LocalDate boeDate;

    @JsonProperty("awbBlNo")
    private String awbBlNo;

    @JsonProperty("poNo")
    private String poNo;

    @JsonProperty("invoiceNo")
    private String invoiceNo;

    @JsonProperty("partnerName")
    private String partnerName;

    @JsonProperty("incoTerm")
    private String incoTerm;

    @JsonProperty("modeOfShipment")
    private String modeOfShipment;

    @JsonProperty("shipmentArrivalDate")
    private LocalDate shipmentArrivalDate;

    @JsonProperty("destinationPort")
    private String destinationPort;

    @JsonProperty("chaAgent")
    private String chaAgent;

    @JsonProperty("status")
    private BigDecimal status;

    @JsonProperty("statusDesc")
    private String statusDesc;

    @JsonProperty("paymentDate")
    private LocalDate paymentDate;

    @JsonProperty("bucketNo")
    private BigDecimal bucketNo;

    @JsonProperty("creationDate")
    private LocalDateTime creationDate;

    @JsonProperty("orgId")
    private BigDecimal orgId;

    @JsonProperty("lob")
    private String lob;

    @JsonProperty("assignedChaId")
    private String assignedChaId;

    @JsonProperty("dutyAmount")
    private String dutyAmount;
}
