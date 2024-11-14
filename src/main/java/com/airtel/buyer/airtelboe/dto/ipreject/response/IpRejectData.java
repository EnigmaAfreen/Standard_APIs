package com.airtel.buyer.airtelboe.dto.ipreject.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IpRejectData {

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("chaName")
    private String chaName;

    @JsonProperty("rejectionReason")
    private String rejectionReason;

    @JsonProperty("rejectionActualAmount")
    private String rejectionActualAmount;

    @JsonProperty("rejectionDescription")
    private String rejectionDescription;

    @JsonProperty("airtelBoeRefNoErrMsg")
    private String shipmentIdErrMsg;

    @JsonProperty("chaNameErrMsg")
    private String chaNameErrMsg;

    @JsonProperty("rejectionReasonErrMsg")
    private String rejectionReasonErrMsg;

    @JsonProperty("rejectionActualAmountErrMsg")
    private String rejectionActualAmountErrMsg;

    @JsonProperty("rejectionDescriptionErrMsg")
    private String rejectionDescriptionErrMsg;

}
