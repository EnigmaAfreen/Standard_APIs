package com.airtel.buyer.airtelboe.dto.fetchchareassignmentpagedata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChaReassignmentPageDataList {

    @JsonProperty("shipmentId")
    private String shipmentId;

    @JsonProperty("statusRef")
    private String statusRef;

    @JsonProperty("chaAgent")
    private String chaAgent;

    @JsonProperty("leName")
    private String leName;

    @JsonProperty("ouName")
    private String ouName;

    @JsonProperty("portName")
    private String portName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("destinationPort")
    private String destinationPort;

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("lob")
    private String lob;

    @JsonProperty("bucketNo")
    private String bucketNo;

    @JsonProperty("stage")
    private String stage;

    @JsonProperty("assignedChaId")
    private String assignedChaId;

}
