package com.airtel.buyer.airtelboe.service.scm;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.scm.stage2.request.SubmitStage2Request;
import com.airtel.buyer.airtelboe.dto.scm.stage2.response.SubmitStage2Response;
import com.airtel.buyer.airtelboe.dto.scm.stage4.request.ScmStage4RejectRequest;
import com.airtel.buyer.airtelboe.dto.scm.stage4.request.SubmitStage4Request;
import com.airtel.buyer.airtelboe.dto.scm.stage4.response.ScmStage4RejectResponse;
import com.airtel.buyer.airtelboe.dto.scm.stage4.response.SubmitStage4Response;

public interface ScmService {

    BoeResponse<SubmitStage2Response> scmSubmitStage2Information(SubmitStage2Request submitStage2Request);
    Boolean isFta(String countryCode);
    Boolean isAntiDumping(String countryCode);
    BoeResponse<SubmitStage4Response> stage4ApproveByScm(SubmitStage4Request submitStage4Request);

    BoeResponse<ScmStage4RejectResponse> stage4RejectByScm(ScmStage4RejectRequest scmStage4RejectRequest);
}
