package com.airtel.buyer.airtelboe.dto.fetchchareassignmentpagedata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChaReassignmentPageData {

    @JsonProperty("assignmentFailedList")
    private List<ChaReassignmentPageDataList> assignmentFailedList;

    @JsonProperty("notApprovedList")
    private List<ChaReassignmentPageDataList> notApprovedList;

    @JsonProperty("approvedList")
    private List<ChaReassignmentPageDataList> approvedList;
}
