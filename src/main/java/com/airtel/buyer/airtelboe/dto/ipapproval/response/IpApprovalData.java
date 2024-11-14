package com.airtel.buyer.airtelboe.dto.ipapproval.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IpApprovalData {

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("chaName")
    private String chaName;

    @JsonProperty("airtelBoeRefNoErrMsg")
    private String shipmentIdErrMsg;

    @JsonProperty("chaNameErrMsg")
    private String chaNameErrMsg;

}
