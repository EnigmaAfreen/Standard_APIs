package com.airtel.buyer.airtelboe.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class EmailUtil {

    /**
     * method to validate email
     *
     * @param email
     * @return
     */
    public static boolean validateEmail(String email) {
        log.info("Entering validate Email :: email Id :: " + email);
        if (email.contains(",")) {
            return validateEmailIds(email);
        } else {
            String regex = CommonConstants.EMAIL_FORMAT_REGEX;
            // String regex =
            // "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            Pattern pattern = Pattern.compile(regex);
            if (email != null) {
                Matcher matcher = pattern.matcher(email);
                log.info(email + " : " + matcher.matches());
                return matcher.matches();
            } else {
                log.info("email doesn't matched!");
                return false;
            }
        }
    }

    /**
     * method used to valid multiple email ids
     *
     * @param emailIds
     * @return
     */
    public static boolean validateEmailIds(String emailIds) {
        String regex = CommonConstants.MULTIPLE_EMAILS_FORMAT_REGEX;
        // String regex =
        // "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        if (emailIds != null) {
            Matcher matcher = pattern.matcher(emailIds);
            log.info(emailIds + " : " + matcher.matches());
            return matcher.matches();
        } else {
            log.info("MULTIPLE_EMAILS_FORMAT_REGEX Failed");
            return false;
        }
    }

    private static Boolean areAllFieldsValid(String recipient, StringBuilder cc, String sender, String subject,
                                             String body) {

        if (StringUtils.isBlank(recipient) || StringUtils.isBlank(sender) || StringUtils.isBlank(subject)
                || StringUtils.isBlank(body)) {
            return Boolean.FALSE;
        }

        if (!EmailUtil.validateEmailIds(recipient) || !EmailUtil.validateEmail(sender)) {
            return Boolean.FALSE;
        }

        if (cc != null && !StringUtils.isBlank(cc) && !EmailUtil.validateEmailIds(cc.toString())) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public static Boolean sendEmail(JavaMailSender javaMailSender, String recipient, StringBuilder cc, String sender,
                                    String subject, String body) {

        try {
            log.info("=====SENDING EMAIL=====");
            log.info("Sender : " + sender);
            log.info("Recipent : " + recipient);
            log.info("cc : " + cc);
            log.info("subject : " + subject);
            log.info("cc : " + body);
            log.info("=====SENDING EMAIL=====");

            if (!areAllFieldsValid(recipient, cc, sender, subject, body)) {
                log.info("All fields valid :: FALSE");
                return Boolean.FALSE;
            }

            MimeMessage msg = javaMailSender.createMimeMessage();
            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setFrom(sender);

            // helper.setTo(InternetAddress.parse(recipient)); // uncomment for prod
            helper.setTo("a_nishant.sinha@airtel.com"); // only for testing purpose

            if (!StringUtils.isBlank(cc)) {
                helper.setCc(InternetAddress.parse(cc.toString()));
            }
            helper.setSubject(subject);
            // true = text/html
            helper.setText(body, true);
            javaMailSender.send(msg);
            return Boolean.TRUE;
        } catch (MessagingException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public static boolean sendEmailWithOneAttachmentBoot(JavaMailSender mailSender, String to, String cc, String sender, String subject, String body, String fileToAttach) {
        boolean isMailSent = false;

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(sender));

            helper.setTo(new InternetAddress(to));// uncomment for prod
//             helper.setTo(new InternetAddress("a_nishant.sinha@airtel.com"));// only for testing purpose

            if (!StringUtils.isBlank(cc)) {
                helper.setCc(InternetAddress.parse(cc));
            }
            helper.setSubject(subject);
            helper.setText(body, true);
            log.info("Attaching file in email:" + fileToAttach);
            FileSystemResource file = new FileSystemResource(fileToAttach);
            helper.addAttachment(file.getFilename(), file);
            mailSender.send(message);
            isMailSent = true;
        } catch (MessagingException e) {
            e.printStackTrace();
            isMailSent = false;
        }
        return isMailSent;
    }

    public static boolean sendEmailWithMultipleAttachmentsBoot(JavaMailSender mailSender, String to, String cc, String sender, String subject, String body, List<String> filesList) {
        boolean isMailSent = false;

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(sender));

            helper.setTo(new InternetAddress(to));// uncomment for prod
//             helper.setTo(new InternetAddress("a_nishant.sinha@airtel.com"));// only for testing purpose

            if (!StringUtils.isBlank(cc)) {
                helper.setCc(InternetAddress.parse(cc));
            }
            helper.setSubject(subject);
            helper.setText(body, true);

            if (filesList != null) {
                for (String fileToAttach : filesList) {
                    log.info("Attaching file in email:" + fileToAttach);
                    FileSystemResource file = new FileSystemResource(fileToAttach);
                    helper.addAttachment(file.getFilename(), file);
                }
            }

            mailSender.send(message);
            isMailSent = true;
        } catch (MessagingException e) {
            e.printStackTrace();
            isMailSent = false;
        }
        return isMailSent;
    }

}
