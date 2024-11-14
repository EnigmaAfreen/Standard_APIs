package com.airtel.buyer.airtelboe.service.fetchagentlist;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchchaagentlist.response.ChaAgentListData;
import com.airtel.buyer.airtelboe.dto.fetchchareassignmentpagedata.request.FetchChaReassignmentPageDataRequest;
import com.airtel.buyer.airtelboe.dto.fetchchareassignmentpagedata.response.ChaReassignmentPageData;

public interface ChaReAssignmentService {

    BoeResponse<ChaAgentListData> fetchChaAgentList();

    BoeResponse<ChaReassignmentPageData> fetchReAssignmentPageData(FetchChaReassignmentPageDataRequest fetchChaReassignmentPageDataRequest,String olm, String role);

}
