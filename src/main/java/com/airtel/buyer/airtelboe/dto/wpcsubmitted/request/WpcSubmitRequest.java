package com.airtel.buyer.airtelboe.dto.wpcsubmitted.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WpcSubmitRequest {

    @JsonProperty("airtelBoeRefNo")
    private List<BigDecimal> airtelBoeRefNo;
}
