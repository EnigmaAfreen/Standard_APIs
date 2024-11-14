package com.airtel.buyer.airtelboe.service.dutycategory;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchdutycategory.response.FetchDutyCategoryData;
import com.airtel.buyer.airtelboe.dto.port.response.PortData;

import java.util.List;

public interface DutyCategoryService {

    BoeResponse<List<FetchDutyCategoryData>> dutyCategoryList();

}
