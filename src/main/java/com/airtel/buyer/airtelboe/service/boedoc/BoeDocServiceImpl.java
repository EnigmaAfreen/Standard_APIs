package com.airtel.buyer.airtelboe.service.boedoc;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchboedoc.response.FetchBoeDocData;
import com.airtel.buyer.airtelboe.repository.ShipDoc;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipDocTblRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoeDocServiceImpl implements BoeDocService {

    @Autowired
    private BtvlWkfBoeShipDocTblRepository btvlWkfBoeShipDocTblRepository;

    @Override
    public BoeResponse<List<FetchBoeDocData>> boeDocInformation(BigDecimal shipmentId) {

        BoeResponse<List<FetchBoeDocData>> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getFetchBoeDocDataListObject(shipmentId));

        return boeResponse;
    }

    private List<FetchBoeDocData> getFetchBoeDocDataListObject(BigDecimal shipmentId) {

        List<FetchBoeDocData> fetchBoeDocDataList = null;

        List<ShipDoc> list = this.btvlWkfBoeShipDocTblRepository.fetchShipmentDocByShipmentId(shipmentId);

        if (list != null && !list.isEmpty()) {
            fetchBoeDocDataList = list.stream().map(this::getFetchBoeDocDataObject).collect(Collectors.toList());
        }

        return fetchBoeDocDataList;
    }

    private FetchBoeDocData getFetchBoeDocDataObject(ShipDoc shipDoc) {

        FetchBoeDocData fetchBoeDocData = new FetchBoeDocData();
        fetchBoeDocData.setDocName(shipDoc.getDocName());
        fetchBoeDocData.setContentId(shipDoc.getContentId());

        return fetchBoeDocData;
    }

}
