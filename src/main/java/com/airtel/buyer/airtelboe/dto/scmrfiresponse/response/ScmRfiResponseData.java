package com.airtel.buyer.airtelboe.dto.scmrfiresponse.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ScmRfiResponseData {

    @JsonProperty("rfiComment")
    private String rfiComment;

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("shipmentInfo")
    private ShipmentRes shipmentRes;

    @JsonProperty("shipmentLines")
    private List<ShipmentLineRes> shipmentLineResList;

    @JsonProperty("pointOfContactInfo")
    private PointOfContactRes pointOfContactRes;

    @JsonProperty("shipmentDocs")
    private List<ShipmentDocRes> shipmentDocResList;

    @JsonProperty("rfiCommentErrMsg")
    private String rfiCommentErrMsg;

    @JsonProperty("airtelBoeRefNoErrMsg")
    private String shipmentIdErrMsg;

//    @JsonProperty("shipmentInfoErrMsg")
//    private String shipmentResErrMsg;

}
