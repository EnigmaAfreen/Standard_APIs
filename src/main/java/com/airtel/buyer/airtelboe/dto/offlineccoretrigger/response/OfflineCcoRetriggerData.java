package com.airtel.buyer.airtelboe.dto.offlineccoretrigger.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfflineCcoRetriggerData {

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("chaName")
    private String chaName;

    @JsonProperty("airtelBoeRefNoErrMsg")
    private String shipmentIdErrMsg;

    @JsonProperty("chaNameErrMsg")
    private String chaNameErrMsg;

}
