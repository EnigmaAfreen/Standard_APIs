package com.airtel.buyer.airtelboe.dto.fetchchecklist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerInfoRes {

    @JsonProperty("iecCode")
    private String iecCode;

    @JsonProperty("branchCode")
    private BigDecimal branchCode;

    @JsonProperty("gstn")
    private String gstn;

    @JsonProperty("panNo")
    private String panNo;

    @JsonProperty("authorizedDealerCode")
    private String authorizedDealerCode;

    @JsonProperty("legalEntity")
    private String legalEntity;

    @JsonProperty("importerName")
    private String importerName;

    @JsonProperty("importerAddress")
    private String importerAddress;

    @JsonProperty("shipTo")
    private String shipTo;

}
