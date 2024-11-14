package com.airtel.buyer.airtelboe.service.cco;

import com.airtel.buyer.airtelboe.dto.ccoaction.request.CcoActionRequest;
import com.airtel.buyer.airtelboe.dto.ccoaction.response.CcoActionData;
import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchcco.request.FetchCcoRequest;
import com.airtel.buyer.airtelboe.dto.fetchcco.response.FetchCcoData;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;

public interface CcoService {

    BoeResponse<FetchCcoData> ccoInformation(FetchCcoRequest fetchCcoRequest, int page, int size, UserDetailsImpl userDetails);

    BoeResponse<CcoActionData> ccoApprove(CcoActionRequest ccoActionRequest, UserDetailsImpl userDetails);

    BoeResponse<CcoActionData> ccoReject(CcoActionRequest ccoActionRequest, UserDetailsImpl userDetails);

    BoeResponse<CcoActionData> ccoHold(CcoActionRequest ccoActionRequest, UserDetailsImpl userDetails);

    BoeResponse<CcoActionData> ccoUnHold(CcoActionRequest ccoActionRequest, UserDetailsImpl userDetails);

    void ccoExcel(FetchCcoRequest fetchRequest, UserDetailsImpl userDetails);

}
