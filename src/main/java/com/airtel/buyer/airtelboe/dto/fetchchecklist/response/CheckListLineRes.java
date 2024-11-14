package com.airtel.buyer.airtelboe.dto.fetchchecklist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CheckListLineRes {

    @JsonProperty("itemCodeAndDesc")
    private String itemCodeAndDesc;

    @JsonProperty("itemQty")
    private BigDecimal itemQty;

    @JsonProperty("unitPrice")
    private BigDecimal unitPrice;

    @JsonProperty("hsn")
    private String hsn;

    @JsonProperty("assessableValue")
    private BigDecimal assessableValue;

    @JsonProperty("basicCustomDutyPer")
    private BigDecimal basicCustomDutyPer;

    @JsonProperty("swsPer")
    private BigDecimal swsPer;

    @JsonProperty("igstPer")
    private BigDecimal igstPer;

    @JsonProperty("basicCustomDuty")
    private BigDecimal basicCustomDuty;

    @JsonProperty("sws")
    private BigDecimal sws;

    @JsonProperty("igst")
    private BigDecimal igst;

    @JsonProperty("compensationCessOnIgst")
    private BigDecimal compensationCessOnIgst;

    @JsonProperty("dutyOtherThanGst")
    private BigDecimal dutyOtherThanGst;

    @JsonProperty("totalDuty")
    private BigDecimal totalDuty;

    @JsonProperty("antiDumpingPer")
    private BigDecimal antiDumpingPer;

    @JsonProperty("antiDumpingAmt")
    private BigDecimal antiDumpingAmt;

    @JsonProperty("exemptionNotn")
    private String exemptionNotn;

    @JsonProperty("wpcLicenseNo")
    private String wpcLicenseNo;

    @JsonProperty("epcgLicenseNo")
    private String epcgLicenseNo;

}
