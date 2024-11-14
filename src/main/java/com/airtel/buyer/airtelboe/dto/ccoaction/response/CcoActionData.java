package com.airtel.buyer.airtelboe.dto.ccoaction.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CcoActionData {

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("chaName")
    private String chaName;

    @JsonProperty("airtelBoeRefNoErrMsg")
    private String shipmentIdErrMsg;

    @JsonProperty("commentErrMsg")
    private String commentErrMsg;

    @JsonProperty("chaNameErrMsg")
    private String chaNameErrMsg;

}
