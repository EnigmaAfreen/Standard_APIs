package com.airtel.buyer.airtelboe.service.country;


import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.country.response.CheckFtaData;
import com.airtel.buyer.airtelboe.dto.country.response.CountryData;

import java.util.List;

public interface CountryService {

    BoeResponse<List<CountryData>> countryList();

    BoeResponse<CheckFtaData> isFtaAndAntiDumping(String country);
}
