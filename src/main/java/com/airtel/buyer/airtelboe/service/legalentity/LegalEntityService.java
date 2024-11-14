package com.airtel.buyer.airtelboe.service.legalentity;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchlegalentity.response.FetchLegalEntityData;

import java.util.List;

public interface LegalEntityService {

    BoeResponse<List<FetchLegalEntityData>> leList();

}
