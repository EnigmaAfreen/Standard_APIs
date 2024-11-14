package com.airtel.buyer.airtelboe.dto.addwpcmaster.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AddWpcMasterRequest {

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("licenseDate")
    private LocalDate licenseDate;

}
