package com.airtel.buyer.airtelboe.service.countryphonecode;


import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.countryphonecode.response.CountryPhoneCodeData;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_COUNTRY_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstCountryTblRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CountryPhoneCodeServiceImpl implements CountryPhoneCodeService {

    @Autowired
    private BtvlMstCountryTblRepository btvlMstCountryTblRepository;

    @Override
    public BoeResponse<List<CountryPhoneCodeData>> countryPhoneCodeList() {

        BoeResponse<List<CountryPhoneCodeData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getPortDataListObject());

        return boeResponse;
    }

    private List<CountryPhoneCodeData> getPortDataListObject() {

        List<CountryPhoneCodeData> countryPhoneCodeDataList = null;

        List<BTVL_MST_COUNTRY_TBL> countryPhoneCodes = this.btvlMstCountryTblRepository.findAll();

        if (countryPhoneCodes != null && !countryPhoneCodes.isEmpty()) {
            countryPhoneCodeDataList = countryPhoneCodes.stream().map(this::getCountryPhoneCodeDataObject).collect(Collectors.toList());
        }

        return countryPhoneCodeDataList;
    }

    private CountryPhoneCodeData getCountryPhoneCodeDataObject(BTVL_MST_COUNTRY_TBL bTVL_MST_COUNTRY_TBL) {

        CountryPhoneCodeData countryPhoneCodeData = new CountryPhoneCodeData();
        countryPhoneCodeData.setCountryCode(bTVL_MST_COUNTRY_TBL.getCountryCode());
        countryPhoneCodeData.setCountryPhoneCode(bTVL_MST_COUNTRY_TBL.getCountryPhCode());

        return countryPhoneCodeData;
    }

}
