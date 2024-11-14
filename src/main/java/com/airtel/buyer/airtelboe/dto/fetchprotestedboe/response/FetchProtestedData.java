package com.airtel.buyer.airtelboe.dto.fetchprotestedboe.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FetchProtestedData {

    @JsonProperty("filter")
    private ProtestedBoeFilter protestedBoeFilter;

    @JsonProperty("protestedBoeRecords")
    private List<ProtestedBoeRecord> protestedBoeRecordList;

}
