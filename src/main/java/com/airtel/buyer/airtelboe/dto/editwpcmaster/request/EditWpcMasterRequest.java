package com.airtel.buyer.airtelboe.dto.editwpcmaster.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EditWpcMasterRequest {

    @JsonProperty("wpcId")
    private BigDecimal wpcId;

    @JsonProperty("legalEntity")
    private String legalEntity;

    @JsonProperty("portCode")
    private String portCode;

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("licenseNo")
    private String licenseNo;

    @JsonProperty("wpcQuantity")
    private BigDecimal wpcQuantity;

    @JsonProperty("consumeQuantity")
    private BigDecimal consumeQuantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("endDate")
    private LocalDate endDate;

}