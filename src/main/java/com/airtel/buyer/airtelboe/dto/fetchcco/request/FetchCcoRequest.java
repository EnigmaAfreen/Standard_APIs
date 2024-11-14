package com.airtel.buyer.airtelboe.dto.fetchcco.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FetchCcoRequest {

    @JsonProperty("binNo")
    private Integer binNo;

    @JsonProperty("ou")
    private String ou;

    @JsonProperty("clearanceType")
    private String clearanceType;

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("validFrom")
    private LocalDate validFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("validTo")
    private LocalDate validTo;

}
