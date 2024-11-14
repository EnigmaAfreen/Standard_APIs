package com.airtel.buyer.airtelboe.service.chareassignmentaction;

import com.airtel.buyer.airtelboe.dto.chareassignmentaction.request.ChaReassignmentRequest;
import com.airtel.buyer.airtelboe.dto.chareassignmentaction.response.ChaReassignmentResponse;
import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import com.airtel.buyer.airtelboe.dto.common.Error;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_CHA_TBL;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import com.airtel.buyer.airtelboe.repository.ChaAssignment;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeChaTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeShipmentTblRepository;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlWkfBoeTrailTblRepository;
import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import com.airtel.buyer.airtelboe.util.BoeUtils;
import com.airtel.buyer.airtelboe.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChaReassignmentServiceImpl implements ChaReassignmentActionService {


    @Value("${app.node}")
    public String appNode;

    @Value("${BOE_SHIPMENT_SUBMIT_MAIL_SUBJECT_CHA_ASSIGN_SUCCESS}")
    public String BOE_SHIPMENT_SUBMIT_MAIL_SUBJECT_CHA_ASSIGN_SUCCESS;

    @Value("${BOE_SHIPMENT_SUBMIT_MAIL_BODY_CHA_ASSIGN_SUCCESS}")
    public String BOE_SHIPMENT_SUBMIT_MAIL_BODY_CHA_ASSIGN_SUCCESS;

    @Value("${SUPPLIER_LOGIN_URL}")
    public String SUPPLIER_LOGIN_URL;

    @Value("${AIRTEL_BOE_FROM_EMAIL}")
    public String AIRTEL_BOE_FROM_EMAIL;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    BtvlWkfBoeShipmentTblRepository btvlWkfBoeShipmentTblRepository;

    @Autowired
    BtvlWkfBoeTrailTblRepository btvlWkfBoeTrailTblRepository;

    @Autowired
    ChaReassignmentTransactionService chaReassignmentTransactionService;

    @Autowired
    BtvlMstBoeChaTblRepository btvlMstBoeChaTblRepository;

    @Override
    public BoeResponse<ChaReassignmentResponse> submitChaReassignment(ChaReassignmentRequest chaReassignmentRequest, UserDetailsImpl userDetails) {
        BoeResponse<ChaReassignmentResponse> boeResponse = new BoeResponse<>();
        ChaReassignmentResponse chaReassignmentResponse = new ChaReassignmentResponse();
        boeResponse.setData(chaReassignmentResponse);

        if (this.checkMandatoryFields(chaReassignmentRequest, boeResponse)) {
            if (chaReassignmentRequest.getFromChaAgent().equalsIgnoreCase(chaReassignmentRequest.getToChaAgent())) {
                chaReassignmentResponse.setPopUpMessage("From CHA Agent and To CHA Agent can not be same.");
            } else {
                if (!"Assignment Failed".equalsIgnoreCase(chaReassignmentRequest.getFromChaAgent())) {
                    this.doActionForAssignmentNotFaild(boeResponse, chaReassignmentRequest, userDetails);
                } else if ("Assignment Failed".equalsIgnoreCase(chaReassignmentRequest.getFromChaAgent())) {
                    this.doActionForAssignmentFaild(boeResponse, chaReassignmentRequest, userDetails);
                }
                if (StringUtils.isBlank(boeResponse.getData().getPopUpMessage())) {
                    boeResponse.getData().setPopUpMessage("The CHA for selected BOE(s) has been successfully reassigned.");
                }
            }
        }


        return boeResponse;
    }

    public Boolean checkMandatoryFields(ChaReassignmentRequest chaReassignmentRequest,
                                        BoeResponse<ChaReassignmentResponse> boeResponse) {
        Boolean status = Boolean.TRUE;
        ChaReassignmentResponse chaReassignmentResponse = boeResponse.getData();


        if (StringUtils.isBlank(chaReassignmentRequest.getFromChaAgent())) {
            status = Boolean.FALSE;
            chaReassignmentResponse.setFromChaAgentErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }
        if (StringUtils.isBlank(chaReassignmentRequest.getToChaAgent())) {
            status = Boolean.FALSE;
            chaReassignmentResponse.setToChaAgentErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }
        if (StringUtils.isBlank(chaReassignmentRequest.getDescription())) {
            status = Boolean.FALSE;
            chaReassignmentResponse.setDescriptionErrMsg(CommonConstants.FIELD_IS_MANDATORY);
        }

        if (!chaReassignmentRequest.getFromChaAgent().equalsIgnoreCase("Assignment Failed") &&
                chaReassignmentRequest.getCheckListStatus().equalsIgnoreCase("Approved")) {
            if (StringUtils.isBlank(chaReassignmentRequest.getAcknowledgement())) {
                status = Boolean.FALSE;
                chaReassignmentResponse.setAcknowledgementErrMsg(CommonConstants.FIELD_IS_MANDATORY);
            }
        }

        if (!status) {
            this.setErrorObject(boeResponse);
        }
        log.info("ChaReassignmentServiceImpl :: inside method :: checkMandatoryFields :: status: " + status);
        return status;
    }

    private void setErrorObject(BoeResponse<ChaReassignmentResponse> boeResponse) {
        Error error = new Error();
        error.setCode(CommonConstants.CHA_REASSIGNMENT_ERROR_CODE);
        error.setTraceId(BoeUtils.generateTraceId(appNode));
        boeResponse.setError(error);
    }

    private void doActionForAssignmentNotFaild(BoeResponse<ChaReassignmentResponse> boeResponse, ChaReassignmentRequest chaReassignmentRequest, UserDetailsImpl userDetails) {
        Boolean result = Boolean.FALSE;
        if (chaReassignmentRequest.getShipmentId() != null) {

            List<BTVL_WKF_BOE_SHIPMENT_TBL> btvlWkfBoeShipmentTblList = this.btvlWkfBoeShipmentTblRepository.findByShipmentIdIn(chaReassignmentRequest.getShipmentId());


            for (BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL : btvlWkfBoeShipmentTblList) {

                try {
                    result = this.chaReassignmentTransactionService.doActionForAssignmentNotFaild(bTVL_WKF_BOE_SHIPMENT_TBL, boeResponse, chaReassignmentRequest, userDetails);
                } catch (Exception e) {
                    log.info("Exception Raised in method doActionForAssignmentNotFaild for shipment id" + bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId());
                    log.info("Exception is " + e.getMessage());
                    e.printStackTrace();
                }
                if (!result) {
                    boeResponse.getData().setPopUpMessage("CHA Re-assignment failed for some of the selected records. Please check the CHA master and try again.");
                } else if (result) {

                    String subject =
                            String.format(BOE_SHIPMENT_SUBMIT_MAIL_SUBJECT_CHA_ASSIGN_SUCCESS,
                                    this.getChaNameAndEmail(bTVL_WKF_BOE_SHIPMENT_TBL.getAssignedChaId()).get(0),
                                    bTVL_WKF_BOE_SHIPMENT_TBL.getModifiedDate()
                                            .toLocalDate());

                    String mailBody =
                            String.format(BOE_SHIPMENT_SUBMIT_MAIL_BODY_CHA_ASSIGN_SUCCESS,
                                    SUPPLIER_LOGIN_URL,
                                    bTVL_WKF_BOE_SHIPMENT_TBL.getInvoiceNumbers(),
                                    bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId(),
                                    bTVL_WKF_BOE_SHIPMENT_TBL.getOuName(),
                                    bTVL_WKF_BOE_SHIPMENT_TBL.getDestinationPort(),
                                    bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute1());

//                    Boolean result1 =
//                            EmailUtil.sendEmail(javaMailSender, this.getChaNameAndEmail(bTVL_WKF_BOE_SHIPMENT_TBL.getAssignedChaId()).get(1), null,
//                                    AIRTEL_BOE_FROM_EMAIL,
//                                    subject, mailBody);

//                    only for testing purpose
//                    Boolean result1 =
//                            EmailUtil.sendEmail(javaMailSender, "a_hari.o.tiwary@airtel.com", null,
//                                    AIRTEL_BOE_FROM_EMAIL,
//                                    subject, mailBody);


//                    log.info("Email Send Result : " + result1);
                }

            }
        }


    }

    public List<String> getChaNameAndEmail(String chaCode) {
        String chaName = null;
        String chaEmail = null;
        List<String> list = new ArrayList<>();

        List<BTVL_MST_BOE_CHA_TBL> btvlMstBoeChaTbl = this.btvlMstBoeChaTblRepository.findByChaCode(chaCode);

        if (btvlMstBoeChaTbl != null) {
            chaName = btvlMstBoeChaTbl.get(0).getChaName();
            chaEmail = btvlMstBoeChaTbl.get(0).getChaEmail();
            list.add(chaName);
            list.add(chaEmail);
        }
        log.info("Returned Cha Name : " + chaName);
        log.info("Returned Cha Email : " + chaName);
        return list;
    }

    private void doActionForAssignmentFaild(BoeResponse<ChaReassignmentResponse> boeResponse, ChaReassignmentRequest chaReassignmentRequest, UserDetailsImpl userDetails) {
        Boolean result = Boolean.FALSE;
        if (chaReassignmentRequest.getShipmentId() != null) {

            List<BTVL_WKF_BOE_SHIPMENT_TBL> btvlWkfBoeShipmentTblList = this.btvlWkfBoeShipmentTblRepository.findByShipmentIdIn(chaReassignmentRequest.getShipmentId());


            for (BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL : btvlWkfBoeShipmentTblList) {


                ChaAssignment cha = this.btvlMstBoeChaTblRepository.findChaAgentsByLeOuPortCode(bTVL_WKF_BOE_SHIPMENT_TBL.getLeName(), bTVL_WKF_BOE_SHIPMENT_TBL.getOuName(), bTVL_WKF_BOE_SHIPMENT_TBL.getAssignedChaId());

                if (cha != null) {

//
                    try {
                        result = this.chaReassignmentTransactionService.doActionForAssignmentFaild(cha, bTVL_WKF_BOE_SHIPMENT_TBL, boeResponse, chaReassignmentRequest, userDetails);
                    } catch (Exception e) {
                        log.info("Exception Raised in method doActionForAssignmentFaild for shipment id" + bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId());
                        log.info("Exception is " + e.getMessage());
                        e.printStackTrace();
                    }

                    if (result == null || !result) {
                        boeResponse.getData().setPopUpMessage("CHA Re-assignment failed for some of the selected records. Please check the CHA master and try again.");
                    } else if (result != null && result) {


                        String subject =
                                String.format(BOE_SHIPMENT_SUBMIT_MAIL_SUBJECT_CHA_ASSIGN_SUCCESS,
                                        this.getChaNameAndEmail(bTVL_WKF_BOE_SHIPMENT_TBL.getAssignedChaId()).get(0),
                                        bTVL_WKF_BOE_SHIPMENT_TBL.getModifiedDate()
                                                .toLocalDate());

                        String mailBody =
                                String.format(BOE_SHIPMENT_SUBMIT_MAIL_BODY_CHA_ASSIGN_SUCCESS,
                                        SUPPLIER_LOGIN_URL,
                                        bTVL_WKF_BOE_SHIPMENT_TBL.getInvoiceNumbers(),
                                        bTVL_WKF_BOE_SHIPMENT_TBL.getShipmentId(),
                                        bTVL_WKF_BOE_SHIPMENT_TBL.getOuName(),
                                        bTVL_WKF_BOE_SHIPMENT_TBL.getDestinationPort(),
                                        bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute1());

//                        Boolean result1 =
//                                EmailUtil.sendEmail(javaMailSender, this.getChaNameAndEmail(bTVL_WKF_BOE_SHIPMENT_TBL.getAssignedChaId()).get(1), null,
//                                        AIRTEL_BOE_FROM_EMAIL,
//                                        subject, mailBody);

                        //Static value for testing
//                        Boolean result1 =
//                                EmailUtil.sendEmail(javaMailSender, "a_hari.o.tiwary@airtel.com", null,
//                                        AIRTEL_BOE_FROM_EMAIL,
//                                        subject, mailBody);
//
//                        log.info("Email Send Result : " + result1);
                    }
                } else if (cha == null) {
                    boeResponse.getData().setPopUpMessage("CHA Re-assignment failed for some of the selected records. Please check the CHA master and try again.");
                }

            }

        }

    }


}
