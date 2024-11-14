package com.airtel.buyer.airtelboe.repository.erp;


import com.airtel.buyer.airtelboe.model.erp.FND_CURRENCIES_VL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FndCurrenciesVlRepository extends JpaRepository<FND_CURRENCIES_VL, String> {

    List<FND_CURRENCIES_VL> findByCurrencyFlagAndEnabledFlag(String currencyFlag, String enabledFlag);
}
