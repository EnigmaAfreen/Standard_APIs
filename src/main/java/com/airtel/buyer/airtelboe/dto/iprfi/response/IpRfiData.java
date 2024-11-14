package com.airtel.buyer.airtelboe.dto.iprfi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IpRfiData {

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("rfiReason")
    private String rfiReason;

    @JsonProperty("rfiDescription")
    private String rfiDescription;

    @JsonProperty("airtelBoeRefNoErrMsg")
    private String shipmentIdErrMsg;

    @JsonProperty("rfiReasonErrMsg")
    private String rfiReasonErrMsg;

    @JsonProperty("rfiDescriptionErrMsg")
    private String rfiDescriptionErrMsg;

}
