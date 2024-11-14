package com.airtel.buyer.airtelboe.service.countryphonecode;


import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.countryphonecode.response.CountryPhoneCodeData;

import java.util.List;

public interface CountryPhoneCodeService {

    BoeResponse<List<CountryPhoneCodeData>> countryPhoneCodeList();
}
