package com.airtel.buyer.airtelboe.service.scmrfiresponse;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.scmrfiresponse.request.*;
import com.airtel.buyer.airtelboe.dto.scmrfiresponse.response.*;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_PORT_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoePortTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipmentTblRepository;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Slf4j
@Service
public class ScmRfiResponseServiceImpl implements ScmRfiResponseService {

    @Autowired
    private BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    private BtvlMstBoePortTblRepository btvlMstBoePortTblRepository;

    @Autowired
    private ScmRfiResponseTransactionService scmRfiResponseTransactionService;

    @Value("${app.node}")
    public String appNode;

    @Override
    public BoeResponse<ScmRfiResponseData> rfiResponse(ScmRfiResponseReq scmRfiResponseReq, UserDetailsImpl userDetails) {

        BoeResponse<ScmRfiResponseData> boeResponse = new BoeResponse<>();
        boeResponse.setData(this.getScmRfiResponseDataObject(scmRfiResponseReq));

        if (this.basicFieldsCheck(scmRfiResponseReq, boeResponse)) {

            BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL =
                    this.btvlWkfBoeShipmentTblRepository.findByShipmentId(scmRfiResponseReq.getShipmentId());

//            if (this.checkOtherFields(scmRfiResponseReq, boeResponse, bTVL_WKF_BOE_SHIPMENT_TBL)) {
//                this.scmRfiResponseTransactionService.rfi(scmRfiResponseReq, userDetails, bTVL_WKF_BOE_SHIPMENT_TBL);
//            }
            if (!StringUtils.isBlank(scmRfiResponseReq.getFlag()) && scmRfiResponseReq.getFlag().equalsIgnoreCase("next")) {
                this.checkOtherFields(scmRfiResponseReq, boeResponse, bTVL_WKF_BOE_SHIPMENT_TBL);
            } else if (!StringUtils.isBlank(scmRfiResponseReq.getFlag()) && scmRfiResponseReq.getFlag().equalsIgnoreCase("submit")) {
                if (this.checkOtherFields(scmRfiResponseReq, boeResponse, bTVL_WKF_BOE_SHIPMENT_TBL)) {
                    this.scmRfiResponseTransactionService.rfi(scmRfiResponseReq, userDetails, bTVL_WKF_BOE_SHIPMENT_TBL);
                }
            }

        }

        return boeResponse;
    }

    private ScmRfiResponseData getScmRfiResponseDataObject(ScmRfiResponseReq scmRfiResponseReq) {

        ScmRfiResponseData scmRfiResponseData = new ScmRfiResponseData();
        scmRfiResponseData.setRfiComment(scmRfiResponseReq.getRfiComment());
        scmRfiResponseData.setShipmentId(scmRfiResponseReq.getShipmentId());
        scmRfiResponseData.setShipmentRes(this.getShipmentResObject(scmRfiResponseReq.getShipmentReq()));
        scmRfiResponseData.setShipmentLineResList(this.getShipmentLineResListObject(scmRfiResponseReq.getShipmentLineReqList()));
        scmRfiResponseData.setPointOfContactRes(this.getPointOfContactResObject(scmRfiResponseReq.getPointOfContactReq()));
        scmRfiResponseData.setShipmentDocResList(this.getShipmentDocResListObject(scmRfiResponseReq.getShipmentDocReqList()));

        return scmRfiResponseData;
    }

