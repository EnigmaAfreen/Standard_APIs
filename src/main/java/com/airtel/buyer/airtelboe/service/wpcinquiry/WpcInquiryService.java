package com.airtel.buyer.airtelboe.service.wpcinquiry;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.request.WpcInquiryRequest;
import com.airtel.buyer.airtelboe.dto.wpcinquiry.response.WpcInquiryResponseData;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public interface WpcInquiryService {

    BoeResponse<WpcInquiryResponseData> getWpcData(WpcInquiryRequest wpcInquiryRequest);

    BoeResponse<WpcInquiryResponseData> getWpcExcelData(WpcInquiryRequest wpcInquiryRequest, HttpServletResponse httpServletResponse);
}
