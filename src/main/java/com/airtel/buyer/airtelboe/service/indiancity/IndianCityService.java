package com.airtel.buyer.airtelboe.service.indiancity;


import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.indiancity.response.IndianCityData;

import java.math.BigDecimal;
import java.util.List;

public interface IndianCityService {

    BoeResponse<List<IndianCityData>> indianCityList(BigDecimal pincode);

}
