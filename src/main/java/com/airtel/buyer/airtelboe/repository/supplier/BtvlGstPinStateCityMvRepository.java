package com.airtel.buyer.airtelboe.repository.supplier;


import com.airtel.buyer.airtelboe.model.BTVL_GST_PIN_STATE_CITY_PK;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_GST_PIN_STATE_CITY_MV;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlGstPinStateCityMvRepository extends JpaRepository<BTVL_GST_PIN_STATE_CITY_MV, BTVL_GST_PIN_STATE_CITY_PK> {

    List<BTVL_GST_PIN_STATE_CITY_MV> findByPincode(BigDecimal pincode);
    
}
