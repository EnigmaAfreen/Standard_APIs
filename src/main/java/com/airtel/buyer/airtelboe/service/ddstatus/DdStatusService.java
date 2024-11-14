package com.airtel.buyer.airtelboe.service.ddstatus;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.ddstatus.request.DdStatusRequest;
import com.airtel.buyer.airtelboe.dto.ddstatus.response.DdStatusRecords;
import com.airtel.buyer.airtelboe.dto.ddstatus.response.DdStatusResponse;
import com.airtel.buyer.airtelboe.dto.fetchboemismatch.response.BoeMismatchData;
import com.airtel.buyer.airtelboe.dto.scmdashboard.request.ScmDashRequest;
import com.airtel.buyer.airtelboe.dto.scmdashboard.response.ScmDashResponse;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public interface DdStatusService {
    BoeResponse<DdStatusResponse> fetchDdStatusDashData(int page, int size,DdStatusRequest ddStatusRequest);
    BoeResponse<DdStatusResponse> getDdStatusDashExcelData(int page, int size, DdStatusRequest ddStatusRequest, HttpServletResponse httpServletResponse);
}
