package com.airtel.buyer.airtelboe.service.WpcInfo;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.wpcinfo.request.WpcInfoRequest;
import com.airtel.buyer.airtelboe.dto.wpcinfo.response.WpcInfoResponse;
import com.airtel.buyer.airtelboe.dto.wpcsubmitted.request.WpcSubmitRequest;
import com.airtel.buyer.airtelboe.dto.wpcsubmitted.response.WpcSubmitResponse;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;

public interface WpcInfoService {

    BoeResponse<WpcInfoResponse> getWpcInfoDashData(int page, int size, String olm, String role, WpcInfoRequest wpcInfoRequest);

    BoeResponse<WpcSubmitResponse> submitWpc(WpcSubmitRequest wpcSubmitRequest, UserDetailsImpl userDetails);

}
