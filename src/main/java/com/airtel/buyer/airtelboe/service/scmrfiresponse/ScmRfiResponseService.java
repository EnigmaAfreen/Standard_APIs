package com.airtel.buyer.airtelboe.service.scmrfiresponse;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.scmrfiresponse.request.ScmRfiResponseReq;
import com.airtel.buyer.airtelboe.dto.scmrfiresponse.response.ScmRfiResponseData;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import org.springframework.stereotype.Service;

@Service
public interface ScmRfiResponseService {

    BoeResponse<ScmRfiResponseData> rfiResponse(ScmRfiResponseReq scmRfiResponseReq, UserDetailsImpl userDetails);
}
