package com.airtel.buyer.airtelboe.dto.fetchofflinecco.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FetchOfflineCcoData {

    @JsonProperty("filter")
    private OfflineCcoFilter offlineCcoFilter;

    @JsonProperty("overAllBucketCount")
    private Integer overAllBucketCount;

    @JsonProperty("onHoldBucketCount")
    private Integer onHoldBucketCount;

    @JsonProperty("overAllBucketRecords")
    private List<OfflineCcoRecord> overAllBucketList;

    @JsonProperty("onHoldBucketRecords")
    private List<OfflineCcoRecord> onHoldBucketList;

}
