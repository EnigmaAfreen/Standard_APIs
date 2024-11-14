package com.airtel.buyer.airtelboe.dto.addepcgmaster.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AddEpcgMasterData {

    @JsonProperty("licenseNo")
    private String licenseNo;

    @JsonProperty("legalEntity")
    private String legalEntity;

    @JsonProperty("itemSerialNo")
    private String itemSerialNo;

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("itemDescription")
    private String itemDescription;

    @JsonProperty("port")
    private String port;

    @JsonProperty("totalItemQty")
    private BigDecimal totalItemQty;

    @JsonProperty("balanceQty")
    private BigDecimal balanceQty;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("licenseDate")
    private LocalDate licenseDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("licenseNoErrMsg")
    private String licenseNoErrMsg;

    @JsonProperty("legalEntityErrMsg")
    private String legalEntityErrMsg;

    @JsonProperty("itemCodeErrMsg")
    private String itemCodeErrMsg;

    @JsonProperty("itemDescriptionErrMsg")
    private String itemDescriptionErrMsg;

    @JsonProperty("portErrMsg")
    private String portErrMsg;

    @JsonProperty("totalItemQtyErrMsg")
    private String totalItemQtyErrMsg;

    @JsonProperty("balanceQtyErrMsg")
    private String balanceQtyErrMsg;

    @JsonProperty("licenseDateErrMsg")
    private String licenseDateErrMsg;

    @JsonProperty("endDateErrMsg")
    private String endDateErrMsg;

}
