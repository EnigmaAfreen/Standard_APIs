package com.airtel.buyer.airtelboe.dto.epcginquiry.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EpcgInquiryResponseData {

    @JsonProperty("filter")
    private EpcgFilter epcgFilter;

    @JsonProperty("epcgRecords")
    private List<EpcgInquiryRecords> epcgInquiryRecordsList;
}
