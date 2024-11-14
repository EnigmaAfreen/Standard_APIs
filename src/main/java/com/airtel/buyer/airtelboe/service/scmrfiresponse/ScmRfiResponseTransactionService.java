package com.airtel.buyer.airtelboe.service.scmrfiresponse;

import com.airtel.buyer.airtelboe.dto.document.response.DocumentResponse;
import com.airtel.buyer.airtelboe.dto.scmrfiresponse.request.*;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.*;
import com.airtel.buyer.airtelboe.repository.supplier.*;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import com.airtel.buyer.airtelboe.util.DmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScmRfiResponseTransactionService {

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlWkfBoeTrailTblRepository btvlWkfBoeTrailTblRepository;

    @Autowired
    private BtvlWkfBoeShipPocTblRepository btvlWkfBoeShipPocTblRepository;

    @Autowired
    private BtvlWkfBoeShipLineTblRepository btvlWkfBoeShipLineTblRepository;

    @Autowired
    private BtvlWkfBoeShipDocTblRepository btvlWkfBoeShipDocTblRepository;

    @Value("${UPLOAD.DOC.TO.UCM.URL}")
    private String uploadUcmUrl;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Transactional
    public void rfi(ScmRfiResponseReq scmRfiResponseReq,
                    UserDetailsImpl userDetails,
                    BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL) {

        if (scmRfiResponseReq.getShipmentDocReqList() != null &&
                !scmRfiResponseReq.getShipmentDocReqList().isEmpty()) {

            List<BTVL_WKF_BOE_SHIP_DOC_TBL> wkfShipDocList =
                    this.getWkfBoeShipDocListObject(scmRfiResponseReq.getShipmentDocReqList(),
                            bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId(), bTVL_WKF_BOE_SHIPMENT_TBL.getStage(),
                            userDetails.getUsername());
            this.btvlWkfBoeShipDocTblRepository.saveAll(wkfShipDocList);
        }

        this.setWkfBoeShipmentObject(bTVL_WKF_BOE_SHIPMENT_TBL,
                scmRfiResponseReq.getShipmentReq(), userDetails.getUsername());
        this.btvlWkfBoeShipmentTblRepository.save(bTVL_WKF_BOE_SHIPMENT_TBL);

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL =
                this.getWkfBoeTrailObject(new BigDecimal(13), new BigDecimal(4),
                        null, "RFI RESPONSE", "RFI RESPONSE",
                        bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId(),
                        scmRfiResponseReq.getRfiComment(),
                        userDetails.getUsername());
        this.btvlWkfBoeTrailTblRepository.save(bTVL_WKF_BOE_TRAIL_TBL);

        if (scmRfiResponseReq.getPointOfContactReq() != null) {
            BTVL_WKF_BOE_SHIP_POC_TBL bTVL_WKF_BOE_SHIP_POC_TBL =
                    this.getWkfBoeShipPocObject(scmRfiResponseReq.getPointOfContactReq(),
                            scmRfiResponseReq.getShipmentId());
            this.btvlWkfBoeShipPocTblRepository.save(bTVL_WKF_BOE_SHIP_POC_TBL);
        }

        if (scmRfiResponseReq.getShipmentLineReqList() != null &&
                !scmRfiResponseReq.getShipmentLineReqList().isEmpty()) {
            List<BTVL_WKF_BOE_SHIP_LINE_TBL> wkfShipLineList =
                    this.getWkfBoeShipLineListObject(scmRfiResponseReq.getShipmentLineReqList());
            this.btvlWkfBoeShipLineTblRepository.saveAll(wkfShipLineList);
        }

    }

    private void setWkfBoeShipmentObject(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL, ShipmentReq shipmentReq, String olmId) {

        bTVL_WKF_BOE_SHIPMENT_TBL.setStage(new BigDecimal(3));
        bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(new BigDecimal(8));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(new BigDecimal(4));
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(olmId);

        if (shipmentReq != null) {
            bTVL_WKF_BOE_SHIPMENT_TBL.setShipmentMode(shipmentReq.getShipmentMode());
            bTVL_WKF_BOE_SHIPMENT_TBL.setCountryLoading(shipmentReq.getCountryLoading());
            bTVL_WKF_BOE_SHIPMENT_TBL.setPortLoading(shipmentReq.getPortLoading());
            bTVL_WKF_BOE_SHIPMENT_TBL.setDestinationCountry(shipmentReq.getDestinationCountry());
            bTVL_WKF_BOE_SHIPMENT_TBL.setDestinationPort(shipmentReq.getDestinationPort());
            bTVL_WKF_BOE_SHIPMENT_TBL.setGrossShipmentWeight(shipmentReq.getGrossShipmentWeight());
            bTVL_WKF_BOE_SHIPMENT_TBL.setGrossShipmentVolume(shipmentReq.getGrossShipmentVolume());
            bTVL_WKF_BOE_SHIPMENT_TBL.setExpectedArrivalDate(shipmentReq.getExpectedArrivalDate());
            bTVL_WKF_BOE_SHIPMENT_TBL.setNoOfPackages(shipmentReq.getNoOfPackages());
            bTVL_WKF_BOE_SHIPMENT_TBL.setAwbBol(shipmentReq.getAwbBol());
            bTVL_WKF_BOE_SHIPMENT_TBL.setFreightAmtCurrency(shipmentReq.getFreightAmtCurrency());
            bTVL_WKF_BOE_SHIPMENT_TBL.setFreightAmt(shipmentReq.getFreightAmt());
            bTVL_WKF_BOE_SHIPMENT_TBL.setInsuranceAmtCurrency(shipmentReq.getInsuranceAmtCurrency());
            bTVL_WKF_BOE_SHIPMENT_TBL.setInsuranceAmt(shipmentReq.getInsuranceAmt());
            bTVL_WKF_BOE_SHIPMENT_TBL.setEfChargesAmt(shipmentReq.getEfChargesAmt());
            bTVL_WKF_BOE_SHIPMENT_TBL.setBoeNo(shipmentReq.getBoeNo());
            bTVL_WKF_BOE_SHIPMENT_TBL.setBoeDate(shipmentReq.getBoeDate());
            bTVL_WKF_BOE_SHIPMENT_TBL.setShipmentArrivalDate(shipmentReq.getShipmentArrivalDate());
        }

    }

    private BTVL_WKF_BOE_TRAIL_TBL getWkfBoeTrailObject(BigDecimal bucketNo, BigDecimal status,
                                                        BigDecimal stage, String action,
                                                        String commentHeader, BigDecimal shipmentId,
                                                        String comment, String olmId) {

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL = new BTVL_WKF_BOE_TRAIL_TBL();
        bTVL_WKF_BOE_TRAIL_TBL.setBucketNo(bucketNo);
        bTVL_WKF_BOE_TRAIL_TBL.setStatus(status);
        bTVL_WKF_BOE_TRAIL_TBL.setStage(stage);
        bTVL_WKF_BOE_TRAIL_TBL.setAction(action);
        bTVL_WKF_BOE_TRAIL_TBL.setCommentHeader(commentHeader);
        bTVL_WKF_BOE_TRAIL_TBL.setActionBy("SCM");
        bTVL_WKF_BOE_TRAIL_TBL.setShipmentId(shipmentId);
        bTVL_WKF_BOE_TRAIL_TBL.setCommentLine(comment);
        bTVL_WKF_BOE_TRAIL_TBL.setPurgeFlag(0);
        bTVL_WKF_BOE_TRAIL_TBL.setCreatedby(olmId);
        bTVL_WKF_BOE_TRAIL_TBL.setCreationDate(LocalDateTime.now());
        bTVL_WKF_BOE_TRAIL_TBL.setModifiedby(olmId);
        bTVL_WKF_BOE_TRAIL_TBL.setModifiedDate(LocalDateTime.now());

        return bTVL_WKF_BOE_TRAIL_TBL;
    }

    private BTVL_WKF_BOE_SHIP_POC_TBL getWkfBoeShipPocObject(PointOfContactReq pointOfContactReq, BigDecimal shipmentId) {

        BTVL_WKF_BOE_SHIP_POC_TBL bTVL_WKF_BOE_SHIP_POC_TBL =
                this.btvlWkfBoeShipPocTblRepository.findByShipmentId(shipmentId);

        bTVL_WKF_BOE_SHIP_POC_TBL.setNameOfContactPerson(pointOfContactReq.getNameOfContactPerson());
        bTVL_WKF_BOE_SHIP_POC_TBL.setCountry(pointOfContactReq.getCountry());
        bTVL_WKF_BOE_SHIP_POC_TBL.setCity(pointOfContactReq.getCity());
        bTVL_WKF_BOE_SHIP_POC_TBL.setZipcode(pointOfContactReq.getZipcode());
        bTVL_WKF_BOE_SHIP_POC_TBL.setAddress(pointOfContactReq.getAddress());
        bTVL_WKF_BOE_SHIP_POC_TBL.setCountryMobCode(pointOfContactReq.getCountryMobCode());
        bTVL_WKF_BOE_SHIP_POC_TBL.setContactNumber(pointOfContactReq.getContactNumber());
        bTVL_WKF_BOE_SHIP_POC_TBL.setEmailId(pointOfContactReq.getEmailId());

        return bTVL_WKF_BOE_SHIP_POC_TBL;
    }

    private List<BTVL_WKF_BOE_SHIP_LINE_TBL> getWkfBoeShipLineListObject(List<ShipmentLineReq> shipmentLineReqList) {

        Map<BigDecimal, LineReq> lineReqMap =
                shipmentLineReqList.stream().flatMap(line -> line.getLineReqList().stream())
                        .collect(Collectors.toMap(LineReq::getShipmentLineId, Function.identity()));

        List<BigDecimal> lineIdList = new ArrayList<>(lineReqMap.keySet());

        List<BTVL_WKF_BOE_SHIP_LINE_TBL> wkfShipLineList = this.btvlWkfBoeShipLineTblRepository.findAllById(lineIdList);

        for (BTVL_WKF_BOE_SHIP_LINE_TBL bTVL_WKF_BOE_SHIP_LINE_TBL : wkfShipLineList) {
            LineReq matchingReq = lineReqMap.get(bTVL_WKF_BOE_SHIP_LINE_TBL.getShipmentLineId());

            if (matchingReq != null) {
                bTVL_WKF_BOE_SHIP_LINE_TBL.setCountryOfOrigin(matchingReq.getCountryOfOrigin());
                bTVL_WKF_BOE_SHIP_LINE_TBL.setIsFta(matchingReq.getIsFtaChecked());
                bTVL_WKF_BOE_SHIP_LINE_TBL.setIsWpcRequiremnt(matchingReq.getIsAntiDumpingChecked());
            }
        }

        return wkfShipLineList;
    }

    private List<BTVL_WKF_BOE_SHIP_DOC_TBL> getWkfBoeShipDocListObject(List<ShipmentDocReq> shipmentDocReqList,
                                                                       BigDecimal shipmentId, BigDecimal stage,
                                                                       String olmId) {

        //create docList to upload to UCM
        List<String> docsPathList = new ArrayList<>();
        shipmentDocReqList.forEach(doc -> docsPathList.add(doc.getContentId()));

        //upload docs to UCM
        List<DocumentResponse> documentResponseList = this.uploadDocument(docsPathList);

        //fetch all docs from db by shipmentId
        List<BTVL_WKF_BOE_SHIP_DOC_TBL> wkfShipDocList =
                this.btvlWkfBoeShipDocTblRepository.findByShipmentIdAndPurgeFlag(shipmentId, 0);

        //convert db doc list to map
        Map<BigDecimal, BTVL_WKF_BOE_SHIP_DOC_TBL> wkfShipDocMap = wkfShipDocList.stream()
                .collect(Collectors.toMap(BTVL_WKF_BOE_SHIP_DOC_TBL::getDocId, Function.identity()));

        //iterate request doc list
        List<BTVL_WKF_BOE_SHIP_DOC_TBL> processedRecords = shipmentDocReqList.stream().map(
                        shipmentDocReq -> {

                            // Find matching DocumentResponse based on contentId = fileName
                            Optional<DocumentResponse> matchingDocResponse = documentResponseList.stream()
                                    .filter(docResponse -> shipmentDocReq.getContentId().equals(docResponse.getFileName()))
                                    .findFirst();

                            if (matchingDocResponse.isPresent()) {
                                DocumentResponse docResponse = matchingDocResponse.get();
                                BigDecimal docId = shipmentDocReq.getDocId();

                                // Check if the record already exists in the db doc map`
                                BTVL_WKF_BOE_SHIP_DOC_TBL boeShipDocTbl = wkfShipDocMap.get(docId);

                                this.getWkfBoeShipDocObject(boeShipDocTbl, shipmentId,
                                        docId, docResponse, stage, olmId);

                                return boeShipDocTbl;

                            } else {
                                return null;
                            }

                        }
                ).filter(Objects::nonNull) // Filter out null entries
                .collect(Collectors.toList());

        return processedRecords;
    }

    private List<DocumentResponse> uploadDocument(List<String> docsPathList) {

        // upload Doc to UCM
        List<DocumentResponse> documentResponseList = DmsUtil.uploadDoc(docsPathList, this.uploadUcmUrl,
                this.httpServletResponse, this.httpServletRequest);

        if (documentResponseList != null && !documentResponseList.isEmpty()) {

            for (DocumentResponse documentResponse : documentResponseList) {
                if (documentResponse == null || StringUtils.isBlank(documentResponse.getContentId())
                        || !documentResponse.getContentId().startsWith("WC")) {
                    log.info("OfflineCcoTransactionService :: method :: uploadDocument :: doc uploaded to UCM :: Error");
                    throw new BoeException("Document upload fails", null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

        } else {
            throw new BoeException("Document upload fails", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return documentResponseList;
    }

    private BTVL_WKF_BOE_SHIP_DOC_TBL getWkfBoeShipDocObject(BTVL_WKF_BOE_SHIP_DOC_TBL bTVL_WKF_BOE_SHIP_DOC_TBL,
                                                             BigDecimal shipmentId, BigDecimal docId,
                                                             DocumentResponse docResponse, BigDecimal stage,
                                                             String olmId) {

        if (bTVL_WKF_BOE_SHIP_DOC_TBL == null) {

            bTVL_WKF_BOE_SHIP_DOC_TBL = new BTVL_WKF_BOE_SHIP_DOC_TBL();
            bTVL_WKF_BOE_SHIP_DOC_TBL.setShipmentId(shipmentId);
            bTVL_WKF_BOE_SHIP_DOC_TBL.setDocId(docId);
            bTVL_WKF_BOE_SHIP_DOC_TBL.setStage(stage);
            bTVL_WKF_BOE_SHIP_DOC_TBL.setPurgeFlag(0);
            bTVL_WKF_BOE_SHIP_DOC_TBL.setCreatedby(olmId);
            bTVL_WKF_BOE_SHIP_DOC_TBL.setCreationDate(LocalDateTime.now());
        }

        bTVL_WKF_BOE_SHIP_DOC_TBL.setAttribute(docResponse.getContentId());
        bTVL_WKF_BOE_SHIP_DOC_TBL.setDocUrl(docResponse.getDocUrl());
        bTVL_WKF_BOE_SHIP_DOC_TBL.setModifiedby(olmId);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setModifiedDate(LocalDateTime.now());

        return bTVL_WKF_BOE_SHIP_DOC_TBL;
    }

}
