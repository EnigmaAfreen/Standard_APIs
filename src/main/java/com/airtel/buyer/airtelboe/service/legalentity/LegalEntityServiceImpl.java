package com.airtel.buyer.airtelboe.service.legalentity;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchlegalentity.response.FetchLegalEntityData;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlBoeOuLobMappTblRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LegalEntityServiceImpl implements LegalEntityService {

    @Autowired
    private BtvlBoeOuLobMappTblRepository btvlBoeOuLobMappTblRepository;

    @Override
    public BoeResponse<List<FetchLegalEntityData>> leList() {

        BoeResponse<List<FetchLegalEntityData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchLegalEntityDataListObject());

        return boeResponse;
    }

    private List<FetchLegalEntityData> getFetchLegalEntityDataListObject() {

        List<FetchLegalEntityData> fetchLegalEntityDataList = null;

        List<String> leList = this.btvlBoeOuLobMappTblRepository.findDistinctLeNames();

        if (leList != null && !leList.isEmpty()) {
            fetchLegalEntityDataList = leList.stream().map(this::getFetchLegalEntityDataObject).collect(Collectors.toList());
        }

        return fetchLegalEntityDataList;
    }

    private FetchLegalEntityData getFetchLegalEntityDataObject(String legalEntity) {

        FetchLegalEntityData fetchLegalEntityData = new FetchLegalEntityData();
        fetchLegalEntityData.setLeName(legalEntity);
        fetchLegalEntityData.setLeValue(legalEntity);

        return fetchLegalEntityData;
    }

}
