package com.airtel.buyer.airtelboe.service.indiancity;


import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.indiancity.response.IndianCityData;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_GST_PIN_STATE_CITY_MV;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlGstPinStateCityMvRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IndianCityServiceImpl implements IndianCityService {

    @Autowired
    private BtvlGstPinStateCityMvRepository btvlGstPinStateCityMvRepository;

    @Override
    public BoeResponse<List<IndianCityData>> indianCityList(BigDecimal pincode) {

        BoeResponse<List<IndianCityData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getIndianCityDataListObject(pincode));

        return boeResponse;
    }

    private List<IndianCityData> getIndianCityDataListObject(BigDecimal pincode) {

        List<IndianCityData> indianCityDataList = null;

        List<BTVL_GST_PIN_STATE_CITY_MV> cities = this.btvlGstPinStateCityMvRepository.findByPincode(pincode);

        if (cities != null && !cities.isEmpty()) {
            indianCityDataList = cities.stream().map(this::getIndianCityDataObject).collect(Collectors.toList());
        }

        return indianCityDataList;
    }

    private IndianCityData getIndianCityDataObject(BTVL_GST_PIN_STATE_CITY_MV bTVL_GST_PIN_STATE_CITY_MV) {

        IndianCityData indianCityData = new IndianCityData();
        indianCityData.setCity(bTVL_GST_PIN_STATE_CITY_MV.getCity());
        indianCityData.setCityCode(bTVL_GST_PIN_STATE_CITY_MV.getCity());

        return indianCityData;
    }

}
