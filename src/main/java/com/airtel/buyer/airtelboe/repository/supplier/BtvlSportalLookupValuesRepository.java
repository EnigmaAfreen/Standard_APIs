package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_SPORTAL_LOOKUP_VALUES;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BtvlSportalLookupValuesRepository extends JpaRepository<BTVL_SPORTAL_LOOKUP_VALUES, String> {

    List<BTVL_SPORTAL_LOOKUP_VALUES> findByLookupTypeAndMeaning(String lookupType, String meaning);

}
