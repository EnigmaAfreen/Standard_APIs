package com.airtel.buyer.airtelboe.util;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_WKF_BOE_SHIPMENT_TBL;
import lombok.extern.slf4j.Slf4j;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_MST_BOE_CHA_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlMstBoeChaTblRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.UUID;

@Slf4j
public class BoeUtils {

    @Autowired
    BtvlMstBoeChaTblRepository btvlMstBoeChaTblRepository;

    public static String generateTraceId(String appNode) {
        String uniqueID = UUID.randomUUID().toString() + "-" + appNode;
        return uniqueID;
    }

    public static String getCCoActionMailToken(BigDecimal shipmentId, String olmId, String emailId) {
        log.info("Entering method :: getCCoActionMailToken");
        String encryptedMailToken = null;

        try {
            String str = shipmentId + "~" + olmId + "~" + emailId + "~" + new Timestamp(System.currentTimeMillis());

            log.info("String generated :: " + str);

            encryptedMailToken = EncryptDecryptUtil.encrypt(str);

            log.info("Encrypted Mail Token :: " + encryptedMailToken);

        } catch (Exception e) {
            log.info("Exception raised :: " + e.getMessage());
            e.printStackTrace();
        }

        log.info("Exiting method :: getCCoActionMailToken");
        return encryptedMailToken;
    }

    public static String getCCoActionMailBody(BTVL_WKF_BOE_SHIPMENT_TBL bTVL_WKF_BOE_SHIPMENT_TBL, String chaName, String emailApprovalURL) {
        log.info("Entering method :: getCCoActionMailBody");
        String mailBody = null;
        String encryptedMailToken = null;

        try {

            encryptedMailToken = URLEncoder.encode(bTVL_WKF_BOE_SHIPMENT_TBL.getAttribute4(), "UTF-8");

            mailBody =
                    String.format(CommonConstants.BOE_SHIPMENT_SUBMIT_MAIL_BODY_CCO,
                            bTVL_WKF_BOE_SHIPMENT_TBL.getInvoiceNumbers(), bTVL_WKF_BOE_SHIPMENT_TBL.getPoNo(),
                            bTVL_WKF_BOE_SHIPMENT_TBL.getVendorName(), chaName);

            String actionLinks =
                    "<p> Please click on one of the  following choices to automatically initiate action in the Supplier Invoice Digitization Portal.</p><br/>" +
                            "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"800\">\n" + "            <tr>\n" +
                            "             <td valign=\"top\" align=\"left\" style=\"font-size:14px;font-weight:700;\">\n" +
                            "                                                <font face=\"'Roboto', Arial, sans-serif\"><a href='%s' target='_blank'>APPROVE</a></font>\n" +
                            "                                                </td>\n" +
                            "                                                  <td valign=\"top\" align=\"left\" style=\"font-size:14px;font-weight:700;\">\n" +
                            "                                                  <font face=\"'Roboto', Arial, sans-serif\"><a href='%s' target='_blank'>REJECT</a></font>\n" +
                            "                                                </td>\n" +
                            //                "                                                  <td valign=\"top\" align=\"left\" style=\"font-size:14px;font-weight:700;\">\n" +


                            //                "                                                  <font face=\"'Roboto', Arial, sans-serif\"><a href='%s' target='_blank'>HOLD</a></font>\n" +


                            //                "                                                </td>\n" +

                            "                                                </tr>\n" +
                            "                                                </table>\n" + "<p>Thanks,<br /> Team Airtel</p>";

            String generalParamLink = "?q=%s&&a=%s";

            String url = emailApprovalURL;

            String encryptedCcoApprAction = EncryptDecryptUtil.emailEncrypt("ccoaprv");
            String encryptedCcoRejAction = EncryptDecryptUtil.emailEncrypt("ccorjct");
            //            String encryptedCcoHoldAction = EncryptDecryptUtil.emailEncrypt("ccohold");

            String ccoApprLink = url + String.format(generalParamLink, encryptedMailToken, encryptedCcoApprAction);
            String ccoRejLink = url + String.format(generalParamLink, encryptedMailToken, encryptedCcoRejAction);
            //            String ccoHoldLink = url + String.format(generalParamLink, encryptedMailToken, encryptedCcoHoldAction);

            //            actionLinks = String.format(actionLinks, ccoApprLink, ccoRejLink, ccoHoldLink);

            actionLinks = String.format(actionLinks, ccoApprLink, ccoRejLink);

            mailBody = mailBody + actionLinks;

        } catch (Exception e) {
            log.info("Exception raised :: " + e.getMessage());
            e.printStackTrace();
        }

        log.info("Exiting method :: getCCoActionMailBody");
        return mailBody;
    }

}
