package com.airtel.buyer.airtelboe.service.epcginquiry;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.epcginquiry.request.EpcgInquiryRequest;
import com.airtel.buyer.airtelboe.dto.epcginquiry.response.EpcgInquiryResponseData;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.request.WpcInquiryRequest;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.response.WpcInquiryResponseData;

import javax.servlet.http.HttpServletResponse;

public interface EpcgInquiryService {

    BoeResponse<EpcgInquiryResponseData> getEpcgInquiryData(EpcgInquiryRequest epcgInquiryRequest);

    BoeResponse<EpcgInquiryResponseData> getEpcgExcelData(EpcgInquiryRequest epcgInquiryRequest, HttpServletResponse httpServletResponse);

}
