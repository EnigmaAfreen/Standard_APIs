package com.airtel.buyer.airtelboe.service.scmloboulist;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.scmloblist.response.ScmLobListResponse;
import com.airtel.buyer.airtelboe.dto.scmoulist.response.OuListResponseData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScmLobOuListService {

    BoeResponse<List<ScmLobListResponse>> getLobNames(String olm, String role);

    BoeResponse<List<OuListResponseData>> getOuList(String lob);
}
