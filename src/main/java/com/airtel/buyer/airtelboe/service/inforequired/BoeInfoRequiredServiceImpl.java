package com.airtel.buyer.airtelboe.service.inforequired;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.inforequired.response.BoeInfoTrail;
import com.airtel.buyer.airtelboe.dto.inforequired.response.InfoRequiredData;
import com.airtel.buyer.airtelboe.repository.BoeInfoReq;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeTrailTblRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoeInfoRequiredServiceImpl implements BoeInfoRequiredService {

    @Autowired
    BtvlWkfBoeTrailTblRepository btvlWkfBoeTrailTblRepository;

    @Override
    public BoeResponse<InfoRequiredData> fetchInfoRequiredData(BigDecimal shipmentId) {
        BoeResponse boeResponse = new BoeResponse();
        boeResponse.setData(this.loadRequestedInformation(shipmentId));
        return boeResponse;
    }


    public InfoRequiredData loadRequestedInformation(BigDecimal shipmentNo) {
        InfoRequiredData infoRequiredData = new InfoRequiredData();

        List<BoeInfoReq> boeInfoReqList = this.btvlWkfBoeTrailTblRepository.fetchInfoReqDataForShipmentId(shipmentNo);
        if (boeInfoReqList != null) {
            infoRequiredData.setBoeInfoTrail(boeInfoReqList.stream().map(this::getInfoReqObject).collect(Collectors.toList()));
        }
        return infoRequiredData;
    }

    private BoeInfoTrail getInfoReqObject(BoeInfoReq boeInfoReq) {
        BoeInfoTrail infoRequiredData = new BoeInfoTrail();
        infoRequiredData.setTrailId(boeInfoReq.getTrailId());
        infoRequiredData.setStatus(boeInfoReq.getStatus());
        infoRequiredData.setBucketNo(boeInfoReq.getBucketNo());
        infoRequiredData.setActionBy(boeInfoReq.getActionBy());
        infoRequiredData.setCommentLine(boeInfoReq.getCommentLine());
        infoRequiredData.setShipmentId(boeInfoReq.getShipmentId());
        infoRequiredData.setCreationDate(boeInfoReq.getCreationDate());
        infoRequiredData.setCommentHeader(boeInfoReq.getCommentHeader());

        return infoRequiredData;
    }

}
