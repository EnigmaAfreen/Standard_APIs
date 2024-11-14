package com.airtel.buyer.airtelboe.dto.scmrfiresponse.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ScmRfiResponseReq {

    @JsonProperty("rfiComment")
    private String rfiComment;

    @JsonProperty("airtelBoeRefNo")
    private BigDecimal shipmentId;

    @JsonProperty("shipmentInfo")
    private ShipmentReq shipmentReq;

    @JsonProperty("flag")
    private String flag;

    @JsonProperty("shipmentLines")
    private List<ShipmentLineReq> shipmentLineReqList;

    @JsonProperty("pointOfContactInfo")
    private PointOfContactReq pointOfContactReq;

    @JsonProperty("shipmentDocs")
    private List<ShipmentDocReq> shipmentDocReqList;

}
