package com.airtel.buyer.airtelboe.dto.wpcinquiry.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WpcInquiryResponseData {

    @JsonProperty("FilterResponse")
    FilterResponse filterResponse;

    @JsonProperty("WpcRecords")
    List<WpcInquiryRecords> wpcInquiryRecords;
}
