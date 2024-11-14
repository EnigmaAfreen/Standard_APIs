package com.airtel.buyer.airtelboe.dto.protestedboeaction.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProtestedBoeActionData {

    @JsonProperty("airtelBoeRefNos")
    private List<BigDecimal> shipmentIdList;

    @JsonProperty("airtelBoeRefNoErrMsg")
    private String shipmentIdErrMsg;

}
