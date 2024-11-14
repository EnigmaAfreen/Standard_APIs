package com.airtel.buyer.airtelboe.service.dutycategory;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchdutycategory.response.FetchDutyCategoryData;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_SPORTAL_LOOKUP_VALUES;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlSportalLookupValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DutyCategoryServiceImpl implements DutyCategoryService {

    @Autowired
    private BtvlSportalLookupValuesRepository btvlSportalLookupValuesRepository;

    @Override
    public BoeResponse<List<FetchDutyCategoryData>> dutyCategoryList() {

        BoeResponse<List<FetchDutyCategoryData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchDutyCategoryDataListObject());

        return boeResponse;
    }

    private List<FetchDutyCategoryData> getFetchDutyCategoryDataListObject() {

        List<FetchDutyCategoryData> fetchDutyCategoryDataList = null;

        List<BTVL_SPORTAL_LOOKUP_VALUES> lookupCodeList =
                this.btvlSportalLookupValuesRepository.findByLookupTypeAndMeaning("BOE_DUTY_CATEGORY_TYPE", "BCD");

        if (lookupCodeList != null && !lookupCodeList.isEmpty()) {
            fetchDutyCategoryDataList = lookupCodeList.stream().map(this::getFetchDutyCategoryDataObject).collect(Collectors.toList());
        }

        return fetchDutyCategoryDataList;
    }

    private FetchDutyCategoryData getFetchDutyCategoryDataObject(BTVL_SPORTAL_LOOKUP_VALUES bTVL_SPORTAL_LOOKUP_VALUES) {

        FetchDutyCategoryData fetchDutyCategoryData = new FetchDutyCategoryData();
        fetchDutyCategoryData.setDutyCategoryName(bTVL_SPORTAL_LOOKUP_VALUES.getLookupCode());
        fetchDutyCategoryData.setDutyCategoryValue(bTVL_SPORTAL_LOOKUP_VALUES.getLookupCode());

        return fetchDutyCategoryData;
    }
}
