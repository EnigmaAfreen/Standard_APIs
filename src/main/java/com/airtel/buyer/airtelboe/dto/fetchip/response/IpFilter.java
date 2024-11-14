package com.airtel.buyer.airtelboe.dto.fetchip.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IpFilter {

    @JsonProperty("binNo")
    private Integer binNo;

    @JsonProperty("ou")
    private String ou;

    @JsonProperty("boeNo")
    private String boeNo;

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("rms")
    private String rms;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("validFrom")
    private LocalDate validFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @JsonProperty("validTo")
    private LocalDate validTo;

}
