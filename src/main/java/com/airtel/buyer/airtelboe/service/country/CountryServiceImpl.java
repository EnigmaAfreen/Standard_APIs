package com.airtel.buyer.airtelboe.service.country;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.country.response.CheckFtaData;
import com.airtel.buyer.airtelboe.dto.country.response.CountryData;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_ANTI_DUMPING_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_FTA_TBL;
import com.airtel.buyer.airtelboe.repository.Country;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeAntiDumpingRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeFtaTblRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    @Autowired
    private BtvlMstBoeFtaTblRepository btvlMstBoeFtaTblRepository;

    @Autowired
    private BtvlMstBoeAntiDumpingRepository btvlMstBoeAntiDumpingRepository;

    @Override
    public BoeResponse<List<CountryData>> countryList() {

        BoeResponse<List<CountryData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getCountryDataListObject());

        return boeResponse;
    }

    private List<CountryData> getCountryDataListObject() {

        List<CountryData> countryDataList = null;

        List<Country> ftaCountries = this.btvlMstBoeFtaTblRepository.fetchFtaCountryList();

        if (ftaCountries != null && !ftaCountries.isEmpty()) {
            countryDataList = ftaCountries.stream().map(this::getCountryDataObject).collect(Collectors.toList());
        }

        return countryDataList;
    }

    private CountryData getCountryDataObject(Country country) {

        CountryData countryData = new CountryData();
        countryData.setCountry(country.getCountry());
        countryData.setCountryCode(country.getCountryCode());

        return countryData;
    }

    @Override
    public BoeResponse<CheckFtaData> isFtaAndAntiDumping(String country) {
        BoeResponse<CheckFtaData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getCheckFtaDataObject(country));

        return boeResponse;
    }

    private CheckFtaData getCheckFtaDataObject(String country) {

        CheckFtaData checkFtaData = new CheckFtaData();

        List<BTVL_MST_BOE_FTA_TBL> ftaList = this.btvlMstBoeFtaTblRepository.findByCountryCode(country);

        if (ftaList != null && !ftaList.isEmpty()) {
            checkFtaData.setIsFta(Boolean.TRUE);
        } else {
            checkFtaData.setIsFta(Boolean.FALSE);
        }

        checkFtaData = getCheckAntiDumpingDataObject(country, checkFtaData);

        return checkFtaData;
    }

    private CheckFtaData getCheckAntiDumpingDataObject(String country, CheckFtaData checkFtaData) {

        List<BTVL_MST_BOE_ANTI_DUMPING_TBL> antiDumpingList = this.btvlMstBoeAntiDumpingRepository.findByCountryCode(country);

        if (antiDumpingList != null && !antiDumpingList.isEmpty()) {
            checkFtaData.setIsAntiDumping(Boolean.TRUE);
        } else {
            checkFtaData.setIsAntiDumping(Boolean.FALSE);
        }

        return checkFtaData;
    }
}
