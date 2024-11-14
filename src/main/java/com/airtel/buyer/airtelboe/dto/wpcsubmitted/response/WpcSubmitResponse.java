package com.airtel.buyer.airtelboe.dto.wpcsubmitted.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WpcSubmitResponse {
    @JsonProperty("airtelBoeRefNo")
    private List<BigDecimal> airtelBoeRefNo;

    @JsonProperty("status")
    private String status;

    @JsonProperty("airtelBoeRefNoErrMsg")
    private String airtelBoeRefNoErrMsg;
}
