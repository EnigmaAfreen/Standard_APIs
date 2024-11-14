package com.airtel.buyer.airtelboe.dto.scmrfiresponse.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PointOfContactRes {

//    @JsonProperty("shipmentPocId")
//    private BigDecimal shipmentPocId;

    @JsonProperty("nameOfContactPerson")
    private String nameOfContactPerson;

    @JsonProperty("country")
    private String country;

    @JsonProperty("city")
    private String city;

    @JsonProperty("zipcode")
    private String zipcode;

    @JsonProperty("address")
    private String address;

    @JsonProperty("countryMobCode")
    private String countryMobCode;

    @JsonProperty("contactNumber")
    private String contactNumber;

    @JsonProperty("emailId")
    private String emailId;

//    @JsonProperty("shipmentPocIdErrMsg")
//    private String shipmentPocIdErrMsg;

    @JsonProperty("nameOfContactPersonErrMsg")
    private String nameOfContactPersonErrMsg;

    @JsonProperty("countryErrMsg")
    private String countryErrMsg;

    @JsonProperty("cityErrMsg")
    private String cityErrMsg;

    @JsonProperty("zipcodeErrMsg")
    private String zipcodeErrMsg;

    @JsonProperty("addressErrMsg")
    private String addressErrMsg;

    @JsonProperty("countryMobCodeErrMsg")
    private String countryMobCodeErrMsg;

    @JsonProperty("contactNumberErrMsg")
    private String contactNumberErrMsg;

    @JsonProperty("emailIdErrMsg")
    private String emailIdErrMsg;
}
