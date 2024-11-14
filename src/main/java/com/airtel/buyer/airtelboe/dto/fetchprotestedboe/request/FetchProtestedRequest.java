package com.airtel.buyer.airtelboe.dto.fetchprotestedboe.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FetchProtestedRequest {

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("validFrom")
    private LocalDate validFrom;

    @JsonProperty("validTo")
    private LocalDate validTo;

}