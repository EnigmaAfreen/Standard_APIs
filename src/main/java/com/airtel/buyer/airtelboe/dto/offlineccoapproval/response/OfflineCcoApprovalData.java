package com.airtel.buyer.airtelboe.dto.offlineccoapproval.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfflineCcoApprovalData {

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("approveComment")
    private String approveComment;

    @JsonProperty("offlineCcoAttachment1DocPath")
    private String offlineCcoAttachment1DocPath;

    @JsonProperty("offlineCcoAttachment2DocPath")
    private String offlineCcoAttachment2DocPath;

    @JsonProperty("offlineCcoAttachment3DocPath")
    private String offlineCcoAttachment3DocPath;

    @JsonProperty("offlineCcoAttachment4DocPath")
    private String offlineCcoAttachment4DocPath;

    @JsonProperty("airtelBoeRefNoErrMsg")
    private String shipmentIdErrMsg;

    @JsonProperty("approveCommentErrMsg")
    private String approveCommentErrMsg;

    @JsonProperty("offlineCcoAttachment1DocPathErrMsg")
    private String offlineCcoAttachment1DocPathErrMsg;

}
