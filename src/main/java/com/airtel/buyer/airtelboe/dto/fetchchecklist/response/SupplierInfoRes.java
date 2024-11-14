package com.airtel.buyer.airtelboe.dto.fetchchecklist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SupplierInfoRes {

    @JsonProperty("supplierExporterName")
    private String supplierExporterName;

    @JsonProperty("supplierExporterAddress")
    private String supplierExporterAddress;

    @JsonProperty("supplierExporterCountry")
    private String supplierExporterCountry;

}