    private ShipmentRes getShipmentResObject(ShipmentReq shipmentReq) {

        ShipmentRes shipmentRes = new ShipmentRes();
//        shipmentRes.setShipmentId(shipmentReq.getShipmentId());
        shipmentRes.setShipmentMode(shipmentReq.getShipmentMode());
        shipmentRes.setCountryLoading(shipmentReq.getCountryLoading());
        shipmentRes.setPortLoading(shipmentReq.getPortLoading());
        shipmentRes.setDestinationCountry(shipmentReq.getDestinationCountry());
        shipmentRes.setDestinationPort(shipmentReq.getDestinationPort());
        shipmentRes.setGrossShipmentWeight(shipmentReq.getGrossShipmentWeight());
        shipmentRes.setGrossShipmentVolume(shipmentReq.getGrossShipmentVolume());
        shipmentRes.setExpectedArrivalDate(shipmentReq.getExpectedArrivalDate());
        shipmentRes.setNoOfPackages(shipmentReq.getNoOfPackages());
        shipmentRes.setAwbBol(shipmentReq.getAwbBol());
        shipmentRes.setFreightAmtCurrency(shipmentReq.getFreightAmtCurrency());
        shipmentRes.setFreightAmt(shipmentReq.getFreightAmt());
        shipmentRes.setInsuranceAmtCurrency(shipmentReq.getInsuranceAmtCurrency());
        shipmentRes.setInsuranceAmt(shipmentReq.getInsuranceAmt());
        shipmentRes.setEfChargesAmt(shipmentReq.getEfChargesAmt());
        shipmentRes.setBoeNo(shipmentReq.getBoeNo());
        shipmentRes.setBoeDate(shipmentReq.getBoeDate());
        shipmentRes.setShipmentArrivalDate(shipmentReq.getShipmentArrivalDate());

        return shipmentRes;
    }

    private List<ShipmentLineRes> getShipmentLineResListObject(List<ShipmentLineReq> shipmentLineReqList) {

        List<ShipmentLineRes> shipmentLineResList = null;

        if (shipmentLineReqList != null && !shipmentLineReqList.isEmpty()) {
            shipmentLineResList = shipmentLineReqList.stream().map(this::getShipmentLineResObject).collect(Collectors.toList());
        }

        return shipmentLineResList;
    }

    private ShipmentLineRes getShipmentLineResObject(ShipmentLineReq shipmentLineReq) {

        ShipmentLineRes shipmentLineRes = new ShipmentLineRes();
        shipmentLineRes.setInvoiceNumber(shipmentLineReq.getInvoiceNumber());
        shipmentLineRes.setInvoiceHeaderId(shipmentLineReq.getInvoiceHeaderId());
        shipmentLineRes.setLineResList(this.getLineResListObject(shipmentLineReq.getLineReqList()));

        return shipmentLineRes;
    }

    private List<LineRes> getLineResListObject(List<LineReq> lineReqList) {

        List<LineRes> lineResList = null;

        if (lineReqList != null && !lineReqList.isEmpty()) {
            lineResList = lineReqList.stream().map(this::getLineResObject).collect(Collectors.toList());
        }

        return lineResList;
    }

    private LineRes getLineResObject(LineReq lineReq) {

        LineRes lineRes = new LineRes();
        lineRes.setShipmentLineId(lineReq.getShipmentLineId());
//        lineRes.setItemCode(lineReq.getItemCode());
//        lineRes.setItemDescription(lineReq.getItemDescription());
//        lineRes.setHsnCode(lineReq.getHsnCode());
        lineRes.setCountryOfOrigin(lineReq.getCountryOfOrigin());
        lineRes.setIsFtaChecked(lineReq.getIsFtaChecked());
        lineRes.setIsAntiDumpingChecked(lineReq.getIsAntiDumpingChecked());
//        lineRes.setIsWpcChecked(lineReq.getIsWpcChecked());

        return lineRes;
    }

    private PointOfContactRes getPointOfContactResObject(PointOfContactReq pointOfContactReq) {

        PointOfContactRes pointOfContactRes = new PointOfContactRes();
//        pointOfContactRes.setShipmentPocId(pointOfContactReq.getShipmentPocId());
        pointOfContactRes.setNameOfContactPerson(pointOfContactReq.getNameOfContactPerson());
        pointOfContactRes.setCountry(pointOfContactReq.getCountry());
        pointOfContactRes.setCity(pointOfContactReq.getCity());
        pointOfContactRes.setZipcode(pointOfContactReq.getZipcode());
        pointOfContactRes.setAddress(pointOfContactReq.getAddress());
        pointOfContactRes.setCountryMobCode(pointOfContactReq.getCountryMobCode());
        pointOfContactRes.setContactNumber(pointOfContactReq.getContactNumber());
        pointOfContactRes.setEmailId(pointOfContactReq.getEmailId());

        return pointOfContactRes;
    }

