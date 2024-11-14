package com.airtel.buyer.airtelboe.dto.editepcgmaster.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EditEpcgMasterRequest {

    @JsonProperty("epcgId")
    private BigDecimal epcgId;

    @JsonProperty("legalEntity")
    private String legalEntity;

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("itemDescription")
    private String itemDescription;

    @JsonProperty("port")
    private String port;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("endDate")
    private LocalDate endDate;

}
