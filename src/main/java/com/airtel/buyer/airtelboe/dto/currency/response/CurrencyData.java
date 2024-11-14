package com.airtel.buyer.airtelboe.dto.currency.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrencyData {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("currencyCode")
    private String currencyCode;

}
