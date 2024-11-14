package com.airtel.buyer.airtelboe.dto.protestedboeaction.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProtestedBoeActionRequest {

    @JsonProperty("airtelBoeRefNos")
    private List<BigDecimal> shipmentIdList;
    
}
