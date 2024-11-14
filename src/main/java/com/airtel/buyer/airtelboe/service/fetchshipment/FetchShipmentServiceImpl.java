package com.airtel.buyer.airtelboe.service.fetchshipment;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.fetchshipment.response.*;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.*;
import com.airtel.buyer.airtelboe.repository.ShipDoc;
import com.airtel.buyer.airtelboe.repository.supplier.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class FetchShipmentServiceImpl implements FetchShipmentService {

    @Autowired
    private BtvlMstBoePortTblRepository btvlMstBoePortTblRepository;

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlWkfBoeShipLineTblRepository btvlWkfBoeShipLineTblRepository;

    @Autowired
    private BtvlWkfBoeShipPocTblRepository btvlWkfBoeShipPocTblRepository;

    @Autowired
    private BtvlWkfBoeShipDocTblRepository btvlWkfBoeShipDocTblRepository;

    @Autowired
    private BtvlWkfBoeChkHTblRepository btvlWkfBoeChkHTblRepository;

    @Autowired
    private BtvlWkfInvoiceHeaderTblRepository btvlWkfInvoiceHeaderTblRepository;

    @Override
    public BoeResponse<FetchShipmentData> shipmentInformation(BigDecimal shipmentId) {

        BoeResponse<FetchShipmentData> boeResponse = new BoeResponse<>();

        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                this.btvlWkfBoeShipmentTblRepository.findByShipmentId(shipmentId);

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            boeResponse.setData(this.getFetchShipmentDataObject(bTVL_WKF_BOE_SHIPMENT_TBL));

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + shipmentId);
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

        return boeResponse;
    }

    private FetchShipmentData getFetchShipmentDataObject(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL) {

        FetchShipmentData fetchShipmentData = new FetchShipmentData();
        fetchShipmentData.setShipmentInfo(this.getShipmentInfoObject(bTVL_WKF_BOE_SHIPMENT_TBL));
        fetchShipmentData.setShipmentLineInfoList(this.getShipmentLineInfoListObject(bTVL_WKF_BOE_SHIPMENT_TBL.getInvoiceNumbers(), bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId()));
        fetchShipmentData.setPointOfContactInfo(this.getPointOfContactInfoObject(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId()));
        fetchShipmentData.setShipmentDocList(this.getShipmentDocListObject(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId()));
        fetchShipmentData.setCheckListHeaderInfo(this.getChecklistHeaderObject(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId()));
        return fetchShipmentData;
    }

    private ShipmentInfo getShipmentInfoObject(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL) {

        ShipmentInfo shipmentInfo = new ShipmentInfo();
        shipmentInfo.setPoNo(bTVL_WKF_BOE_SHIPMENT_TBL.getPoNo());
        shipmentInfo.setDestinationPort(bTVL_WKF_BOE_SHIPMENT_TBL.getDestinationPort());
        shipmentInfo.setIncoTerm(bTVL_WKF_BOE_SHIPMENT_TBL.getIncoTerm());
        shipmentInfo.setShipmentMode(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentMode());
        shipmentInfo.setDestinationCountry(bTVL_WKF_BOE_SHIPMENT_TBL.getDestinationCountry());
        shipmentInfo.setInvoiceNumbers(bTVL_WKF_BOE_SHIPMENT_TBL.getInvoiceNumbers());
        shipmentInfo.setCountryLoading(bTVL_WKF_BOE_SHIPMENT_TBL.getCountryLoading());
        shipmentInfo.setPortLoading(bTVL_WKF_BOE_SHIPMENT_TBL.getPortLoading());
        shipmentInfo.setGrossShipmentWeight(bTVL_WKF_BOE_SHIPMENT_TBL.getGrossShipmentWeight());
        shipmentInfo.setGrossShipmentVolume(bTVL_WKF_BOE_SHIPMENT_TBL.getGrossShipmentVolume());
        shipmentInfo.setExpectedArrivalDate(bTVL_WKF_BOE_SHIPMENT_TBL.getExpectedArrivalDate());
        shipmentInfo.setNoOfPackages(bTVL_WKF_BOE_SHIPMENT_TBL.getNoOfPackages());
        shipmentInfo.setAwbBol(bTVL_WKF_BOE_SHIPMENT_TBL.getAwbBol());
        shipmentInfo.setFreightAmtCurrency(bTVL_WKF_BOE_SHIPMENT_TBL.getFreightAmtCurrency());
        shipmentInfo.setFreightAmt(bTVL_WKF_BOE_SHIPMENT_TBL.getFreightAmt());
        shipmentInfo.setInsuranceAmtCurrency(bTVL_WKF_BOE_SHIPMENT_TBL.getInsuranceAmtCurrency());
        shipmentInfo.setInsuranceAmt(bTVL_WKF_BOE_SHIPMENT_TBL.getInsuranceAmt());
        shipmentInfo.setEfChargesAmt(bTVL_WKF_BOE_SHIPMENT_TBL.getEfChargesAmt());
        shipmentInfo.setBoeNo(bTVL_WKF_BOE_SHIPMENT_TBL.getBoeNo());
        shipmentInfo.setBoeDate(bTVL_WKF_BOE_SHIPMENT_TBL.getBoeDate());
        shipmentInfo.setShipmentArrivalDate(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentArrivalDate());

        shipmentInfo.setStage(bTVL_WKF_BOE_SHIPMENT_TBL.getStage());
        shipmentInfo.setStatus(bTVL_WKF_BOE_SHIPMENT_TBL.getStatus());
        shipmentInfo.setBucketNo(bTVL_WKF_BOE_SHIPMENT_TBL.getBucketNo());
        String portType = getPortType(bTVL_WKF_BOE_SHIPMENT_TBL.getDestinationPort());
        shipmentInfo.setPortType(portType);
        // below data added for stage 5 - BOE Copy Upload by CHA
        shipmentInfo.setClearanceType(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute1());
        shipmentInfo.setIsProtestedDataErr(bTVL_WKF_BOE_SHIPMENT_TBL.getIsProtestedDataErr());
        shipmentInfo.setProtestDataErrReason(bTVL_WKF_BOE_SHIPMENT_TBL.getProtestDataErrReason());
        // below data added for stage 6 - Out of Charge by CHA
        shipmentInfo.setOutOfChargeDate(bTVL_WKF_BOE_SHIPMENT_TBL.getOutOfChargeDate());
        shipmentInfo.setShipmentCourierDate(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentCourierDate());
        shipmentInfo.setShipmentCourierRefNo(bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentCourierRefNo());
        // view protested boe
        shipmentInfo.setProtestDescription(bTVL_WKF_BOE_SHIPMENT_TBL.getProtestDescription());

        return shipmentInfo;
    }

    private List<ShipmentLineInfo> getShipmentLineInfoListObject(String invoiceNumbers, BigDecimal shipmentId) {

        List<ShipmentLineInfo> shipmentLineInfoList = null;

        String[] invoiceNumbersArr = invoiceNumbers.split(",");

        List<BTVL_WKF_BOE_SHIP_LINE_TBL> shipmentLineList =
                this.btvlWkfBoeShipLineTblRepository.findByShipmentId(shipmentId);

        if (shipmentLineList != null && !shipmentLineList.isEmpty() &&
                invoiceNumbersArr != null && invoiceNumbersArr.length > 0) {

            shipmentLineInfoList = Stream.of(invoiceNumbersArr).
                    map(invoiceNo -> this.getShipmentLineInfoObject(invoiceNo, shipmentLineList)).collect(Collectors.toList());

            List<BigDecimal> invHeaderIds = shipmentLineInfoList.stream().map(sli -> sli.getInvoiceHeaderId()).collect(Collectors.toList());
            // for each invoice numbers, update invoiceId, odType in shipmentLineInfoList
            List<BTVL_WKF_INVOICE_HEADER_TBL> invoiceList = btvlWkfInvoiceHeaderTblRepository.findByInvoiceHeaderIdIn(invHeaderIds);
            shipmentLineInfoList = updateInvoiceDetailsInShipmentLineInfo(shipmentLineInfoList, invoiceList);

        }
        return shipmentLineInfoList;
    }

    private ShipmentLineInfo getShipmentLineInfoObject(String invoiceNo, List<BTVL_WKF_BOE_SHIP_LINE_TBL> shipmentLineList) {

        ShipmentLineInfo shipmentLineInfo = null;

        List<BTVL_WKF_BOE_SHIP_LINE_TBL> filteredList = shipmentLineList.stream().filter(line -> invoiceNo.equalsIgnoreCase(line.getInvoiceNumber())).collect(Collectors.toList());

        if (filteredList != null && !filteredList.isEmpty()) {
            shipmentLineInfo = new ShipmentLineInfo();
            shipmentLineInfo.setInvoiceNumber(invoiceNo);
            shipmentLineInfo.setInvoiceHeaderId(filteredList.get(0).getInvoiceHeaderId());
            shipmentLineInfo.setLineInfoList(this.getLineInfoListObject(filteredList));
        }

        return shipmentLineInfo;
    }

    private List<ShipmentLineInfo> updateInvoiceDetailsInShipmentLineInfo(List<ShipmentLineInfo> shipmentLineInfoList, List<BTVL_WKF_INVOICE_HEADER_TBL> invoiceList) {

        if(shipmentLineInfoList!=null && !shipmentLineInfoList.isEmpty()){
            shipmentLineInfoList.forEach(shipmentLineInfo -> {
                List<BTVL_WKF_INVOICE_HEADER_TBL> filteredList = invoiceList.stream().filter(i -> i.getInvoiceHeaderId().longValue() == shipmentLineInfo.getInvoiceHeaderId().longValue()).collect(Collectors.toList());

                if(filteredList != null && !filteredList.isEmpty()){
                    shipmentLineInfo.setInvoiceId(String.valueOf(filteredList.get(0).getInvoiceId()));
                    shipmentLineInfo.setOdType(String.valueOf(filteredList.get(0).getOdType()));
                }
            });
        }
        return shipmentLineInfoList;
    }

    private List<LineInfo> getLineInfoListObject(List<BTVL_WKF_BOE_SHIP_LINE_TBL> shipmentLineList) {

        List<LineInfo> lineInfoList = null;

        if (shipmentLineList != null && !shipmentLineList.isEmpty()) {
            lineInfoList = shipmentLineList.stream().map(this::getLineInfo).collect(Collectors.toList());
        }

        return lineInfoList;
    }

    private LineInfo getLineInfo(BTVL_WKF_BOE_SHIP_LINE_TBL bTVL_WKF_BOE_SHIP_LINE_TBL) {

        LineInfo lineInfo = new LineInfo();
        lineInfo.setShipmentLineId(bTVL_WKF_BOE_SHIP_LINE_TBL.getShipmentLineId());
        lineInfo.setInvoiceLineId(bTVL_WKF_BOE_SHIP_LINE_TBL.getInvoiceLineId());
        lineInfo.setItemCode(bTVL_WKF_BOE_SHIP_LINE_TBL.getItemCode());
        lineInfo.setItemDescription(bTVL_WKF_BOE_SHIP_LINE_TBL.getItemDescription());
        lineInfo.setHsnCode(bTVL_WKF_BOE_SHIP_LINE_TBL.getHsnCode());
        lineInfo.setCountryOfOrigin(bTVL_WKF_BOE_SHIP_LINE_TBL.getCountryOfOrigin());
        lineInfo.setIsFtaChecked(bTVL_WKF_BOE_SHIP_LINE_TBL.getIsFta());
        lineInfo.setIsAntiDumpingChecked(bTVL_WKF_BOE_SHIP_LINE_TBL.getIsAntiDumping());
        lineInfo.setIsWpcChecked(bTVL_WKF_BOE_SHIP_LINE_TBL.getIsWpcRequiremnt());

        return lineInfo;
    }

    private PointOfContactInfo getPointOfContactInfoObject(BigDecimal shipmentId) {

        PointOfContactInfo pointOfContactInfo = null;

        BTVL_WKF_BOE_SHIP_POC_TBL bTVL_WKF_BOE_SHIP_POC_TBL = this.btvlWkfBoeShipPocTblRepository.findByShipmentId(shipmentId);

        if (bTVL_WKF_BOE_SHIP_POC_TBL != null) {
            pointOfContactInfo = new PointOfContactInfo();
            pointOfContactInfo.setNameOfContactPerson(bTVL_WKF_BOE_SHIP_POC_TBL.getNameOfContactPerson());
            pointOfContactInfo.setCountry(bTVL_WKF_BOE_SHIP_POC_TBL.getCountry());
            pointOfContactInfo.setCity(bTVL_WKF_BOE_SHIP_POC_TBL.getCity());
            pointOfContactInfo.setZipcode(bTVL_WKF_BOE_SHIP_POC_TBL.getZipcode());
            pointOfContactInfo.setAddress(bTVL_WKF_BOE_SHIP_POC_TBL.getAddress());
            pointOfContactInfo.setCountryMobCode(bTVL_WKF_BOE_SHIP_POC_TBL.getCountryMobCode());
            pointOfContactInfo.setContactNumber(bTVL_WKF_BOE_SHIP_POC_TBL.getContactNumber());
            pointOfContactInfo.setEmailId(bTVL_WKF_BOE_SHIP_POC_TBL.getEmailId());
        }

        return pointOfContactInfo;
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
        shipmentDoc.setDocId(shipmentDoc.getDocId());
        shipmentDoc.setContentId(shipDoc.getContentId());

        return shipmentDoc;
    }


    private String getPortType(String portCode) {

        if (StringUtils.isBlank(portCode))
            return null;

        Optional<List<BTVL_MST_BOE_PORT_TBL>> listOfPorts = Optional.ofNullable(btvlMstBoePortTblRepository.findByPortCodeIgnoreCase(portCode));

        if (listOfPorts.isPresent()) {
            return listOfPorts.get().get(0).getPortType();
        } else
            return null;
    }

    private CheckListHeaderInfo getChecklistHeaderObject(BigDecimal shipmentId) {

        CheckListHeaderInfo checkListHeaderInfo = null;

        BTVL_WKF_BOE_CHK_H_TBL btvl_wkf_boe_chk_h_tbl = btvlWkfBoeChkHTblRepository.findByShipmentIdAndPurgeFlag(shipmentId, 0);
        if (btvl_wkf_boe_chk_h_tbl != null) {
            checkListHeaderInfo = new CheckListHeaderInfo();
            checkListHeaderInfo.setDemurrageAmt(btvl_wkf_boe_chk_h_tbl.getDemurrageAmt());
            checkListHeaderInfo.setInterestPenaltyAmt(btvl_wkf_boe_chk_h_tbl.getInterestPenaltyAmt());
            checkListHeaderInfo.setShiRotationDate(btvl_wkf_boe_chk_h_tbl.getShiRotationDate());
        }

        return checkListHeaderInfo;
    }
}
