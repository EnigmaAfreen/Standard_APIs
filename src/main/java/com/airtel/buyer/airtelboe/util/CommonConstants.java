package com.airtel.buyer.airtelboe.util;

import java.util.UUID;

public class CommonConstants {

    public static final String FIELD_IS_MANDATORY = "Field is mandatory, Please provide mandatory fields.";

    public static final String SCM_LOB_ERROR_CODE = "ABOELOB";

    public static final String SCM_OU_LIST_ERROR_CODE = "ABOEOU";

    public static final String SCM_DASHBOARD_ERROR_CODE = "ABOESCMD";

    public static final String EPCG_MASTER_ADD_ERROR_CODE = "ABOEEMA";

    public static final String EPCG_MASTER_EDIT_ERROR_CODE = "ABOEEME";

    public static final String EPCG_MASTER_END_DATE_ERROR_CODE = "ABOEEMED";

    public static final String WPC_MASTER_ADD_ERROR_CODE = "ABOEWMA";

    public static final String WPC_MASTER_EDIT_ERROR_CODE = "ABOEWME";

    public static final String WPC_MASTER_END_DATE_ERROR_CODE = "ABOEWMED";

    public static final String ITEM_MASTER_ADD_ERROR_CODE = "ABOEIMA";

    public static final String ITEM_MASTER_EDIT_ERROR_CODE = "ABOEIME";

    public static final String ITEM_MASTER_END_DATE_ERROR_CODE = "ABOEIMED";

    public static final String PROTESTED_BOE_ACTION_ERROR_CODE = "ABOEPBA";

    public static final String OFFLINE_CCO_APPROVE_ERROR_CODE = "ABOEOCA";

    public static final String OFFLINE_CCO_RETRIGGER_ERROR_CODE = "ABOEOCR";

    public static final String AIRTEL_BOE_FROM_EMAIL = "InvoicePortal@airtel.com";

    public static final String BOE_SHIPMENT_SUBMIT_MAIL_SUBJECT_CCO = "Duty Approval - BOE #%s  PO #%s";

    public static final String BOE_SHIPMENT_SUBMIT_MAIL_BODY_CCO = "<p>Dear Team,</p><br/>\n" +
            "<p>The following Shipment BOE was filed and ready for Duty payment. Requset you to check and approve the Duty payment to proceed further.</p>\n" +
            "<p><strong>Shipment Details</strong></p>\n" +
            "<p>Invoice # : %s</p>\n" +
            "<p>PO # : %s</p>\n" +
            "<p>Partner Name : %s</p>\n" +
            "<p>CHA Name : %s</p>";

    public static final String MULTIPLE_EMAILS_FORMAT_REGEX = "^(\\s?[^\\s,]+@[^\\s,]+\\.[^\\s,]+\\s?,)*(\\s?[^\\s,]+@[^\\s,]+\\.[^\\s,]+)$";

    public static final String EMAIL_FORMAT_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String CCO_APPROVE_ERROR_CODE = "ABOECA";

    public static final String CCO_REJECT_ERROR_CODE = "ABOECR";

    public static final String CCO_HOLD_ERROR_CODE = "ABOECH";

    public static final String CCO_UNHOLD_ERROR_CODE = "ABOECU";

    public static final String IP_APPROVE_ERROR_CODE = "ABOEIA";

    public static final String IP_REJECT_ERROR_CODE = "ABOEIR";

    public static final String IP_RFI_ERROR_CODE = "ABOEIRFI";

    public static final String BOE_IP_REJECT_MAIL_SUBJECT = "";

    public static final String BOE_IP_REJECT_MAIL_BODY = "";

    public static final String SCM_STAGE2_SUBMIT_ERROR_CODE = "ABOESST2";

    public static final String SCM_STAGE4_APPROVE_ERROR_CODE = "ABOES4AS";
    public static final String SCM_STAGE4_REJECT_ERROR_CODE = "ABOES4RS";

    public static final String SHIPMENT_ID_NOT_PRESENT = "Shipment Id is mandatory";
    public static final String REJ_REASON_NOT_PRESENT = "Reject Reason is mandatory";
    public static final String REJ_REASON_DESC_NOT_PRESENT = "Rejection description is mandatory";

    public static final String CHECK_MANDATORY_FAILED = "Mandatory field check failed";

    public static final String CHECK_MANDATORY_DOCS_FAILED = "Mandatory document check failed";

    public static final String UPLOADED_DOC_ERR_MSG = "Please upload PDF, PNG, JPG file only with max. file size 6mb";

    public static String generateTraceId(String appNode) {
        String uniqueID = UUID.randomUUID().toString() + "-" + appNode;
        return uniqueID;
    }

    public static final String FTA_NODE_NOT_PRESENT = "FTA Details not submitted";

    public static final String STAGE_BUCKET_MATCH_FAILED = "Stage and Bucket match failed";
    public static final String CHA_REASSIGNMENT_ERROR_CODE = "ABOECHARE";

       public static final String WPC_INFO_DASHBOARD_ERROR_CODE = "ABOEWpcInf";

    public static final String WPC_SUBMIT_ERROR_CODE = "ABOEWpcSub";
    public static final String DD_STATUS_DASHBOARD_ERROR_CODE = "ABOEDdS";

    public static final String SCM_RFI_RESPONSE_ERROR_CODE = "ABOESRR";

    public static final String WPC_INQUIRY_ERROR_CODE = "ABOEWPC";

    public static final String SHIPMENT_STATUS_REPORT_ERROR_CODE = "ABOESSR";

    public static final String EPCG_INQUIRY_ERROR_CODE = "ABOEEPCG";
    
}
