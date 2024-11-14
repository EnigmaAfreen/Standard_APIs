package com.airtel.buyer.airtelboe.dto.fetchchamaster.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FetchChaMasterData {

    @JsonProperty("legalEntity")
    private String legalEntity;

    @JsonProperty("lob")
    private String lob;

    @JsonProperty("ouName")
    private String ouName;

    @JsonProperty("portCode")
    private String portCode;

    @JsonProperty("portDescription")
    private String portDescription;

    @JsonProperty("chaName")
    private String chaName;

    @JsonProperty("chaCode")
    private String chaCode;

    @JsonProperty("chaEmail")
    private String chaEmail;

    @JsonProperty("chaPhoneNo")
    private String chaPhoneNo;

}
