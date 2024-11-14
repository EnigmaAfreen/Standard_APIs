package com.airtel.buyer.airtelboe.dto.editepcgmaster.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EditEpcgMasterData {

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

    @JsonProperty("epcgIdErrMsg")
    private String epcgIdErrMsg;

    @JsonProperty("legalEntityErrMsg")
    private String legalEntityErrMsg;

    @JsonProperty("itemCodeErrMsg")
    private String itemCodeErrMsg;

    @JsonProperty("itemDescriptionErrMsg")
    private String itemDescriptionErrMsg;

    @JsonProperty("portErrMsg")
    private String portErrMsg;

    @JsonProperty("endDateErrMsg")
    private String endDateErrMsg;

}
