package com.airtel.buyer.airtelboe.service.currency;


import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.currency.response.CurrencyData;
import com.airtel.buyer.airtelboe.model.erp.FND_CURRENCIES_VL;
import com.airtel.buyer.airtelboe.repository.erp.FndCurrenciesVlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private FndCurrenciesVlRepository fndCurrenciesVlRepository;

    @Override
    public BoeResponse<List<CurrencyData>> currencyList() {

        BoeResponse<List<CurrencyData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getCurrencyDataListObject());

        return boeResponse;
    }

    private List<CurrencyData> getCurrencyDataListObject() {

        List<CurrencyData> currencyDataList = null;

        List<FND_CURRENCIES_VL> currencies = this.fndCurrenciesVlRepository.findByCurrencyFlagAndEnabledFlag("Y", "Y");

        if (currencies != null && !currencies.isEmpty()) {
            currencyDataList = currencies.stream().map(this::getCurrencyDataObject).collect(Collectors.toList());
        }

        return currencyDataList;
    }

    private CurrencyData getCurrencyDataObject(FND_CURRENCIES_VL fND_CURRENCIES_VL) {

        CurrencyData currencyData = new CurrencyData();
        currencyData.setCurrency(fND_CURRENCIES_VL.getCurrencyCode());
        currencyData.setCurrencyCode(fND_CURRENCIES_VL.getCurrencyCode());

        return currencyData;
    }

}
