package com.airtel.buyer.airtelboe.dto.ddstatus.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DdStatusRecords {

    @JsonProperty("ddId")
    private BigDecimal ddId;

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("airtelReferrenceNo")
    private BigDecimal airtelReferrenceNo;

    @JsonProperty("shipmentAwbNo")
    private String shipmentAwbNo;

    @JsonProperty("invoiceNumbers")
    private String invoiceNumbers;

    @JsonProperty("documentNo")
    private String documentNo;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("payable")
    private String payable;

    @JsonProperty("port")
    private String port;

    @JsonProperty("ddDate")
    private Date ddDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("ddReferrenceN")
    private String ddReferrenceN;

    @JsonProperty("chaName")
    private String chaName;

    @JsonProperty("partnerVendorCode")
    private BigDecimal partnerVendorCode;

    @JsonProperty("isChecked")
    private Boolean isChecked;

}
