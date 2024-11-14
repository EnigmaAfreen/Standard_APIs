package com.airtel.buyer.airtelboe.service.scmdashboard;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.scmdashboard.request.ScmDashRequest;
import com.airtel.buyer.airtelboe.dto.scmdashboard.response.ScmDashResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public interface ScmDashboardService {

    BoeResponse<ScmDashResponse> getScmDashData(int page, int size, String olm, String role, ScmDashRequest scmDashRequest);

    BoeResponse<ScmDashResponse> getScmDashExcelData(int page, int size, String olm, String role, ScmDashRequest scmDashRequest, HttpServletResponse httpServletResponse);

    BoeResponse<ScmDashResponse> getScmDashRawExcelData(int page, int size, String olm, String role, ScmDashRequest scmDashRequest, HttpServletResponse httpServletResponse);

}
