package com.airtel.buyer.airtelboe.service.currency;


import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.currency.response.CurrencyData;

import java.util.List;

public interface CurrencyService {

    BoeResponse<List<CurrencyData>> currencyList();
}
