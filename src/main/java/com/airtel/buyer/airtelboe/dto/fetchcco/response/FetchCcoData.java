package com.airtel.buyer.airtelboe.dto.fetchcco.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FetchCcoData {

    @JsonProperty("filter")
    private CcoFilter ccoFilter;

    @JsonProperty("overAllBucketCount")
    private Integer overAllBucketCount;

    @JsonProperty("onHoldBucketCount")
    private Integer onHoldBucketCount;

    @JsonProperty("overAllBucketRecords")
    private List<CcoRecord> overAllBucketList;

    @JsonProperty("onHoldBucketRecords")
    private List<CcoRecord> onHoldBucketList;

}
