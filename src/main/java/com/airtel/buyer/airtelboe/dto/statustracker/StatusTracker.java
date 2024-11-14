package com.airtel.buyer.airtelboe.dto.statustracker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatusTracker {

    @JsonProperty("TrailId")
    private BigDecimal TrailId;

    @JsonProperty("ShipmentId")
    private BigDecimal ShipmentId;
    @JsonProperty("ActionBy")
    private String ActionBy;
    @JsonProperty("RefDescription")
    private String RefDescription;
    @JsonProperty("Status")
    private BigDecimal Status;
    @JsonProperty("ActionDate")
    private String ActionDate;
    @JsonProperty("Action")
    private String Action;
    @JsonProperty("Stage")
    private BigDecimal Stage;
    @JsonProperty("BucketNo")
    private BigDecimal BucketNo;
    @JsonProperty("CommentHeader")
    private String CommentHeader;
    @JsonProperty("CommentLine")
    private String CommentLine;
    @JsonProperty("InvoiceSubmissionDate")
    private String InvoiceSubmissionDate;
    @JsonProperty("ChaCode")
    private String ChaCode;
    @JsonProperty("Attribute1")
    private String Attribute1;
    @JsonProperty("Attribute2")
    private String Attribute2;
    @JsonProperty("AssignedCcoId")
    private String AssignedCcoId;

}
