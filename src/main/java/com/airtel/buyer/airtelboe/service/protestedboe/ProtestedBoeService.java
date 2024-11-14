package com.airtel.buyer.airtelboe.service.protestedboe;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchprotestedboe.request.FetchProtestedRequest;
import com.airtel.buyer.airtelboe.dto.fetchprotestedboe.response.FetchProtestedData;
import com.airtel.buyer.airtelboe.dto.protestedboeaction.request.ProtestedBoeActionRequest;
import com.airtel.buyer.airtelboe.dto.protestedboeaction.response.ProtestedBoeActionData;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;

public interface ProtestedBoeService {

    BoeResponse<FetchProtestedData> protestedBoeInformation(FetchProtestedRequest fetchProtestedRequest, int page, int size);

    BoeResponse<ProtestedBoeActionData> protestedBoeAction(ProtestedBoeActionRequest protestedBoeActionRequest, UserDetailsImpl userDetails);

    void protestedBoeExcel(FetchProtestedRequest fetchProtestedRequest);

}
