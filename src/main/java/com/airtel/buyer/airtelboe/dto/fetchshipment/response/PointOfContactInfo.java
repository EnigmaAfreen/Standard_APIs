package com.airtel.buyer.airtelboe.dto.fetchshipment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PointOfContactInfo {

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

}
