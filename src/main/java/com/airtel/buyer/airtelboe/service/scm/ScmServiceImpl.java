package com.airtel.buyer.airtelboe.service.scm;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.dto.document.response.DocumentResponse;
import com.airtel.buyer.airtelboe.dto.scm.stage2.request.SubmitStage2Request;
import com.airtel.buyer.airtelboe.dto.scm.stage2.response.Fta;
import com.airtel.buyer.airtelboe.dto.scm.stage2.response.LineInfo;
import com.airtel.buyer.airtelboe.dto.scm.stage2.response.ShipmentDoc;
import com.airtel.buyer.airtelboe.dto.scm.stage2.response.SubmitStage2Response;
import com.airtel.buyer.airtelboe.dto.scm.stage4.request.ScmStage4RejectRequest;
import com.airtel.buyer.airtelboe.dto.scm.stage4.request.SubmitStage4Request;
import com.airtel.buyer.airtelboe.dto.scm.stage4.response.ScmStage4RejectResponse;
import com.airtel.buyer.airtelboe.dto.scm.stage4.response.SubmitStage4Response;
import com.airtel.buyer.airtelboe.exception.BoeException;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIP_DOC_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIP_LINE_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_TRAIL_TBL;
import com.airtel.buyer.airtelboe.repository.ChaAssignment;
import com.airtel.buyer.airtelboe.repository.supplier.*;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import com.airtel.buyer.airtelboe.util.DmsUtil;
import com.airtel.buyer.airtelboe.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScmServiceImpl implements ScmService {

    @Autowired
    BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;
    @Autowired
    BtvlWkfBoeShipLineTblRepository btvlWkfBoeShipLineTblRepository;
    @Autowired
    BtvlWkfBoeShipDocTblRepository btvlWkfBoeShipDocTblRepository;
    @Autowired
    BtvlMstBoeFtaTblRepository btvlMstBoeFtaTblRepository;
    @Autowired
    BtvlMstBoeAntiDumpingRepository btvlMstBoeAntiDumpingRepository;
    @Autowired
    BtvlMstBoePortTblRepository btvlMstBoePortTblRepository;
    @Autowired
    ScmServiceTxn scmServiceTxn;

    @Value("${app.node}")
    private String appNode;

    @Value("${UPLOAD.DOC.TO.UCM.URL}")
    private String uploadUcmUrl;

    @Value("${BOE_SHIPMENT_SUBMIT_MAIL_SUBJECT_CHA_ASSIGN_SUCCESS}")
    private String BOE_SHIPMENT_SUBMIT_MAIL_SUBJECT_CHA_ASSIGN_SUCCESS;

    @Value("${BOE_SHIPMENT_SUBMIT_MAIL_BODY_CHA_ASSIGN_SUCCESS}")
    private String BOE_SHIPMENT_SUBMIT_MAIL_BODY_CHA_ASSIGN_SUCCESS;

    @Value("${SUPPLIER_LOGIN_URL}")
    private String SUPPLIER_LOGIN_URL;

    @Value("${AIRTEL_BOE_FROM_EMAIL}")
    private String AIRTEL_BOE_FROM_EMAIL;

    @Autowired
    private HttpServletResponse httpServletResponse;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JavaMailSender javaMailSender;

    private static String fetchDocId(String docName) {
        //24	BOE COPY
        //25	PACKING LIST
        //26	AIRWAY BILL
        //27	BILL OF LADING
        //28	INSURANCE CERTIFICATE
        //29	FREIGHT CERTIFICATE
        //30	COUNTRY OF ORIGIN CERTIFICATE
        //31	EXPORT LICENCE
        //32	DEMURRAGE
        //33	CONCOR / AAI
        //34	OTHER1
        //35	OTHER2
        //36	OTHER3
        //37	OTHER4
        //38	OTHER5
        //
        switch (docName) {
            case "BOE COPY":
                return "24";
            case "PACKING LIST":
                return "25";
            case "AIRWAY BILL":
                return "26";
            case "BILL OF LADING":
                return "27";
            case "INSURANCE CERTIFICATE":
                return "28";
            case "FREIGHT CERTIFICATE":
                return "29";
            case "COUNTRY OF ORIGIN CERTIFICATE":
                return "30";
            case "EXPORT LICENCE":
                return "31";
            case "DEMURRAGE":
                return "32";
            case "OTHER1":
                return "33";
            case "OTHER2":
                return "34";
            case "OTHER3":
                return "35";
            case "OTHER4":
                return "36";
            case "OTHER5":
                return "37";

        }
        return null;
    }

    private void setErrorObject(BoeResponse boeResponse, String code) {
        Error error = new Error();
        error.setCode(code);
        error.setTraceId(CommonConstants.generateTraceId(appNode));
        boeResponse.setError(error);
    }

    @Override
    public BoeResponse<SubmitStage2Response> scmSubmitStage2Information(SubmitStage2Request submitStage2Request) {
        log.info("Entering class ::: ScmServiceImpl ::: method :: scmSubmitStage2Information :: " + submitStage2Request);
        BoeResponse<SubmitStage2Response> response = new BoeResponse<>();
        SubmitStage2Response submitStage2Response = getResponseObject(submitStage2Request);
        response.setData(submitStage2Response);

        if (submitStage2Request.getShipmentId() == null) {
            log.error("Shipment Id not found for request : " + submitStage2Request);
            setErrorObject(response, CommonConstants.SCM_STAGE2_SUBMIT_ERROR_CODE);
            throw new BoeException(CommonConstants.SHIPMENT_ID_NOT_PRESENT, response, HttpStatus.BAD_REQUEST);
        }

        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL = btvlWkfBoeShipmentTblRepository.findByShipmentId(submitStage2Request.getShipmentId());
        // check if shipment object has stage - 2, bucket - 11
        try {
            if (bTVL_WKF_BOE_SHIPMENT_TBL.getStage().intValue() != 2 || bTVL_WKF_BOE_SHIPMENT_TBL.getBucketNo().intValue() != 11) {
                log.error("Shipment id cannot be processed as bucket no and stage are different than 11 , 2 respectively " + submitStage2Request + ", Stage value found in db :" + bTVL_WKF_BOE_SHIPMENT_TBL.getStage() + ", Bucket No found in db : " + bTVL_WKF_BOE_SHIPMENT_TBL.getBucketNo());
                setErrorObject(response, CommonConstants.SCM_STAGE2_SUBMIT_ERROR_CODE);
                throw new BoeException(CommonConstants.STAGE_BUCKET_MATCH_FAILED, response, HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            log.error("Exception occured when matching stage and bucket" + e.getMessage());
            setErrorObject(response, CommonConstants.SCM_STAGE2_SUBMIT_ERROR_CODE);
            throw new BoeException(CommonConstants.STAGE_BUCKET_MATCH_FAILED, response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<BTVL_WKF_BOE_SHIP_LINE_TBL> bTVL_WKF_BOE_SHIP_LINE_TBLs = btvlWkfBoeShipLineTblRepository.findByShipmentId(submitStage2Request.getShipmentId());
        List<BTVL_WKF_BOE_SHIP_DOC_TBL> savedDocs = btvlWkfBoeShipDocTblRepository.findByShipmentId(submitStage2Response.getShipmentId());

        // step1 - check for mandatory fields
        if (!checkMandatoryFields(submitStage2Response)) {
            log.error("Mandatory Fields check failed for shipment id : " + submitStage2Request.getShipmentId());
            setErrorObject(response, CommonConstants.SCM_STAGE2_SUBMIT_ERROR_CODE);
            throw new BoeException(CommonConstants.CHECK_MANDATORY_FAILED, response, HttpStatus.BAD_REQUEST);
        }

        // step 2 - check for mandatory documents
        if (!checkMandatoryDocs(submitStage2Response, savedDocs)) {
            log.error("Mandatory Doc check failed for shipment id : " + submitStage2Request.getShipmentId());
            setErrorObject(response, CommonConstants.SCM_STAGE2_SUBMIT_ERROR_CODE);
            throw new BoeException(CommonConstants.CHECK_MANDATORY_DOCS_FAILED, response, HttpStatus.BAD_REQUEST);
        }

        // step 3 - get port type to check correct shipmentMode is provided
        String portType = fetchPortType(submitStage2Response.getDestinationPort());
        log.info("Port Type :" + portType);
        if (StringUtils.isBlank(portType)) {
            log.error("Port Type not found for : " + submitStage2Request);
            setErrorObject(response, CommonConstants.SCM_STAGE2_SUBMIT_ERROR_CODE);
            throw new BoeException("Port Type Not Found", response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (portType.contains("SEZ") || portType.contains("FTWZ")) {
            //Below code to check Shipment Mode for FTWZ and SEZ ports
            if (!"Land".equalsIgnoreCase(submitStage2Response.getShipmentMode())) {
                log.error("Incorrect Mode of Shipment provided for : " + submitStage2Request);
                submitStage2Response.setShipmentModeErrMsg("Please choose Land as mode of shipment for FTWZ/SEZ destination port");
                setErrorObject(response, CommonConstants.SCM_STAGE2_SUBMIT_ERROR_CODE);
                throw new BoeException("Incorrect Mode of Shipment", response, HttpStatus.BAD_REQUEST);
            }
        }

        // step 4 - if all above steps pass, upload documents to ucm
        List<String> listOfFilePathsToUploadToUCM = submitStage2Response.getDocs().stream().map(submitShipmentDoc -> submitShipmentDoc.getContentId()).collect(Collectors.toList());

        List<BTVL_WKF_BOE_SHIP_DOC_TBL> btvl_wkf_boe_ship_doc_tbl_list = uploadShipmentDocs(listOfFilePathsToUploadToUCM, submitStage2Response);

        List<BTVL_WKF_BOE_TRAIL_TBL> trailList = new ArrayList<>();
        // step 5 - generate trail
        trailList.add(getBtvl_wkf_boe_trail_tbl(
                bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId(),
                "SUBMIT",
                "SCM",
                3,
                11,
                2,
                0));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStage(BigDecimal.valueOf(3));
        bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(BigDecimal.valueOf(3));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(BigDecimal.valueOf(3));
        // step 5.2 - update shipment object data from request object
        updateShipmentDetails(bTVL_WKF_BOE_SHIPMENT_TBL, submitStage2Response);

        // step 5.3 - update shipment line object data from request object
        updateShipmentLineDetails(bTVL_WKF_BOE_SHIP_LINE_TBLs, submitStage2Response);

        // step 6 - do cha assignment
        ChaAssignment chaAssignment = doChaAssignment(trailList, bTVL_WKF_BOE_SHIPMENT_TBL);
        log.info("Cha :: " + chaAssignment);
        if (chaAssignment != null && !StringUtils.isBlank(chaAssignment.getChaCode())) {
            if (bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentArrivalDate() == null) {
                bTVL_WKF_BOE_SHIPMENT_TBL.setShipmentArrivalDate(bTVL_WKF_BOE_SHIPMENT_TBL.getExpectedArrivalDate());
            }
        } else {
            submitStage2Response.setShipmentModeErrMsg("Please choose Land as mode of shipment for FTWZ/SEZ destination port");
            setErrorObject(response, CommonConstants.SCM_STAGE2_SUBMIT_ERROR_CODE);
            String msg =
                    String.format("Unable to assign CHA on the basis of LE : %s, OU :%s, PORT CODE :%s. Kindly update Master.",
                            bTVL_WKF_BOE_SHIPMENT_TBL.getLeName(),
                            bTVL_WKF_BOE_SHIPMENT_TBL.getOuName(),
                            bTVL_WKF_BOE_SHIPMENT_TBL.getDestinationPort());
            throw new BoeException(msg, response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Shipment Object : " + bTVL_WKF_BOE_SHIPMENT_TBL);
        log.info("Shipment Lines Object : " + bTVL_WKF_BOE_SHIP_LINE_TBLs);
        log.info("Doc List Object : " + btvl_wkf_boe_ship_doc_tbl_list);
        log.info("Trail List Object : " + trailList);
        // step 5 - save information in db
        Boolean infoSaved = scmServiceTxn.saveScmStage2Data(bTVL_WKF_BOE_SHIPMENT_TBL, bTVL_WKF_BOE_SHIP_LINE_TBLs, btvl_wkf_boe_ship_doc_tbl_list, trailList);
        log.info("infoSaved : " + infoSaved);

        // step 8 - send notification email
        if (infoSaved) {
            // SEND MAIL TO CHA AGENT.
            String subject =
                    String.format(BOE_SHIPMENT_SUBMIT_MAIL_SUBJECT_CHA_ASSIGN_SUCCESS, chaAssignment.getChaName(), LocalDate.now());

            String mailBody =
                    String.format(BOE_SHIPMENT_SUBMIT_MAIL_BODY_CHA_ASSIGN_SUCCESS,
                            SUPPLIER_LOGIN_URL,
                            bTVL_WKF_BOE_SHIPMENT_TBL.getInvoiceNumbers(),
                            bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId(),
                            bTVL_WKF_BOE_SHIPMENT_TBL.getOuName(),
                            bTVL_WKF_BOE_SHIPMENT_TBL.getDestinationPort(),
                            fetchPortName(bTVL_WKF_BOE_SHIPMENT_TBL.getDestinationPort()));

            Boolean result1 =
                    EmailUtil.sendEmail(
                            javaMailSender,
                            chaAssignment.getChaEmail(),
                            null,
                            AIRTEL_BOE_FROM_EMAIL,
                            subject,
                            mailBody);
            log.info("Email sent status :: " + result1);
        }
        log.info("Exit class ::: ScmServiceImpl ::: method :: scmSubmitStage2Information :: " + response);
        return response;
    }

    private void updateShipmentDetails(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_wkf_boe_shipment_tbl, SubmitStage2Response submitStage2Response) {
        bTVL_wkf_boe_shipment_tbl.setShipmentMode(submitStage2Response.getShipmentMode());
        bTVL_wkf_boe_shipment_tbl.setCountryLoading(submitStage2Response.getCountryLoading());
        bTVL_wkf_boe_shipment_tbl.setPortLoading(submitStage2Response.getPortLoading());
        bTVL_wkf_boe_shipment_tbl.setDestinationCountry(submitStage2Response.getDestinationCountry());
        bTVL_wkf_boe_shipment_tbl.setDestinationPort(submitStage2Response.getDestinationPort());
        bTVL_wkf_boe_shipment_tbl.setGrossShipmentWeight(submitStage2Response.getGrossShipmentWeight());
        bTVL_wkf_boe_shipment_tbl.setGrossShipmentVolume(submitStage2Response.getGrossShipmentVolume());
        bTVL_wkf_boe_shipment_tbl.setExpectedArrivalDate(LocalDate.parse(submitStage2Response.getExpectedArrivalDate()));
        bTVL_wkf_boe_shipment_tbl.setAwbBol(submitStage2Response.getAwbBol());
        bTVL_wkf_boe_shipment_tbl.setFreightAmtCurrency(submitStage2Response.getFreightAmtCurrency());
        bTVL_wkf_boe_shipment_tbl.setFreightAmt(submitStage2Response.getFreightAmt());
        bTVL_wkf_boe_shipment_tbl.setInsuranceAmtCurrency(submitStage2Response.getInsuranceAmtCurrency());
        bTVL_wkf_boe_shipment_tbl.setInsuranceAmt(submitStage2Response.getInsuranceAmt());
        bTVL_wkf_boe_shipment_tbl.setEfChargesAmt(submitStage2Response.getEfCharges());
        bTVL_wkf_boe_shipment_tbl.setShipmentArrivalDate(LocalDate.parse(submitStage2Response.getShipmentArrivalDate()));
    }

    private void updateShipmentLineDetails(List<BTVL_WKF_BOE_SHIP_LINE_TBL> bTVL_wkf_boe_ship_line_tbLs, SubmitStage2Response submitStage2Response) {
        for (BTVL_WKF_BOE_SHIP_LINE_TBL bTVL_WKF_BOE_SHIP_LINE_TBL : bTVL_wkf_boe_ship_line_tbLs) {
            LineInfo lineInfo = getLineObjectFromRequest(bTVL_WKF_BOE_SHIP_LINE_TBL.getInvoiceLineId(), submitStage2Response);

            bTVL_WKF_BOE_SHIP_LINE_TBL.setItemCode(lineInfo.getItemCode());
            bTVL_WKF_BOE_SHIP_LINE_TBL.setItemDescription(lineInfo.getItemDescription());
            bTVL_WKF_BOE_SHIP_LINE_TBL.setHsnCode(lineInfo.getHsnCode());
            bTVL_WKF_BOE_SHIP_LINE_TBL.setCountryOfOrigin(lineInfo.getCountryOfOrigin());
            bTVL_WKF_BOE_SHIP_LINE_TBL.setIsFta(lineInfo.getIsFtaChecked());
            bTVL_WKF_BOE_SHIP_LINE_TBL.setIsAntiDumping(lineInfo.getIsAntiDumpingChecked());
            bTVL_WKF_BOE_SHIP_LINE_TBL.setIsWpcRequiremnt(lineInfo.getIsWpcChecked());

            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            bTVL_WKF_BOE_SHIP_LINE_TBL.setModifiedby(user.getEmail());
            bTVL_WKF_BOE_SHIP_LINE_TBL.setModifiedDate(LocalDateTime.now());
        }
    }

    private LineInfo getLineObjectFromRequest(BigDecimal invoiceLineId, SubmitStage2Response submitStage2Response) {
        if (invoiceLineId == null) {
            return null;
        }
        for (Fta fta : submitStage2Response.getFta()) {
            for (LineInfo li : fta.getLineInfoList()) {
                if (li.getInvoiceLineId().intValue() == invoiceLineId.intValue()) {
                    return li;
                }
            }
        }
        return null;
    }

    private SubmitStage2Response getResponseObject(SubmitStage2Request submitStage2Request) {
        SubmitStage2Response response = new SubmitStage2Response();

        response.setShipmentMode(submitStage2Request.getShipmentMode());
        response.setCountryLoading(submitStage2Request.getCountryLoading());
        response.setPortLoading(submitStage2Request.getPortLoading());
        response.setDestinationCountry(submitStage2Request.getDestinationCountry());
        response.setDestinationPort(submitStage2Request.getDestinationPort());
        response.setGrossShipmentWeight(submitStage2Request.getGrossShipmentWeight());
        response.setGrossShipmentVolume(submitStage2Request.getGrossShipmentVolume());
        response.setExpectedArrivalDate(submitStage2Request.getExpectedArrivalDate());
        response.setAwbBol(submitStage2Request.getAwbBol());
        response.setFreightAmtCurrency(submitStage2Request.getFreightAmtCurrency());
        response.setFreightAmt(submitStage2Request.getFreightAmt());
        response.setInsuranceAmtCurrency(submitStage2Request.getInsuranceAmtCurrency());
        response.setInsuranceAmt(submitStage2Request.getInsuranceAmt());
        response.setEfCharges(submitStage2Request.getEfCharges());
        response.setShipmentArrivalDate(submitStage2Request.getShipmentArrivalDate());


        List<Fta> ftaList = submitStage2Request.getFta().stream().map(sli -> {
            Fta fta = new Fta();
            fta.setInvoiceNumber(sli.getInvoiceNumber());
            fta.setInvoiceHeaderId(sli.getInvoiceHeaderId());

            List<LineInfo> lineInfoList = sli.getLineInfoList().stream().map(li -> {
                LineInfo liObj = new LineInfo();
                liObj.setShipmentLineId(li.getShipmentLineId());
                liObj.setInvoiceLineId(li.getInvoiceLineId());
                liObj.setItemCode(li.getItemCode());
                liObj.setItemDescription(li.getItemDescription());
                liObj.setHsnCode(li.getHsnCode());
                liObj.setCountryOfOrigin(li.getCountryOfOrigin());
                liObj.setIsFtaChecked(li.getIsFtaChecked());
                liObj.setIsAntiDumpingChecked(li.getIsAntiDumpingChecked());
                liObj.setIsWpcChecked(li.getIsWpcChecked());
                return liObj;
            }).collect(Collectors.toList());
            fta.setLineInfoList(lineInfoList);

            return fta;
        }).collect(Collectors.toList());

        response.setFta(ftaList);

        List<ShipmentDoc> docs = submitStage2Request.getDocs().stream().map(d -> {
            ShipmentDoc shipmentDoc = new ShipmentDoc();
            shipmentDoc.setDocId(fetchDocId(d.getDocName()));
            shipmentDoc.setDocName(d.getDocName());
            shipmentDoc.setContentId(d.getContentId());

            return shipmentDoc;
        }).collect(Collectors.toList());
        response.setDocs(docs);

        return response;
    }

    private Boolean checkMandatoryFields(SubmitStage2Response submitStage2Response) {
        log.info("Checking Mandatory Fields.");
        int failedCount = 0;
        //Mode of Shipment
        if (StringUtils.isBlank(submitStage2Response.getShipmentMode())) {
            submitStage2Response.setShipmentModeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else {
            submitStage2Response.setShipmentModeErrMsg(null);
        }
        //Country of Loading
        if (StringUtils.isBlank(submitStage2Response.getCountryLoading())) {
            submitStage2Response.setCountryLoadingErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else {
            submitStage2Response.setCountryLoadingErrMsg(null);
        }
        //Port of Loading
        if (StringUtils.isBlank(submitStage2Response.getPortLoading())) {
            submitStage2Response.setPortLoadingErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else {
            submitStage2Response.setPortLoadingErrMsg(null);
        }
        //Destination Country
        if (StringUtils.isBlank(submitStage2Response.getDestinationCountry())) {
            submitStage2Response.setDestinationCountryErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else {
            submitStage2Response.setDestinationCountryErrMsg(null);
        }
        //Destination Port
        if (StringUtils.isBlank(submitStage2Response.getDestinationPort())) {
            submitStage2Response.setDestinationPortErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else {
            submitStage2Response.setDestinationPortErrMsg(null);
        }
        //Gross Shipment Weight
        if (submitStage2Response.getGrossShipmentWeight() == null) {
            submitStage2Response.setGrossShipmentWeightErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else if (submitStage2Response.getEfCharges().intValue() <= 0) {
            submitStage2Response.setGrossShipmentWeightErrMsg("Gross Shipment Weight must be greater than 0.");
            failedCount++;
        } else {
            submitStage2Response.setGrossShipmentWeightErrMsg(null);
        }
        //Gross Shipment Volume
        if (submitStage2Response.getGrossShipmentVolume() == null) {
            submitStage2Response.setGrossShipmentVolumeErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else if (submitStage2Response.getGrossShipmentVolume().intValue() <= 0) {
            submitStage2Response.setGrossShipmentVolumeErrMsg("Gross Shipment Weight must be greater than 0.");
            failedCount++;
        } else {
            submitStage2Response.setGrossShipmentVolumeErrMsg(null);
        }
        //Expected Arrival Date/Arrival Date
        if (null == submitStage2Response.getExpectedArrivalDate()) {
            submitStage2Response.setExpectedArrivalDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else {
            submitStage2Response.setExpectedArrivalDateErrMsg(null);
        }
        //Airway Bill No./Bill of Lading
        if (StringUtils.isBlank(submitStage2Response.getAwbBol())) {
            submitStage2Response.setAwbBolErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else {
            submitStage2Response.setAwbBolErrMsg(null);
        }
        //Freight Amt and Currency
        // check if value is null - if yes increment failed count
        if (StringUtils.isBlank(submitStage2Response.getFreightAmtCurrency()) ||
                submitStage2Response.getFreightAmt() == null) {
            submitStage2Response.setFreightAmtErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else if (submitStage2Response.getFreightAmt().intValue() <= 0) {
            submitStage2Response.setFreightAmtErrMsg("Freight Amount must be greater than 0.");
            failedCount++;
        } else {
            submitStage2Response.setFreightAmtErrMsg(null);
        }
        //Insurance Amt and Currency
        // check if value is null - if yes increment failed count
        if (StringUtils.isBlank(submitStage2Response.getInsuranceAmtCurrency()) ||
                submitStage2Response.getInsuranceAmt() == null) {
            submitStage2Response.setInsuranceAmtErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else if (submitStage2Response.getInsuranceAmt().intValue() <= 0) {
            submitStage2Response.setInsuranceAmtErrMsg("Insurance Amount must be greater than 0.");
            failedCount++;
        } else {
            submitStage2Response.setInsuranceAmtErrMsg(null);
        }
        // E&F charge mandatory check
        // check if value is null - if yes increment failed count
        if (submitStage2Response.getEfCharges() == null) {
            submitStage2Response.setEfChargesErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else if (submitStage2Response.getEfCharges().intValue() <= 0) {
            submitStage2Response.setEfChargesErrMsg("E&F Charge Amount must be greater than 0.");
            failedCount++;
        } else {
            submitStage2Response.setEfChargesErrMsg(null);
        }
        //Shipment Arrival Date/Arrival Date
        if (null == submitStage2Response.getShipmentArrivalDate()) {
            submitStage2Response.setShipmentArrivalDateErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            failedCount++;
        } else {
            submitStage2Response.setShipmentArrivalDateErrMsg(null);
        }

        //check if all shipment lines have Country of Origin set
        if (submitStage2Response.getFta() != null) {
            for (Fta invoice : submitStage2Response.getFta()) {
                for (LineInfo l : invoice.getLineInfoList()) {
                    if (StringUtils.isBlank(l.getCountryOfOrigin())) {
                        l.setCountryOfOriginErrMsg(CommonConstants.FIELD_IS_MANDATORY);
                        failedCount++;
                    } else {
                        l.setCountryOfOriginErrMsg(null);
                    }
                }
            }
        } else {
            submitStage2Response.setFtaErrMsg(CommonConstants.FTA_NODE_NOT_PRESENT);
            failedCount++;
        }
        log.info("Failed checks count : " + failedCount);

        if (failedCount > 0) {
            submitStage2Response.setHeaderErrMsg("Please fix the highlighted errors in order to process further.");
        } else {
            submitStage2Response.setHeaderErrMsg(null);
        }

        return failedCount == 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    private Boolean checkMandatoryDocs(SubmitStage2Response submitStage2Response,
                                       List<BTVL_WKF_BOE_SHIP_DOC_TBL> savedDocs) {
        Boolean pass = Boolean.TRUE;
        // 26 - AIRWAY BILL - IFF - Mode of Shipment is Air
        ShipmentDoc airwayBillDoc = submitStage2Response.getDocs().stream().filter(d -> "26".equalsIgnoreCase(d.getDocId())).findAny().orElse(null);
        if (submitStage2Response.getShipmentMode().equalsIgnoreCase("Air") && airwayBillDoc == null) {
            submitStage2Response.setDocsErrMsg("Airway Bill Document is missing");
            pass = Boolean.FALSE;
        } else if (submitStage2Response.getShipmentMode().equalsIgnoreCase("Air") && airwayBillDoc != null && StringUtils.isBlank(airwayBillDoc.getContentId())) {
            airwayBillDoc.setDocErrMsg(CommonConstants.UPLOADED_DOC_ERR_MSG);
            pass = Boolean.FALSE;
        }

        // 27 - BILL OF LADING - IFF - Mode of Shipment is Ship or Land
        ShipmentDoc billOfLadingDoc = submitStage2Response.getDocs().stream().filter(d -> "27".equalsIgnoreCase(d.getDocId())).findAny().orElse(null);
        if ((submitStage2Response.getShipmentMode().equalsIgnoreCase("Ship") || submitStage2Response.getShipmentMode().equalsIgnoreCase("Land")) &&
                billOfLadingDoc == null) {
            submitStage2Response.setDocsErrMsg("Bill Of Lading Document is missing");
            pass = Boolean.FALSE;
        } else if ((submitStage2Response.getShipmentMode().equalsIgnoreCase("Ship") || submitStage2Response.getShipmentMode().equalsIgnoreCase("Land")) &&
                billOfLadingDoc != null && StringUtils.isBlank(billOfLadingDoc.getContentId())) {
            billOfLadingDoc.setDocErrMsg(CommonConstants.UPLOADED_DOC_ERR_MSG);
            pass = Boolean.FALSE;
        }

        // 28 - INSURANCE CERTIFICATE
        ShipmentDoc insuranceDoc = submitStage2Response.getDocs().stream().filter(d -> "28".equalsIgnoreCase(d.getDocId())).findAny().orElse(null);
        if (insuranceDoc == null) {
            submitStage2Response.setDocsErrMsg("Insurance Document is missing");
            pass = Boolean.FALSE;
        } else if (insuranceDoc != null && StringUtils.isBlank(insuranceDoc.getContentId())) {
            insuranceDoc.setDocErrMsg(CommonConstants.UPLOADED_DOC_ERR_MSG);
            pass = Boolean.FALSE;
        }
        // 29 - FREIGHT CERTIFICATE
        ShipmentDoc freightDoc = submitStage2Response.getDocs().stream().filter(d -> "29".equalsIgnoreCase(d.getDocId())).findAny().orElse(null);
        if (freightDoc == null) {
            submitStage2Response.setDocsErrMsg("Freight Document is missing");
            pass = Boolean.FALSE;
        } else if (freightDoc != null && StringUtils.isBlank(freightDoc.getContentId())) {
            freightDoc.setDocErrMsg(CommonConstants.UPLOADED_DOC_ERR_MSG);
            pass = Boolean.FALSE;
        }
        // 30 - COUNTRY OF ORIGIN CERTIFICATE - IFF
        List<ShipmentDoc> countryOfOriginDocs = submitStage2Response.getDocs().stream().filter(d -> "30".equalsIgnoreCase(d.getDocId())).collect(Collectors.toList());
        // country of origin document is applicable if any of the shipment line has isFTA true, alteast one country of origin document is required.
        if (countryOfOriginDocs.size() > 0) {
            for (ShipmentDoc d : countryOfOriginDocs) {
                if (StringUtils.isBlank(d.getContentId())) {
                    d.setDocErrMsg(CommonConstants.UPLOADED_DOC_ERR_MSG);
                    pass = Boolean.FALSE;
                }
            }
        }

        // 34 - OTHERS1
        ShipmentDoc other1Doc = submitStage2Response.getDocs().stream().filter(d -> "34".equalsIgnoreCase(d.getDocId())).findAny().orElse(null);
        if (!isDocAlreadyUploaded(savedDocs, 34) && other1Doc != null && StringUtils.isBlank(other1Doc.getContentId())) {
            other1Doc.setDocErrMsg(CommonConstants.UPLOADED_DOC_ERR_MSG);
            pass = Boolean.FALSE;
        }

        // 35 - OTHERS2
        ShipmentDoc other2Doc = submitStage2Response.getDocs().stream().filter(d -> "35".equalsIgnoreCase(d.getDocId())).findAny().orElse(null);
        if (!isDocAlreadyUploaded(savedDocs, 35) && other2Doc != null && StringUtils.isBlank(other2Doc.getContentId())) {
            other2Doc.setDocErrMsg(CommonConstants.UPLOADED_DOC_ERR_MSG);
            pass = Boolean.FALSE;
        }

        // 36 - OTHERS3
        ShipmentDoc other3Doc = submitStage2Response.getDocs().stream().filter(d -> "36".equalsIgnoreCase(d.getDocId())).findAny().orElse(null);
        if (!isDocAlreadyUploaded(savedDocs, 36) && other3Doc != null && StringUtils.isBlank(other3Doc.getContentId())) {
            other3Doc.setDocErrMsg(CommonConstants.UPLOADED_DOC_ERR_MSG);
            pass = Boolean.FALSE;
        }
        // 37 - OTHERS4
        ShipmentDoc other4Doc = submitStage2Response.getDocs().stream().filter(d -> "37".equalsIgnoreCase(d.getDocId())).findAny().orElse(null);
        if (!isDocAlreadyUploaded(savedDocs, 37) && other4Doc != null && StringUtils.isBlank(other4Doc.getContentId())) {
            other4Doc.setDocErrMsg(CommonConstants.UPLOADED_DOC_ERR_MSG);
            pass = Boolean.FALSE;
        }
        // 38 - OTHERS5
        ShipmentDoc other5Doc = submitStage2Response.getDocs().stream().filter(d -> "38".equalsIgnoreCase(d.getDocId())).findAny().orElse(null);
        if (!isDocAlreadyUploaded(savedDocs, 38) && other5Doc != null && StringUtils.isBlank(other5Doc.getContentId())) {
            other5Doc.setDocErrMsg(CommonConstants.UPLOADED_DOC_ERR_MSG);
            pass = Boolean.FALSE;
        }

        return pass;
    }

    private List<BTVL_WKF_BOE_SHIP_DOC_TBL> uploadShipmentDocs
            (List<String> listOfFilePathsToUploadToUCM, SubmitStage2Response submitStage2Response) {

        List<BTVL_WKF_BOE_SHIP_DOC_TBL> listOfShipmentDocs = new ArrayList<>();

        // upload list of documents to ucm by calling partner common upload rest api
        List<DocumentResponse> uploadedDocs = this.uploadDocument(listOfFilePathsToUploadToUCM);

        // once uploaded merge the shipment request docs array with the above received doc response array
        for (DocumentResponse d : uploadedDocs) {
            for (ShipmentDoc rd : submitStage2Response.getDocs()) {
                if (d.getFileName().equalsIgnoreCase(rd.getContentId())) {
                    listOfShipmentDocs.add(getBtvlWkfBoeShipDocTblObj(d.getContentId(), new BigDecimal(rd.getDocId()), d.getDocUrl(), submitStage2Response.getShipmentId()));
                }
            }
        }
        return listOfShipmentDocs;
    }

    private BTVL_WKF_BOE_SHIP_DOC_TBL getBtvlWkfBoeShipDocTblObj(String contentId, BigDecimal docId, String docUrl, BigDecimal shipmentId) {

        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BTVL_WKF_BOE_SHIP_DOC_TBL bTVL_WKF_BOE_SHIP_DOC_TBL = new BTVL_WKF_BOE_SHIP_DOC_TBL();
        bTVL_WKF_BOE_SHIP_DOC_TBL.setShipmentId(shipmentId);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setAttribute(contentId);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setDocId(docId);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setDocUrl(docUrl);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setStage(BigDecimal.valueOf(2));
        bTVL_WKF_BOE_SHIP_DOC_TBL.setPurgeFlag(0);
        bTVL_WKF_BOE_SHIP_DOC_TBL.setCreatedby(user.getEmail());
        bTVL_WKF_BOE_SHIP_DOC_TBL.setModifiedby(user.getEmail());
        bTVL_WKF_BOE_SHIP_DOC_TBL.setCreationDate(LocalDateTime.now());
        bTVL_WKF_BOE_SHIP_DOC_TBL.setModifiedDate(LocalDateTime.now());

        return bTVL_WKF_BOE_SHIP_DOC_TBL;
    }

    private List<DocumentResponse> uploadDocument(List<String> docsPathList) {
        log.info("Inside class ShipmentServiceImpl::entering::method::uploadDocument::docPathList::" + docsPathList);

        List<DocumentResponse> documentResponseList = DmsUtil.uploadDoc(docsPathList, this.uploadUcmUrl, this.httpServletResponse, this.httpServletRequest);

        if (documentResponseList != null && !documentResponseList.isEmpty()) {

            for (DocumentResponse documentResponse : documentResponseList) {
                if (documentResponse == null || StringUtils.isBlank(documentResponse.getContentId())
                        || !documentResponse.getContentId().startsWith("WC")) {
                    log.info("ShipmentServiceImpl :: method :: uploadDocument :: doc uploaded to UCM :: Error");
                    throw new BoeException("Document upload fails", null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            throw new BoeException("Document upload fails", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /* // TODO uncomment below for local testing
        List<DocumentResponse> documentResponseList = docsPathList.stream().map(s -> {
            DocumentResponse d = new DocumentResponse();
            d.setFileName(s);
            d.setContentId("WCC_TEST123");
            d.setDocUrl("test_url");
            return d;
        }).collect(Collectors.toList());
        */

        log.info("Inside ShipmentServiceImpl::exiting::method::uploadDocument");
        return documentResponseList;
    }

    public ChaAssignment doChaAssignment(List<BTVL_WKF_BOE_TRAIL_TBL> trailList, BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL) {
        ChaAssignment cha = btvlWkfBoeShipmentTblRepository.findChaAgentsByLeOuPortCode(
                bTVL_WKF_BOE_SHIPMENT_TBL.getLeName(),
                bTVL_WKF_BOE_SHIPMENT_TBL.getOuName(),
                bTVL_WKF_BOE_SHIPMENT_TBL.getDestinationPort()).get(0);
        if (cha != null) {

            trailList.add(getBtvl_wkf_boe_trail_tbl(
                    bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId(),
                    "SUBMIT",
                    "SCM",
                    4,
                    11,
                    2,
                    0));
            bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(BigDecimal.valueOf(4));
            bTVL_WKF_BOE_SHIPMENT_TBL.setAssignedChaId(cha.getChaCode());
            bTVL_WKF_BOE_SHIPMENT_TBL.setChaAssigmentDate(LocalDate.now());
        } else {
            log.info("Assignment Failed");
        }
        return cha;
    }

    private BTVL_WKF_BOE_TRAIL_TBL getBtvl_wkf_boe_trail_tbl(BigDecimal shipmentId,
                                                             String action,
                                                             String actionBy,
                                                             int status,
                                                             int bucketNo,
                                                             int stage,
                                                             int purgeFlag) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL1 = new BTVL_WKF_BOE_TRAIL_TBL();
        bTVL_WKF_BOE_TRAIL_TBL1.setShipmentId(shipmentId);
        bTVL_WKF_BOE_TRAIL_TBL1.setAction(action);
        bTVL_WKF_BOE_TRAIL_TBL1.setActionBy(actionBy);
        bTVL_WKF_BOE_TRAIL_TBL1.setStatus(new BigDecimal(status)); // 4 - CHA ASSIGNMENT
        bTVL_WKF_BOE_TRAIL_TBL1.setBucketNo(new BigDecimal(bucketNo));
        bTVL_WKF_BOE_TRAIL_TBL1.setStage(new BigDecimal(stage));
        bTVL_WKF_BOE_TRAIL_TBL1.setPurgeFlag(purgeFlag);
        bTVL_WKF_BOE_TRAIL_TBL1.setCreatedby(user.getEmail());
        bTVL_WKF_BOE_TRAIL_TBL1.setCreationDate(LocalDateTime.now());
        bTVL_WKF_BOE_TRAIL_TBL1.setModifiedby(user.getEmail());
        bTVL_WKF_BOE_TRAIL_TBL1.setModifiedDate(LocalDateTime.now());
        return bTVL_WKF_BOE_TRAIL_TBL1;
    }

    private BTVL_WKF_BOE_TRAIL_TBL getBtvl_wkf_boe_trail_tbl(BigDecimal shipmentId,
                                                             String action,
                                                             String actionBy,
                                                             int status,
                                                             int bucketNo,
                                                             int stage,
                                                             int purgeFlag,
                                                             String commentHeader,
                                                             String commentLine) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL1 = new BTVL_WKF_BOE_TRAIL_TBL();
        bTVL_WKF_BOE_TRAIL_TBL1.setShipmentId(shipmentId);
        bTVL_WKF_BOE_TRAIL_TBL1.setAction(action);
        bTVL_WKF_BOE_TRAIL_TBL1.setActionBy(actionBy);
        bTVL_WKF_BOE_TRAIL_TBL1.setStatus(new BigDecimal(status)); // 4 - CHA ASSIGNMENT
        bTVL_WKF_BOE_TRAIL_TBL1.setBucketNo(new BigDecimal(bucketNo));
        bTVL_WKF_BOE_TRAIL_TBL1.setStage(new BigDecimal(stage));
        bTVL_WKF_BOE_TRAIL_TBL1.setPurgeFlag(purgeFlag);
        bTVL_WKF_BOE_TRAIL_TBL1.setCreatedby(user.getEmail());
        bTVL_WKF_BOE_TRAIL_TBL1.setCreationDate(LocalDateTime.now());
        bTVL_WKF_BOE_TRAIL_TBL1.setModifiedby(user.getEmail());
        bTVL_WKF_BOE_TRAIL_TBL1.setModifiedDate(LocalDateTime.now());
        bTVL_WKF_BOE_TRAIL_TBL1.setCommentHeader(commentHeader);
        bTVL_WKF_BOE_TRAIL_TBL1.setCommentLine(commentLine);
        return bTVL_WKF_BOE_TRAIL_TBL1;
    }

    @Override
    public Boolean isFta(String countryCode) {
        //return btvlMstBoeFtaTblRepository.findByCountryCode(countryCode) != null ? Boolean.TRUE : Boolean.FALSE;
        return null;
    }

    @Override
    public Boolean isAntiDumping(String countryCode) {
        //return btvlMstBoeAntiDumpingRepository.findByCountryCode(countryCode) != null ? Boolean.TRUE : Boolean.FALSE;
        return null;
    }

    private Boolean isDocAlreadyUploaded(List<BTVL_WKF_BOE_SHIP_DOC_TBL> savedDocs, int docId) {
        Predicate<BTVL_WKF_BOE_SHIP_DOC_TBL> p1 = p -> p.getDocId().intValue() == docId;
        return savedDocs.stream().anyMatch(p1);
    }

    private String fetchPortType(String portCode) {
        return btvlMstBoePortTblRepository.findByPortCodeIgnoreCase(portCode) != null ? btvlMstBoePortTblRepository.findByPortCodeIgnoreCase(portCode).get(0).getPortType() : null;
    }

    private String fetchPortName(String portCode) {
        return btvlMstBoePortTblRepository.findByPortCodeIgnoreCase(portCode) != null ? btvlMstBoePortTblRepository.findByPortCodeIgnoreCase(portCode).get(0).getPortName() : null;
    }

    @Override
    public BoeResponse<SubmitStage4Response> stage4ApproveByScm(SubmitStage4Request submitStage4Request) {


        BoeResponse<SubmitStage4Response> response = new BoeResponse<>();
        SubmitStage4Response submitStage4Response = new SubmitStage4Response();
        response.setData(submitStage4Response);

        // validate request object, check if shipmentid is present
        if (submitStage4Request.getShipmentId() == null) {
            log.error("Shipment Id not found for request : " + submitStage4Request);
            setErrorObject(response, CommonConstants.SCM_STAGE4_APPROVE_ERROR_CODE);
            throw new BoeException(CommonConstants.SHIPMENT_ID_NOT_PRESENT, response, HttpStatus.BAD_REQUEST);
        }

        submitStage4Response.setShipmentId(submitStage4Request.getShipmentId());

        // create trail object
        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL = getBtvl_wkf_boe_trail_tbl(
                submitStage4Request.getShipmentId(),
                "APPROVED",
                "SCM",
                6,
                12,
                4,
                0
        );
        List<BTVL_WKF_BOE_TRAIL_TBL> trailList = new ArrayList<>();
        trailList.add(bTVL_WKF_BOE_TRAIL_TBL);

        // update shipment object
        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL = btvlWkfBoeShipmentTblRepository.findByShipmentId(submitStage4Request.getShipmentId());
        bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(new BigDecimal(4));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStage(new BigDecimal(5));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(new BigDecimal(6));

        // save in db
        Boolean result = scmServiceTxn.scmApproveStage4(bTVL_WKF_BOE_SHIPMENT_TBL, trailList);

        if (result) {
            submitStage4Response.setStatus("success");
        } else {
            submitStage4Response.setStatus("failed");
        }
        return response;
    }

    @Override
    public BoeResponse<ScmStage4RejectResponse> stage4RejectByScm(ScmStage4RejectRequest scmStage4RejectRequest) {

        BoeResponse<ScmStage4RejectResponse> response = new BoeResponse<>();
        ScmStage4RejectResponse submitStage4Response = new ScmStage4RejectResponse();
        response.setData(submitStage4Response);

        // validate request object, check if shipmentid is present
        if (scmStage4RejectRequest.getShipmentId() == null) {
            log.error("Shipment Id not found for request : " + scmStage4RejectRequest);
            setErrorObject(response, CommonConstants.SCM_STAGE4_REJECT_ERROR_CODE);
            throw new BoeException(CommonConstants.SHIPMENT_ID_NOT_PRESENT, response, HttpStatus.BAD_REQUEST);
        }

        if (scmStage4RejectRequest.getRejectReason() == null) {
            log.error("Reject Reason not found for request : " + scmStage4RejectRequest);
            setErrorObject(response, CommonConstants.SCM_STAGE4_REJECT_ERROR_CODE);
            throw new BoeException(CommonConstants.REJ_REASON_NOT_PRESENT, response, HttpStatus.BAD_REQUEST);
        }

        if (scmStage4RejectRequest.getRejectDescription() == null) {
            log.error("Reject Reason description not found for request : " + scmStage4RejectRequest);
            setErrorObject(response, CommonConstants.SCM_STAGE4_REJECT_ERROR_CODE);
            throw new BoeException(CommonConstants.REJ_REASON_DESC_NOT_PRESENT, response, HttpStatus.BAD_REQUEST);
        }

        submitStage4Response.setShipmentId(scmStage4RejectRequest.getShipmentId());
        submitStage4Response.setRejectReason(scmStage4RejectRequest.getRejectReason());
        submitStage4Response.setRejectDescription(scmStage4RejectRequest.getRejectDescription());

        // create trail object
        BTVL_WKF_BOE_TRAIL_TBL bTVL_WKF_BOE_TRAIL_TBL = getBtvl_wkf_boe_trail_tbl(
                scmStage4RejectRequest.getShipmentId(),
                "REJECT",
                "SCM",
                4,
                12,
                4,
                0,
                scmStage4RejectRequest.getRejectReason(),
                scmStage4RejectRequest.getRejectDescription()
        );
        List<BTVL_WKF_BOE_TRAIL_TBL> trailList = new ArrayList<>();
        trailList.add(bTVL_WKF_BOE_TRAIL_TBL);

        // update shipment object
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL = btvlWkfBoeShipmentTblRepository.findByShipmentId(scmStage4RejectRequest.getShipmentId());
        bTVL_WKF_BOE_SHIPMENT_TBL.setBucketNo(new BigDecimal(6));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStage(new BigDecimal(3));
        bTVL_WKF_BOE_SHIPMENT_TBL.setStatus(new BigDecimal(4));
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedby(user.getEmail());
        bTVL_WKF_BOE_SHIPMENT_TBL.setModifiedDate(LocalDateTime.now());

        // save in db
        Boolean result = scmServiceTxn.scmRejectStage4(bTVL_WKF_BOE_SHIPMENT_TBL, trailList);

        if (result) {
            submitStage4Response.setStatus("success");
        } else {
            submitStage4Response.setStatus("failed");
        }

        return response;
    }
}