    private List<ShipmentDocRes> getShipmentDocResListObject(List<ShipmentDocReq> shipmentDocReqList) {

        List<ShipmentDocRes> shipmentDocResList = null;

        if (shipmentDocReqList != null && !shipmentDocReqList.isEmpty()) {
            shipmentDocResList = shipmentDocReqList.stream().map(this::getShipmentDocResObject).collect(Collectors.toList());
        }

        return shipmentDocResList;
    }

    private ShipmentDocRes getShipmentDocResObject(ShipmentDocReq shipmentDocReq) {

        ShipmentDocRes shipmentDocRes = new ShipmentDocRes();
        shipmentDocRes.setDocName(shipmentDocReq.getDocName());
        shipmentDocRes.setDocId(shipmentDocReq.getDocId());
        shipmentDocRes.setContentId(shipmentDocReq.getContentId());

        return shipmentDocRes;
    }

    private Boolean basicFieldsCheck(ScmRfiResponseReq scmRfiResponseReq,
                                     BoeResponse<ScmRfiResponseData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        ScmRfiResponseData scmRfiResponseData = boeResponse.getData();

        if (StringUtils.isBlank(scmRfiResponseReq.getRfiComment())) {
            isValidationPassed = Boolean.FALSE;
            scmRfiResponseData.setRfiCommentErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

//        if (scmRfiResponseReq.getShipmentReq() == null) {
//            isValidationPassed = Boolean.FALSE;
//            scmRfiResponseData.setShipmentResErrMsg(CommonConstants.FIELD_IS_MANDATORY);
//
//        } else if (scmRfiResponseReq.getShipmentReq().getShipmentId() == null ||
//                StringUtils.isBlank(scmRfiResponseReq.getShipmentReq().getShipmentId().toString())) {
//            isValidationPassed = Boolean.FALSE;
//            scmRfiResponseData.getShipmentRes().setShipmentIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
//        }

        if (scmRfiResponseReq.getShipmentId() == null ||
                StringUtils.isBlank(scmRfiResponseReq.getShipmentId().toString())) {
            isValidationPassed = Boolean.FALSE;
            scmRfiResponseData.setShipmentIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("CcoServiceImpl :: method :: basicFieldsCheck :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse);
        }

        return isValidationPassed;
    }

    private Boolean checkOtherFields(ScmRfiResponseReq scmRfiResponseReq,
                                     BoeResponse<ScmRfiResponseData> boeResponse,
                                     BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL) {

        if (bTVL_WKF_BOE_SHIPMENT_TBL != null) {

            if (this.isValidAction(bTVL_WKF_BOE_SHIPMENT_TBL)) {

                return this.checkMandatoryFields(scmRfiResponseReq, boeResponse, bTVL_WKF_BOE_SHIPMENT_TBL);

            } else {
                log.info("Not a valid RFI response action for Shipment Id :: " + scmRfiResponseReq.getShipmentId());
                throw new BoeException("No record found", null, HttpStatus.UNPROCESSABLE_ENTITY);
            }

        } else {
            log.info("No record found in BTVL_WKF_BOE_SHIPMENT_TBL for Shipment Id :: " + scmRfiResponseReq.getShipmentId());
            throw new BoeException("No record found", null, HttpStatus.BAD_REQUEST);
        }

    }

    private Boolean isValidAction(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL) {

        Boolean isValid = Boolean.FALSE;

        Integer dbBucketNo = bTVL_WKF_BOE_SHIPMENT_TBL.getBucketNo().intValue();

        if (dbBucketNo == 13) {
            isValid = Boolean.TRUE;
        }

        log.info("ScmRfiResponseServiceImpl :: method :: isValidAction :: " + bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId() + " :: " + isValid);
        return isValid;
    }

    private Boolean checkMandatoryFields(ScmRfiResponseReq scmRfiResponseReq,
                                         BoeResponse<ScmRfiResponseData> boeResponse,
                                         BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL) {

        if ("DDP".equalsIgnoreCase(bTVL_WKF_BOE_SHIPMENT_TBL.getIncoTerm())) {
            // DDP
            return this.checkMandatoryFieldsForDDP(scmRfiResponseReq, boeResponse);

        } else {

            List<BTVL_MST_BOE_PORT_TBL> portsList = this.btvlMstBoePortTblRepository.
                    findByPortCodeAndPurgeFlagAllIgnoreCase(bTVL_WKF_BOE_SHIPMENT_TBL.getDestinationPort(), 0);

            String destinationPortType = portsList.get(0).getPortType();

            if (!StringUtils.isBlank(destinationPortType) &&
                    (destinationPortType.contains("FTWZ") || destinationPortType.contains("SEZ"))) {
                // Non DDP and FTWZ or SEZ
                return this.checkMandatoryFieldsForFtwzAndEnFAndCnD(scmRfiResponseReq, boeResponse);

            } else {
                // non ftwz / sez
                if (bTVL_WKF_BOE_SHIPMENT_TBL.getIncoTerm().startsWith("E") ||
                        bTVL_WKF_BOE_SHIPMENT_TBL.getIncoTerm().startsWith("F")) {
                    // E & F Terms
                    return this.checkMandatoryFieldsForNonFtwzAndEnF(scmRfiResponseReq, boeResponse);

                } else if (bTVL_WKF_BOE_SHIPMENT_TBL.getIncoTerm().startsWith("C") ||
                        bTVL_WKF_BOE_SHIPMENT_TBL.getIncoTerm().startsWith("D")) {
                    // C & D Terms
                    return this.checkMandatoryFieldsForNonFtwzAndCnD(scmRfiResponseReq, boeResponse);

                }
            }
        }

        return Boolean.FALSE;
    }

    private Boolean checkMandatoryFieldsForNonFtwzAndEnF(ScmRfiResponseReq scmRfiResponseReq,
                                                         BoeResponse<ScmRfiResponseData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        ShipmentReq shipmentReq = scmRfiResponseReq.getShipmentReq();

        ShipmentRes shipmentRes = boeResponse.getData().getShipmentRes();

        if (shipmentReq.getGrossShipmentWeight() == null ||
                StringUtils.isBlank(shipmentReq.getGrossShipmentWeight().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setGrossShipmentWeightErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getGrossShipmentVolume() == null ||
                StringUtils.isBlank(shipmentReq.getGrossShipmentVolume().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setGrossShipmentVolumeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getNoOfPackages() == null ||
                StringUtils.isBlank(shipmentReq.getNoOfPackages().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setNoOfPackagesErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (!this.checkPocMandatoryFields(scmRfiResponseReq, boeResponse)) {
            isValidationPassed = Boolean.FALSE;
        }

        if (StringUtils.isBlank(shipmentReq.getShipmentMode())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setShipmentModeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getCountryLoading())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setCountryLoadingErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getPortLoading())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setPortLoadingErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getDestinationCountry())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setDestinationCountryErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getDestinationPort())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setDestinationPortErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getExpectedArrivalDate() == null ||
                StringUtils.isBlank(shipmentReq.getExpectedArrivalDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setExpectedArrivalDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getAwbBol())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setAwbBolErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getFreightAmtCurrency())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setFreightAmtCurrencyErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getFreightAmt() == null ||
                StringUtils.isBlank(shipmentReq.getFreightAmt().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setFreightAmtErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getInsuranceAmtCurrency())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setInsuranceAmtCurrencyErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getShipmentArrivalDate() == null ||
                StringUtils.isBlank(shipmentReq.getShipmentArrivalDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setShipmentArrivalDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (!this.checkFtaMandatoryFields(scmRfiResponseReq, boeResponse)) {
            isValidationPassed = Boolean.FALSE;
        }

        if (scmRfiResponseReq.getShipmentDocReqList() != null &&
                !scmRfiResponseReq.getShipmentDocReqList().isEmpty()) {

            if (!this.checkDocMandatoryFields(scmRfiResponseReq, boeResponse)) {
                isValidationPassed = Boolean.FALSE;
            }
        }

        log.info("CcoServiceImpl :: method :: checkMandatoryFieldsForNonFtwzAndEnF :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse);
        }

        return isValidationPassed;
    }

    private Boolean checkPocMandatoryFields(ScmRfiResponseReq scmRfiResponseReq,
                                            BoeResponse<ScmRfiResponseData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        PointOfContactReq pointOfContactReq = scmRfiResponseReq.getPointOfContactReq();

        PointOfContactRes pointOfContactRes = boeResponse.getData().getPointOfContactRes();

        if (StringUtils.isBlank(pointOfContactReq.getNameOfContactPerson())) {
            isValidationPassed = Boolean.FALSE;
            pointOfContactRes.setNameOfContactPersonErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(pointOfContactReq.getCountry())) {
            isValidationPassed = Boolean.FALSE;
            pointOfContactRes.setCountryErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(pointOfContactReq.getCity())) {
            isValidationPassed = Boolean.FALSE;
            pointOfContactRes.setCityErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(pointOfContactReq.getZipcode())) {
            isValidationPassed = Boolean.FALSE;
            pointOfContactRes.setZipcodeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(pointOfContactReq.getAddress())) {
            isValidationPassed = Boolean.FALSE;
            pointOfContactRes.setAddressErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(pointOfContactReq.getCountryMobCode())) {
            isValidationPassed = Boolean.FALSE;
            pointOfContactRes.setCountryMobCodeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(pointOfContactReq.getContactNumber())) {
            isValidationPassed = Boolean.FALSE;
            pointOfContactRes.setContactNumberErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(pointOfContactReq.getEmailId())) {
            isValidationPassed = Boolean.FALSE;
            pointOfContactRes.setEmailIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        log.info("CcoServiceImpl :: method :: checkPocMandatoryFields :: " + isValidationPassed);

        return isValidationPassed;
    }

    private Boolean checkFtaMandatoryFields(ScmRfiResponseReq scmRfiResponseReq,
                                            BoeResponse<ScmRfiResponseData> boeResponse) {

        AtomicReference<Boolean> isValidationPassed = new AtomicReference<>(Boolean.TRUE);

        List<LineReq> lineReqList = scmRfiResponseReq.getShipmentLineReqList().stream().
                flatMap(req -> req.getLineReqList().stream()).collect(Collectors.toList());

        List<LineRes> lineResList = boeResponse.getData().getShipmentLineResList().stream().
                flatMap(res -> res.getLineResList().stream()).collect(Collectors.toList());

        IntStream.range(0, lineReqList.size()).forEach(i -> {
                    LineReq lineReq = lineReqList.get(i);
                    LineRes lineRes = lineResList.get(i);

                    if (!this.validateLineReq(lineReq, lineRes)) {
                        isValidationPassed.set(Boolean.FALSE);
                    }
                }
        );

        log.info("CcoServiceImpl :: method :: checkFtaMandatoryFields :: " + isValidationPassed);

        return isValidationPassed.get();
    }

    private Boolean validateLineReq(LineReq lineReq, LineRes lineRes) {

        Boolean isValidationPassed = Boolean.TRUE;

        if (lineReq.getShipmentLineId() == null ||
                StringUtils.isBlank(lineReq.getShipmentLineId().toString())) {
            isValidationPassed = Boolean.FALSE;
            lineRes.setShipmentLineIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(lineReq.getCountryOfOrigin())) {
            isValidationPassed = Boolean.FALSE;
            lineRes.setCountryOfOriginErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(lineReq.getIsFtaChecked())) {
            isValidationPassed = Boolean.FALSE;
            lineRes.setIsFtaCheckedErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(lineReq.getIsAntiDumpingChecked())) {
            isValidationPassed = Boolean.FALSE;
            lineRes.setIsAntiDumpingCheckedErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        return isValidationPassed;
    }

    private Boolean checkDocMandatoryFields(ScmRfiResponseReq scmRfiResponseReq,
                                            BoeResponse<ScmRfiResponseData> boeResponse) {

        AtomicReference<Boolean> isValidationPassed = new AtomicReference<>(Boolean.TRUE);

        List<ShipmentDocReq> shipmentDocReqList = scmRfiResponseReq.getShipmentDocReqList();

        List<ShipmentDocRes> shipmentDocResList = boeResponse.getData().getShipmentDocResList();

        IntStream.range(0, shipmentDocReqList.size()).forEach(i -> {
                    ShipmentDocReq shipmentDocReq = shipmentDocReqList.get(i);
                    ShipmentDocRes shipmentDocRes = shipmentDocResList.get(i);

                    if (!this.validateDocReq(shipmentDocReq, shipmentDocRes)) {
                        isValidationPassed.set(Boolean.FALSE);
                    }
                }
        );

        log.info("CcoServiceImpl :: method :: checkDocMandatoryFields :: " + isValidationPassed);

        return isValidationPassed.get();
    }

    private Boolean validateDocReq(ShipmentDocReq shipmentDocReq, ShipmentDocRes shipmentDocRes) {

        Boolean isValidationPassed = Boolean.TRUE;

        if (shipmentDocReq.getDocId() == null ||
                StringUtils.isBlank(shipmentDocReq.getDocId().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentDocRes.setDocIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentDocReq.getDocName())) {
            isValidationPassed = Boolean.FALSE;
            shipmentDocRes.setDocNameErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentDocReq.getContentId())) {
            isValidationPassed = Boolean.FALSE;
            shipmentDocRes.setContentIdErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        return isValidationPassed;
    }

    private Boolean checkMandatoryFieldsForDDP(ScmRfiResponseReq scmRfiResponseReq,
                                               BoeResponse<ScmRfiResponseData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        ShipmentReq shipmentReq = scmRfiResponseReq.getShipmentReq();

        ShipmentRes shipmentRes = boeResponse.getData().getShipmentRes();

        if (shipmentReq.getGrossShipmentWeight() == null ||
                StringUtils.isBlank(shipmentReq.getGrossShipmentWeight().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setGrossShipmentWeightErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getGrossShipmentVolume() == null ||
                StringUtils.isBlank(shipmentReq.getGrossShipmentVolume().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setGrossShipmentVolumeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getNoOfPackages() == null ||
                StringUtils.isBlank(shipmentReq.getNoOfPackages().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setNoOfPackagesErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getBoeNo())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setBoeNoErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getBoeDate() == null ||
                StringUtils.isBlank(shipmentReq.getBoeDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setBoeDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getShipmentArrivalDate() == null ||
                StringUtils.isBlank(shipmentReq.getShipmentArrivalDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setShipmentArrivalDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (scmRfiResponseReq.getShipmentDocReqList() != null &&
                !scmRfiResponseReq.getShipmentDocReqList().isEmpty()) {

            if (!this.checkDocMandatoryFields(scmRfiResponseReq, boeResponse)) {
                isValidationPassed = Boolean.FALSE;
            }
        }

        log.info("CcoServiceImpl :: method :: checkMandatoryFieldsForDDP :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse);
        }

        return isValidationPassed;
    }

    private Boolean checkMandatoryFieldsForFtwzAndEnFAndCnD(ScmRfiResponseReq scmRfiResponseReq,
                                                            BoeResponse<ScmRfiResponseData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        ShipmentReq shipmentReq = scmRfiResponseReq.getShipmentReq();

        ShipmentRes shipmentRes = boeResponse.getData().getShipmentRes();

        if (StringUtils.isBlank(shipmentReq.getCountryLoading())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setCountryLoadingErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getPortLoading())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setPortLoadingErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getGrossShipmentWeight() == null ||
                StringUtils.isBlank(shipmentReq.getGrossShipmentWeight().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setGrossShipmentWeightErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getGrossShipmentVolume() == null ||
                StringUtils.isBlank(shipmentReq.getGrossShipmentVolume().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setGrossShipmentVolumeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getExpectedArrivalDate() == null ||
                StringUtils.isBlank(shipmentReq.getExpectedArrivalDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setExpectedArrivalDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getNoOfPackages() == null ||
                StringUtils.isBlank(shipmentReq.getNoOfPackages().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setNoOfPackagesErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (!this.checkFtaMandatoryFields(scmRfiResponseReq, boeResponse)) {
            isValidationPassed = Boolean.FALSE;
        }

        if (scmRfiResponseReq.getShipmentDocReqList() != null &&
                !scmRfiResponseReq.getShipmentDocReqList().isEmpty()) {

            if (!this.checkDocMandatoryFields(scmRfiResponseReq, boeResponse)) {
                isValidationPassed = Boolean.FALSE;
            }
        }

        log.info("CcoServiceImpl :: method :: checkMandatoryFieldsForFtwzAndEnFAndCnD :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse);
        }

        return isValidationPassed;
    }

    private Boolean checkMandatoryFieldsForNonFtwzAndCnD(ScmRfiResponseReq scmRfiResponseReq,
                                                         BoeResponse<ScmRfiResponseData> boeResponse) {

        Boolean isValidationPassed = Boolean.TRUE;

        ShipmentReq shipmentReq = scmRfiResponseReq.getShipmentReq();

        ShipmentRes shipmentRes = boeResponse.getData().getShipmentRes();

        if (StringUtils.isBlank(shipmentReq.getShipmentMode())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setShipmentModeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getCountryLoading())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setCountryLoadingErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getPortLoading())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setPortLoadingErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getGrossShipmentWeight() == null ||
                StringUtils.isBlank(shipmentReq.getGrossShipmentWeight().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setGrossShipmentWeightErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getGrossShipmentVolume() == null ||
                StringUtils.isBlank(shipmentReq.getGrossShipmentVolume().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setGrossShipmentVolumeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getExpectedArrivalDate() == null ||
                StringUtils.isBlank(shipmentReq.getExpectedArrivalDate().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setExpectedArrivalDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getNoOfPackages() == null ||
                StringUtils.isBlank(shipmentReq.getNoOfPackages().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setNoOfPackagesErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getAwbBol())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setAwbBolErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getFreightAmtCurrency())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setFreightAmtCurrencyErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getFreightAmt() == null ||
                StringUtils.isBlank(shipmentReq.getFreightAmt().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setFreightAmtErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getEfChargesAmt() == null ||
                StringUtils.isBlank(shipmentReq.getEfChargesAmt().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setEfChargesAmtErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (StringUtils.isBlank(shipmentReq.getInsuranceAmtCurrency())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setInsuranceAmtCurrencyErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (shipmentReq.getInsuranceAmt() == null ||
                StringUtils.isBlank(shipmentReq.getInsuranceAmt().toString())) {
            isValidationPassed = Boolean.FALSE;
            shipmentRes.setInsuranceAmtErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (!this.checkFtaMandatoryFields(scmRfiResponseReq, boeResponse)) {
            isValidationPassed = Boolean.FALSE;
        }

        if (scmRfiResponseReq.getShipmentDocReqList() != null &&
                !scmRfiResponseReq.getShipmentDocReqList().isEmpty()) {

            if (!this.checkDocMandatoryFields(scmRfiResponseReq, boeResponse)) {
                isValidationPassed = Boolean.FALSE;
            }
        }

        log.info("CcoServiceImpl :: method :: checkMandatoryFieldsForNonFtwzAndCnD :: " + isValidationPassed);

        if (!isValidationPassed) {
            this.setErrorObject(boeResponse);
        }

        return isValidationPassed;
    }

    private void setErrorObject(BoeResponse<ScmRfiResponseData> boeResponse) {
        com.airtel.buyer.airtelboe.dto.common.Error error = new Error();
        error.setCode(CommonConstants.SCM_RFI_RESPONSE_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }


}
