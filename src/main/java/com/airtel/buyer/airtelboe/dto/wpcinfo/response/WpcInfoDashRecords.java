package com.airtel.buyer.airtelboe.dto.wpcinfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WpcInfoDashRecords {

    @JsonProperty("ouName")
    private String ouName;

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("boeDate")
    private LocalDate boeDate;

    @JsonProperty("awbBol")
    private String awbBol;//

    @JsonProperty("poNo")
    private String poNo;

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal airtelBoeRefNo;//shipmentId

    @JsonProperty("invoiceNumbers")
    private String invoiceNumbers;//

    @JsonProperty("vendorName")
    private String vendorName;//

    @JsonProperty("incoTerm")
    private String incoTerm;//

    @JsonProperty("shipmentMode")
    private String shipmentMode;//

    @JsonProperty("shipmentArrivalDate")
    private LocalDate shipmentArrivalDate;

    @JsonProperty("destinationPort")
    private String destinationPort;

    @JsonProperty("assignedChaId")
    private String assignedChaId;

    @JsonProperty("status")
    private BigDecimal status;

    @JsonProperty("lob")
    private String lob;

    @JsonProperty("orgId")
    private BigDecimal orgId;

    @JsonProperty("creationDate")
    private LocalDateTime creationDate;

    @JsonProperty("StatusDesc")
    private String StatusDesc;

    @JsonProperty("chaAgent")
    private String chaAgent;

}
