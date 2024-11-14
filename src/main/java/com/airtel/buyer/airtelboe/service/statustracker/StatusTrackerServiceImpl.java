package com.airtel.buyer.airtelboe.service.statustracker;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.statustracker.ShipmentDoc;
import com.airtel.buyer.airtelboe.dto.statustracker.ShipmentInfo;
import com.airtel.buyer.airtelboe.dto.statustracker.ShipmentLines;
import com.airtel.buyer.airtelboe.dto.statustracker.StatusTracker;
import com.airtel.buyer.airtelboe.dto.statustracker.response.BoeStatusTrackerData;
import com.airtel.buyer.airtelboe.dto.statustracker.response.ResponseData;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIP_LINE_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIP_POC_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_TRAIL_TBL;
import com.airtel.buyer.airtelboe.repository.BoeTrail;
import com.airtel.buyer.airtelboe.repository.ShipDoc;
import com.airtel.buyer.airtelboe.repository.supplier.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class StatusTrackerServiceImpl implements StatusTrackerService {

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlWkfBoeShipLineTblRepository btvlWkfBoeShipLineTblRepository;

    @Autowired
    private BtvlWkfBoeShipPocTblRepository btvlWkfBoeShipPocTblRepository;

    @Autowired
    private BtvlWkfBoeShipDocTblRepository btvlWkfBoeShipDocTblRepository;

    @Autowired
    private BtvlWkfBoeTrailTblRepository btvlWkfBoeTrailTblRepository;

    @Override
    public BoeResponse<BoeStatusTrackerData> trackStatus(BigDecimal shipmentId) {
        BoeResponse boeResponse = new BoeResponse();
        BoeStatusTrackerData boeStatusTrackerData = new BoeStatusTrackerData();
        boeResponse.setData(boeStatusTrackerData);


        List<StatusTracker> statusList = this.generateTrail();
        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                this.btvlWkfBoeShipmentTblRepository.findByShipmentId(shipmentId);

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            ShipmentInfo shipmentInfo = (this.getFetchShipmentDataObject(bTVL_WKF_BOE_SHIPMENT_TBL));

            List<StatusTracker> trailList = this.fetchStatusTrail(shipmentId);
            this.processStatus(shipmentInfo.getStatus().intValue(), statusList, trailList, shipmentInfo);
            List<ResponseData> responseData = statusList.stream().map(this::setResponseData).collect(Collectors.toList());
            boeStatusTrackerData.setResponseData(responseData);

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + shipmentId);
            throw new BoeException("", null, HttpStatus.BAD_REQUEST);
        }


        return boeResponse;
    }


    private ShipmentInfo getFetchShipmentDataObject(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL) {

        ShipmentInfo fetchShipmentData = new ShipmentInfo();
        fetchShipmentData = this.getShipmentInfoObject(bTVL_WKF_BOE_SHIPMENT_TBL);
        fetchShipmentData.setShipmentLineInfoList(this.getShipmentLineInfoListObject(bTVL_WKF_BOE_SHIPMENT_TBL.getInvoiceNumbers(), bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId()));
        fetchShipmentData.setBTVL_WKF_BOE_SHIP_POC_TBL(this.getPointOfContactInfoObject(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId()));
        fetchShipmentData.setShipmentDocList(this.getShipmentDocListObject(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId()));
        fetchShipmentData.setShipmentTrailList(this.getShipmentTrailInfoObject(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId()));

        return fetchShipmentData;
    }

    private ShipmentInfo getShipmentInfoObject(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL) {

        ShipmentInfo shipmentInfo = new ShipmentInfo();

        shipmentInfo.setLOB(bTVL_WKF_BOE_SHIPMENT_TBL.getLob());
        shipmentInfo.setShipmentCourierDate(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentCourierDate());
        shipmentInfo.setShipmentCourierRefNo(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentCourierRefNo());
        shipmentInfo.setOutOfChargeDate(bTVL_WKF_BOE_SHIPMENT_TBL.getOutOfChargeDate());
        shipmentInfo.setIncoTermCategory(bTVL_WKF_BOE_SHIPMENT_TBL.getIncoTermCategory());
        shipmentInfo.setOrgId(bTVL_WKF_BOE_SHIPMENT_TBL.getOrgId());
        shipmentInfo.setVendorName(bTVL_WKF_BOE_SHIPMENT_TBL.getVendorName());
        shipmentInfo.setUtrNo(bTVL_WKF_BOE_SHIPMENT_TBL.getUtrNo());
        shipmentInfo.setStatus(bTVL_WKF_BOE_SHIPMENT_TBL.getStatus());
        shipmentInfo.setStage(bTVL_WKF_BOE_SHIPMENT_TBL.getStage());
        shipmentInfo.setShipmentMode(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentMode());
        shipmentInfo.setShipmentId(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId());
        shipmentInfo.setShipmentArrivalDate(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentArrivalDate());
        shipmentInfo.setPurgeFlag(bTVL_WKF_BOE_SHIPMENT_TBL.getPurgeFlag());
        shipmentInfo.setProtestDescription(bTVL_WKF_BOE_SHIPMENT_TBL.getProtestDescription());
        shipmentInfo.setProtestDataErrReason(bTVL_WKF_BOE_SHIPMENT_TBL.getProtestDataErrReason());
        shipmentInfo.setPortLoading(bTVL_WKF_BOE_SHIPMENT_TBL.getPortLoading());
        shipmentInfo.setPoNo(bTVL_WKF_BOE_SHIPMENT_TBL.getPoNo());
        shipmentInfo.setPoHeaderId(bTVL_WKF_BOE_SHIPMENT_TBL.getPoHeaderId());
        shipmentInfo.setPaymentDate(bTVL_WKF_BOE_SHIPMENT_TBL.getPaymentDate());
        shipmentInfo.setPartnerVendorId(bTVL_WKF_BOE_SHIPMENT_TBL.getPartnerVendorId());
        shipmentInfo.setPartnerVendorCode(bTVL_WKF_BOE_SHIPMENT_TBL.getPartnerVendorCode());
        shipmentInfo.setOuName(bTVL_WKF_BOE_SHIPMENT_TBL.getOuName());
        shipmentInfo.setNoOfPackages(bTVL_WKF_BOE_SHIPMENT_TBL.getNoOfPackages());
        shipmentInfo.setMrrNo(bTVL_WKF_BOE_SHIPMENT_TBL.getMrrNo());
        shipmentInfo.setModifiedDate(bTVL_WKF_BOE_SHIPMENT_TBL.getModifiedDate());
        shipmentInfo.setModifiedby(bTVL_WKF_BOE_SHIPMENT_TBL.getModifiedby());
        shipmentInfo.setLeName(bTVL_WKF_BOE_SHIPMENT_TBL.getLeName());
        shipmentInfo.setIsProtestedDataErr(bTVL_WKF_BOE_SHIPMENT_TBL.getIsProtestedDataErr());
        shipmentInfo.setInvoiceNumbers(bTVL_WKF_BOE_SHIPMENT_TBL.getInvoiceNumbers());
        shipmentInfo.setInsuranceAmtCurrency(bTVL_WKF_BOE_SHIPMENT_TBL.getInsuranceAmtCurrency());
        shipmentInfo.setEfChargesAmt(bTVL_WKF_BOE_SHIPMENT_TBL.getEfChargesAmt());
        shipmentInfo.setInsuranceAmt(bTVL_WKF_BOE_SHIPMENT_TBL.getInsuranceAmt());
        shipmentInfo.setIncoTerm(bTVL_WKF_BOE_SHIPMENT_TBL.getIncoTerm());
        shipmentInfo.setGrossShipmentWeightUom(bTVL_WKF_BOE_SHIPMENT_TBL.getGrossShipmentWeightUom());
        shipmentInfo.setGrossShipmentWeight(bTVL_WKF_BOE_SHIPMENT_TBL.getGrossShipmentWeight());
        shipmentInfo.setGrossShipmentVolumeUom(bTVL_WKF_BOE_SHIPMENT_TBL.getGrossShipmentVolumeUom());
        shipmentInfo.setGrossShipmentVolume(bTVL_WKF_BOE_SHIPMENT_TBL.getGrossShipmentVolume());
        shipmentInfo.setFreightAmt(bTVL_WKF_BOE_SHIPMENT_TBL.getFreightAmt());
        shipmentInfo.setFreightAmtCurrency(bTVL_WKF_BOE_SHIPMENT_TBL.getFreightAmtCurrency());
        shipmentInfo.setFlag5(bTVL_WKF_BOE_SHIPMENT_TBL.getFlag5());
        shipmentInfo.setFlag4(bTVL_WKF_BOE_SHIPMENT_TBL.getFlag4());
        shipmentInfo.setFlag3(bTVL_WKF_BOE_SHIPMENT_TBL.getFlag3());
        shipmentInfo.setFlag2(bTVL_WKF_BOE_SHIPMENT_TBL.getFlag2());
        shipmentInfo.setFlag1(bTVL_WKF_BOE_SHIPMENT_TBL.getFlag1());
        shipmentInfo.setExpectedArrivalDate(bTVL_WKF_BOE_SHIPMENT_TBL.getExpectedArrivalDate());
        shipmentInfo.setDestinationPort(bTVL_WKF_BOE_SHIPMENT_TBL.getDestinationPort());
        shipmentInfo.setDestinationCountry(bTVL_WKF_BOE_SHIPMENT_TBL.getDestinationCountry());
        shipmentInfo.setCreationDate(bTVL_WKF_BOE_SHIPMENT_TBL.getCreationDate());
        shipmentInfo.setCreatedby(bTVL_WKF_BOE_SHIPMENT_TBL.getCreatedby());
        shipmentInfo.setCountryLoading(bTVL_WKF_BOE_SHIPMENT_TBL.getCountryLoading());
        shipmentInfo.setChaAssigmentDate(bTVL_WKF_BOE_SHIPMENT_TBL.getChaAssigmentDate());
        shipmentInfo.setBucketNo(bTVL_WKF_BOE_SHIPMENT_TBL.getBucketNo());
        shipmentInfo.setBoeNo(bTVL_WKF_BOE_SHIPMENT_TBL.getBoeNo());
        shipmentInfo.setBoeDate(bTVL_WKF_BOE_SHIPMENT_TBL.getBoeDate());
        shipmentInfo.setAwbBol(bTVL_WKF_BOE_SHIPMENT_TBL.getAwbBol());
        shipmentInfo.setAttribute1(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute1());
        shipmentInfo.setAttribute2(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute2());
        shipmentInfo.setAttribute3(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute3());
        shipmentInfo.setAttribute4(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute4());
        shipmentInfo.setAttribute5(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute5());
        shipmentInfo.setAttribute6(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute6());
        shipmentInfo.setAttribute7(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute7());
        shipmentInfo.setAttribute8(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute8());
        shipmentInfo.setAttribute9(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute9());
        shipmentInfo.setAttribute10(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute10());
        shipmentInfo.setAttribute11(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute11());
        shipmentInfo.setAttribute12(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute12());
        shipmentInfo.setAttribute13(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute13());
        shipmentInfo.setAttribute14(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute14());
        shipmentInfo.setAttribute15(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute15());
        shipmentInfo.setAssignedChaId(bTVL_WKF_BOE_SHIPMENT_TBL.getAssignedChaId());
        return shipmentInfo;
    }

    private List<ShipmentLines> getShipmentLineInfoListObject(String invoiceNumbers, BigDecimal shipmentId) {

        List<ShipmentLines> shipmentLineInfoList = null;

        String[] invoiceNumbersArr = invoiceNumbers.split(",");

        List<BTVL_WKF_BOE_SHIP_LINE_TBL> shipmentLineList =
                this.btvlWkfBoeShipLineTblRepository.findByShipmentId(shipmentId);

        if (shipmentLineList != null && !shipmentLineList.isEmpty() &&
                invoiceNumbersArr != null && invoiceNumbersArr.length > 0) {
            shipmentLineInfoList = Stream.of(invoiceNumbersArr).map(invoiceNo -> this.getShipmentLineInfoObject(invoiceNo, shipmentLineList)).collect(Collectors.toList());
        }

        return shipmentLineInfoList;
    }

    private ShipmentLines getShipmentLineInfoObject(String invoiceNo, List<BTVL_WKF_BOE_SHIP_LINE_TBL> shipmentLineList) {

        ShipmentLines shipmentLineInfo = null;

        List<BTVL_WKF_BOE_SHIP_LINE_TBL> filteredList = shipmentLineList.stream().filter(line -> invoiceNo.equalsIgnoreCase(line.getInvoiceNumber())).collect(Collectors.toList());

        if (filteredList != null && !filteredList.isEmpty()) {
            shipmentLineInfo = new ShipmentLines();
            shipmentLineInfo.setInvoiceNumber(invoiceNo);
            shipmentLineInfo.setInvoiceHeaderId(filteredList.get(0).getInvoiceHeaderId());
            shipmentLineInfo.setLines(this.getLineInfoListObject(filteredList));
        }

        return shipmentLineInfo;
    }

    private List<BTVL_WKF_BOE_SHIP_LINE_TBL> getLineInfoListObject(List<BTVL_WKF_BOE_SHIP_LINE_TBL> shipmentLineList) {

        List<BTVL_WKF_BOE_SHIP_LINE_TBL> lineInfoList = null;

        if (shipmentLineList != null && !shipmentLineList.isEmpty()) {
            lineInfoList = shipmentLineList.stream().map(this::getLineInfo).collect(Collectors.toList());
        }

        return lineInfoList;
    }

    private BTVL_WKF_BOE_SHIP_LINE_TBL getLineInfo(BTVL_WKF_BOE_SHIP_LINE_TBL bTVL_WKF_BOE_SHIP_LINE_TBL_data) {

        BTVL_WKF_BOE_SHIP_LINE_TBL bTVL_WKF_BOE_SHIP_LINE_TBL = new BTVL_WKF_BOE_SHIP_LINE_TBL();
        bTVL_WKF_BOE_SHIP_LINE_TBL.setShipmentLineId(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getShipmentLineId());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setShipmentId(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getShipmentId());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setInvoiceHeaderId(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getInvoiceHeaderId());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setInvoiceLineId(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getInvoiceLineId());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setCountryOfOrigin(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getCountryOfOrigin());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setIsFta(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getIsFta());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setIsAntiDumping(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getIsAntiDumping());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setIsWpcRequiremnt(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getIsWpcRequiremnt());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute1(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute1());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute2(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute2());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute3(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute3());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute4(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute4());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute5(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute5());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute6(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute6());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute7(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute7());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute8(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute8());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute9(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute9());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute10(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute10());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute11(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute11());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute12(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute12());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute13(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute13());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute14(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute14());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setAttribute15(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getAttribute15());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setPurgeFlag(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getPurgeFlag());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setCreationDate(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getCreationDate());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setCreatedby(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getCreatedby());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setModifiedDate(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getModifiedDate());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setModifiedby(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getModifiedby());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setInvoiceNumber(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getInvoiceNumber());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setItemCode(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getItemCode());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setItemDescription(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getItemDescription());
        bTVL_WKF_BOE_SHIP_LINE_TBL.setHsnCode(bTVL_WKF_BOE_SHIP_LINE_TBL_data.getHsnCode());
//        bTVL_WKF_BOE_SHIP_LINE_TBL.setIsFtaChecked(bTVL_WKF_BOE_SHIP_LINE_TBL_data.get);
//        bTVL_WKF_BOE_SHIP_LINE_TBL.setIsAntiDumpingChecked(bTVL_WKF_BOE_SHIP_LINE_TBL_data.get);
//        bTVL_WKF_BOE_SHIP_LINE_TBL.setIsWpcChecked(bTVL_WKF_BOE_SHIP_LINE_TBL_data.get);

        return bTVL_WKF_BOE_SHIP_LINE_TBL;
    }


    private BTVL_WKF_BOE_SHIP_POC_TBL getPointOfContactInfoObject(BigDecimal shipmentId) {

//        PointOfContactInfo pointOfContactInfo = null;

        BTVL_WKF_BOE_SHIP_POC_TBL bTVL_WKF_BOE_SHIP_POC_TBL = this.btvlWkfBoeShipPocTblRepository.findByShipmentId(shipmentId);

//        if (bTVL_WKF_BOE_SHIP_POC_TBL != null) {
//            pointOfContactInfo = new PointOfContactInfo();
//            pointOfContactInfo.setNameOfContactPerson(bTVL_WKF_BOE_SHIP_POC_TBL.getNameOfContactPerson());
//            pointOfContactInfo.setCountry(bTVL_WKF_BOE_SHIP_POC_TBL.getCountry());
//            pointOfContactInfo.setCity(bTVL_WKF_BOE_SHIP_POC_TBL.getCity());
//            pointOfContactInfo.setZipcode(bTVL_WKF_BOE_SHIP_POC_TBL.getZipcode());
//            pointOfContactInfo.setAddress(bTVL_WKF_BOE_SHIP_POC_TBL.getAddress());
//            pointOfContactInfo.setCountryMobCode(bTVL_WKF_BOE_SHIP_POC_TBL.getCountryMobCode());
//            pointOfContactInfo.setContactNumber(bTVL_WKF_BOE_SHIP_POC_TBL.getContactNumber());
//            pointOfContactInfo.setEmailId(bTVL_WKF_BOE_SHIP_POC_TBL.getEmailId());
//        }

        return bTVL_WKF_BOE_SHIP_POC_TBL;
    }

    private List<ShipmentDoc> getShipmentDocListObject(BigDecimal shipmentId) {

        List<ShipmentDoc> shipmentDocList = null;

        List<ShipDoc> list = this.btvlWkfBoeShipDocTblRepository.fetchShipmentDocByShipmentId(shipmentId);

        if (list != null && !list.isEmpty()) {
            shipmentDocList = list.stream().map(this::getShipmentDocObject).collect(Collectors.toList());
        }

        return shipmentDocList;
    }

    private ShipmentDoc getShipmentDocObject(ShipDoc shipDoc) {

        ShipmentDoc shipmentDoc = new ShipmentDoc();
        shipmentDoc.setDocName(shipDoc.getDocName());
        shipmentDoc.setContentId(shipDoc.getContentId());

        return shipmentDoc;
    }

    private List<BTVL_WKF_BOE_TRAIL_TBL> getShipmentTrailInfoObject(BigDecimal shipmentId) {
        List<BTVL_WKF_BOE_TRAIL_TBL> shipmentTrailInfoList = null;


        List<BTVL_WKF_BOE_TRAIL_TBL> bTVL_WKF_BOE_TRAIL_TBL_list = this.btvlWkfBoeTrailTblRepository.findByShipmentId(shipmentId);
        if (bTVL_WKF_BOE_TRAIL_TBL_list != null && !bTVL_WKF_BOE_TRAIL_TBL_list.isEmpty()) {
            shipmentTrailInfoList = bTVL_WKF_BOE_TRAIL_TBL_list.stream().map(this::getShipmentTrailObject).collect(Collectors.toList());

        }
        return shipmentTrailInfoList;
    }

    private BTVL_WKF_BOE_TRAIL_TBL getShipmentTrailObject(BTVL_WKF_BOE_TRAIL_TBL btvlWkfBoeTrailTbl) {

        BTVL_WKF_BOE_TRAIL_TBL shipmentTrailInfo = new BTVL_WKF_BOE_TRAIL_TBL();
        shipmentTrailInfo.setTrailId(btvlWkfBoeTrailTbl.getTrailId());
        shipmentTrailInfo.setShipmentId(btvlWkfBoeTrailTbl.getShipmentId());
        shipmentTrailInfo.setStage(btvlWkfBoeTrailTbl.getStage());
        shipmentTrailInfo.setBucketNo(btvlWkfBoeTrailTbl.getBucketNo());
        shipmentTrailInfo.setCommentHeader(btvlWkfBoeTrailTbl.getCommentHeader());
        shipmentTrailInfo.setCommentLine(btvlWkfBoeTrailTbl.getCommentLine());
        shipmentTrailInfo.setAction(btvlWkfBoeTrailTbl.getAction());
        shipmentTrailInfo.setActionBy(btvlWkfBoeTrailTbl.getActionBy());
        shipmentTrailInfo.setCreatedby(btvlWkfBoeTrailTbl.getCreatedby());
        shipmentTrailInfo.setCreationDate(btvlWkfBoeTrailTbl.getCreationDate());
        shipmentTrailInfo.setModifiedby(btvlWkfBoeTrailTbl.getModifiedby());
        shipmentTrailInfo.setModifiedDate(btvlWkfBoeTrailTbl.getModifiedDate());
        return shipmentTrailInfo;
    }


    public static List<StatusTracker> generateTrail() {
        List<StatusTracker> statusList = new ArrayList<>();

        //        1       INVOICE SUBMISSION ON PORTAL
        StatusTracker statusTracker = new StatusTracker();

        try {
            statusTracker.setActionBy("Partner");
            statusTracker.setRefDescription("INVOICE SUBMISSION ON PORTAL");
            statusTracker.setStatus(new BigDecimal(1));
            statusList.add(statusTracker);

            //        2       SHIPPING INFO SUBMISSION
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("Partner");
            statusTracker.setRefDescription("SHIPPING INFO SUBMISSION");
            statusTracker.setStatus(new BigDecimal(2));
            statusList.add(statusTracker);

            //        3       INSURANCE AND FREIGHT INFO BY SCM
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("SCM");
            statusTracker.setRefDescription("INSURANCE AND FREIGHT INFO BY SCM");
            statusTracker.setStatus(new BigDecimal(3));
            statusList.add(statusTracker);

            //        4       CHA ASSIGNMENT
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("SCM");
            statusTracker.setRefDescription("CHA ASSIGNMENT");
            statusTracker.setStatus(new BigDecimal(4));
            statusList.add(statusTracker);

            //        5       CHECKLIST FILLING BY CHA
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("CHA");
            statusTracker.setRefDescription("CHECKLIST FILLING BY CHA");
            statusTracker.setStatus(new BigDecimal(5));
            statusList.add(statusTracker);

            //        6       CHECK LIST APPROVAL
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("SCM");
            statusTracker.setRefDescription("CHECK LIST APPROVAL");
            statusTracker.setStatus(new BigDecimal(6));
            statusList.add(statusTracker);

            //        7       BOE COPY UPLOAD BY CHA
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("CHA");
            statusTracker.setRefDescription("BOE COPY UPLOAD BY CHA");
            statusTracker.setStatus(new BigDecimal(7));
            statusList.add(statusTracker);

            //        14       CCO ASSIGNMENT
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("CHA");
            statusTracker.setRefDescription("CCO ASSIGNMENT");
            statusTracker.setStatus(new BigDecimal(14));
            statusList.add(statusTracker);

            //        15       CCO APPROVAL
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("CCO");
            statusTracker.setRefDescription("CCO APPROVAL");
            statusTracker.setStatus(new BigDecimal(15));
            statusList.add(statusTracker);


            //        8       IP TEAM APPROVAL
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("IP Team");
            statusTracker.setRefDescription("IP TEAM APPROVAL");
            statusTracker.setStatus(new BigDecimal(8));
            statusList.add(statusTracker);

            //        9       BOE INVOICE CREATION IN ERP
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("SCM");
            statusTracker.setRefDescription("BOE INVOICE CREATION IN ERP");
            statusTracker.setStatus(new BigDecimal(9));
            statusList.add(statusTracker);

            //        10      PAYMENT
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("Finance");
            statusTracker.setRefDescription("PAYMENT");
            statusTracker.setStatus(new BigDecimal(10));
            statusList.add(statusTracker);

            //        11      OUT OF CHARGE
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("CHA");
            statusTracker.setRefDescription("OUT OF CHARGE");
            statusTracker.setStatus(new BigDecimal(11));
            statusList.add(statusTracker);

            //        12      SHIPMENT DOCUMENT SUBMISSION
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("CHA");
            statusTracker.setRefDescription("SHIPMENT DOCUMENT SUBMISSION");
            statusTracker.setStatus(new BigDecimal(12));
            statusList.add(statusTracker);

            //        13      RECEIPT CREATION
            statusTracker = new StatusTracker();
            statusTracker.setActionBy("Ware House");
            statusTracker.setRefDescription("RECEIPT CREATION");
            statusTracker.setStatus(new BigDecimal(13));
            statusList.add(statusTracker);


        } catch (Exception e) {
            log.info("Exception raised  :: " + e.getMessage());
            e.printStackTrace();
        }

        return statusList;
    }

    public List<StatusTracker> fetchStatusTrail(BigDecimal shipmentId) {
        List<StatusTracker> statusList = null;

        List<BoeTrail> boeTrailList = this.btvlWkfBoeTrailTblRepository.fetchByShipmentId(shipmentId);

        log.info("records from trail list " + boeTrailList);
        if (boeTrailList != null && !boeTrailList.isEmpty()) {
            statusList = boeTrailList.stream().map(this::getBoeTrailObject).collect(Collectors.toList());

        }

        return statusList;
    }

    private StatusTracker getBoeTrailObject(BoeTrail boeTrail) {
        log.info("setting objects from boetrail table" + boeTrail.getTrail_id());
        StatusTracker statusTracker = new StatusTracker();
        statusTracker.setTrailId(boeTrail.getTrail_id());
        statusTracker.setShipmentId(boeTrail.getShipment_id());
        statusTracker.setAction(boeTrail.getAction());
        statusTracker.setRefDescription(boeTrail.getRef_description());
        statusTracker.setStatus(boeTrail.getStatus());
        statusTracker.setActionBy(boeTrail.getAction_by());
        statusTracker.setActionDate(boeTrail.getAction_date());
        statusTracker.setStage(boeTrail.getStage());
        statusTracker.setBucketNo(boeTrail.getBucket_no());
        statusTracker.setCommentHeader(boeTrail.getComment_header());
        statusTracker.setCommentLine(boeTrail.getComment_line());
        statusTracker.setInvoiceSubmissionDate(boeTrail.getInvoice_submission_date());
        statusTracker.setChaCode(boeTrail.getCHA_CODE());
        statusTracker.setAttribute1(boeTrail.getAttribute1());
        statusTracker.setAttribute2(boeTrail.getAttribute2());
        statusTracker.setAssignedCcoId(boeTrail.getASSIGNED_CCO_ID());

        return statusTracker;
    }

    public void processStatus(int shipmentStatus, List<StatusTracker> sList, List<StatusTracker> tList, ShipmentInfo shipmentInfo) {

        try {
            List<StatusTracker> fL = null;

            //            for (int i = 0; i < 13; i++) {
            //                if ((i + 1) <= shipmentStatus) {
            //                    this.populateStatusFor(i, shipmentStatus, sList, tList);
            //                }
            //            }


            if (shipmentStatus <= 7) {
                for (int i = 0; i < 7; i++) {
                    if ((i + 1) <= shipmentStatus) {
                        this.populateStatusFor(i, shipmentStatus, sList, tList, shipmentInfo);
                    }
                }
            } else if (shipmentStatus == 14) {
                for (int i = 0; i < 7; i++) {
                    if ((i + 1) <= shipmentStatus) {
                        this.populateStatusFor(i, shipmentStatus, sList, tList, shipmentInfo);
                    }
                }
                this.populateStatusFor(13, shipmentStatus, sList, tList, shipmentInfo);
            } else if (shipmentStatus == 15) {
                for (int i = 0; i < 7; i++) {
                    if ((i + 1) <= shipmentStatus) {
                        this.populateStatusFor(i, shipmentStatus, sList, tList, shipmentInfo);
                    }
                }
                this.populateStatusFor(13, shipmentStatus, sList, tList, shipmentInfo);
                this.populateStatusFor(14, shipmentStatus, sList, tList, shipmentInfo);
            } else {
                for (int i = 0; i < 13; i++) {
                    if ((i + 1) <= shipmentStatus) {
                        this.populateStatusFor(i, shipmentStatus, sList, tList, shipmentInfo);
                    }
                }
                this.populateStatusFor(13, shipmentStatus, sList, tList, shipmentInfo);
                this.populateStatusFor(14, shipmentStatus, sList, tList, shipmentInfo);
            }


        } catch (Exception e) {
            log.info("Exception raised  :: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void populateStatusFor(int i, int sStatus, List<StatusTracker> sList, List<StatusTracker> tList, ShipmentInfo shipmentInfo) {
        // filter tList for current statuses,
        // get last records - Action Date
        log.info("Populating trail for : i : " + i + ": shipmentStatus :" + sStatus);
        try {
            List<StatusTracker> fL = tList.stream()
                    .filter(tm -> tm.getStatus().intValue() == (i + 1))
                    .collect(Collectors.toList());


            switch (i + 1) {

                case 2:
                    //        1   INVOICE SUBMISSION ON PORTAL
                    //        2       SHIPPING INFO SUBMISSION
                    // set invoice submission date in Action date for element 1 in sList
                    sList.get(0).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getInvoiceSubmissionDate() : null);
                    sList.get(0)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getInvoiceSubmissionDate()) ?
                                    "COMPLETED" : null);
                    sList.get(0).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);

                    sList.get(1).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getActionDate() : null);
                    sList.get(1)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ?
                                    "COMPLETED" : null);
                    sList.get(1).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 3:
                    //        3       INSURANCE AND FREIGHT INFO BY SCM
                    sList.get(2)
                            .setActionDate((shipmentInfo
                                    .getIncoTerm()
                                    .contains("E") || shipmentInfo
                                    .getIncoTerm()
                                    .contains("F")) && fL.size() > 0 ?
                                    fL.get(fL.size() - 1).getActionDate() : "NA");
                    sList.get(2)
                            .setAction((shipmentInfo
                                    .getIncoTerm()
                                    .contains("E") || shipmentInfo
                                    .getIncoTerm()
                                    .contains("F")) && fL.size() > 0 &&
                                    !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ? "COMPLETED" : "NA");
                    sList.get(2).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 4:
                    //        4       CHA ASSIGNMENT
                    sList.get(3).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getActionDate() : null);
                    sList.get(3)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ?
                                    "Assignment Failed".equalsIgnoreCase(fL.get(fL.size() - 1).getChaCode()) ? "FAILED" :
                                            "COMPLETED" : null);
                    sList.get(3).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 5:
                    //        5       CHECKLIST FILLING BY CHA
                    sList.get(4).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getActionDate() : null);
                    sList.get(4)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ?
                                    "COMPLETED" : null);
                    sList.get(4).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 6:
                    //        6       CHECK LIST APPROVAL
                    sList.get(5)
                            .setActionDate(shipmentInfo.getIsProtestedDataErr() == new BigDecimal(2) && fL.size() > 0 ?
                                    fL.get(fL.size() - 1).getActionDate() : "NA");
                    sList.get(5)
                            .setAction(shipmentInfo.getIsProtestedDataErr() == new BigDecimal(2) && fL.size() > 0 &&
                                    !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ? "COMPLETED" : "NA");
                    sList.get(5).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 7:
                    //        7       BOE COPY UPLOAD BY CHA
                    sList.get(6).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getActionDate() : null);
                    sList.get(6)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ?
                                    "COMPLETED" : null);
                    sList.get(6).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 14:
                    //        14       CCO ASSIGNMENT
                    sList.get(7).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getActionDate() : null);
                    sList.get(7)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ?
                                    "CCO ASSIGNMENT FAILED".equalsIgnoreCase(fL.get(fL.size() - 1).getAssignedCcoId()) ?
                                            "FAILED" : "COMPLETED" : null);
                    sList.get(7).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 15:
                    //        15       CCO APPROVAL
                    sList.get(8).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getActionDate() : null);
                    sList.get(8)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ?
                                    "APPROVE".equalsIgnoreCase(fL.get(fL.size() - 1).getAction()) ? "COMPLETED" :
                                            "REJECT".equalsIgnoreCase(fL.get(fL.size() - 1).getAction()) ? "REJECTED" : "HOLD" :
                                    null);
                    sList.get(8).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 8:
                    //        8       IP TEAM APPROVAL
                    sList.get(9).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getActionDate() : null);
                    sList.get(9)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ?
                                    "COMPLETED" : null);
                    sList.get(9).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 9:
                    //        9       BOE INVOICE CREATION IN ERP
                    sList.get(10).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getActionDate() : null);
                    sList.get(10)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ?
                                    "COMPLETED" : null);
                    sList.get(10).setAttribute2(fL.size() > 0 ? fL.get(fL.size() - 1).getAttribute2() : null);
                    sList.get(10).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 10:
                    //        10      PAYMENT
                    sList.get(11).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getAttribute1() : null);
                    sList.get(11)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getAttribute1()) ?
                                    "COMPLETED" : null);
                    sList.get(11).setAttribute2(fL.size() > 0 ? fL.get(fL.size() - 1).getAttribute2() : null);
                    sList.get(11).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 11:
                    //        11      OUT OF CHARGE
                    sList.get(12).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getActionDate() : null);
                    sList.get(12)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ?
                                    "COMPLETED" : null);
                    sList.get(12).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 12:
                    //        12      SHIPMENT DOCUMENT SUBMISSION
                    sList.get(13).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getActionDate() : null);
                    sList.get(13)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ?
                                    "COMPLETED" : null);
                    sList.get(13).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
                case 13:
                    //        13      RECEIPT CREATION
                    sList.get(14).setActionDate(fL.size() > 0 ? fL.get(fL.size() - 1).getActionDate() : null);
                    sList.get(14)
                            .setAction(fL.size() > 0 && !StringUtils.isBlank(fL.get(fL.size() - 1).getActionDate()) ?
                                    "COMPLETED" : null);
                    sList.get(15).setCommentHeader(fL.size() > 0 ? fL.get(fL.size() - 1).getCommentHeader() : null);
                    break;
            }
        } catch (Exception e) {
            log.info("Exception raised  :: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private ResponseData setResponseData(StatusTracker statusTracker) {

        ResponseData responseData = new ResponseData();
        responseData.setAction(statusTracker.getAction());
        responseData.setActionBy(statusTracker.getActionBy());
        responseData.setRefDescription(statusTracker.getRefDescription());
        responseData.setActionDate(statusTracker.getActionDate());
        responseData.setCommentHeader(statusTracker.getCommentHeader());
        responseData.setAttribute2(statusTracker.getAttribute2());


        return responseData;
    }


}
