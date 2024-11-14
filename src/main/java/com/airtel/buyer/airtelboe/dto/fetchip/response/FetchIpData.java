package com.airtel.buyer.airtelboe.dto.fetchip.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FetchIpData {

    @JsonProperty("filter")
    private IpFilter ipFilter;

    @JsonProperty("overAllBucketCount")
    private Integer overAllBucketCount;

    @JsonProperty("rfiBucketCount")
    private Integer rfiBucketCount;

    @JsonProperty("overAllBucketRecords")
    private List<IpRecord> overAllBucketList;

    @JsonProperty("rfiBucketRecords")
    private List<IpRecord> rfiBucketList;

}
