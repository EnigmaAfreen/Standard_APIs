package com.airtel.buyer.airtelboe.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Base64;

public class EncryptDecryptUtil {

    private static final Logger log = LoggerFactory.getLogger(EncryptDecryptUtil.class);

    /**
     * The Constant encryptionKey.
     */
    private static final String encryptionKey = "airteldev1234567";

    /**
     * The Constant characterEncoding.
     */
    private static final String characterEncoding = "UTF-8";

    /**
     * The Constant cipherTransformation.
     */
    private static final String cipherTransformation = "AES/CBC/PKCS5PADDING";

    /**
     * The Constant aesEncryptionAlgorithem.
     */
    private static final String aesEncryptionAlgorithem = "AES";

    /**
     * Method for Encrypt Plain String Data.
     *
     * @param plainText the plain text
     * @return encryptedText
     */
    public static String encrypt(String plainText) {
        log.info("Inside EncryptDecryptUtil ::: encrypt ::: @param String plainText :" + plainText);
        String encryptedText = "";
        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF8"));
            Base64.Encoder encoder = Base64.getEncoder();
            encryptedText = encoder.encodeToString(cipherText);

        } catch (Exception E) {
            // log.info("Encrypt Exception : " + E.getMessage(), E);
            E.printStackTrace();
        }
        log.info("Exiting from EncryptDecryptUtil ::: encrypt");
        return encryptedText;
    }

    /**
     * Method For Get encryptedText and Decrypted provided String.
     *
     * @param encryptedText the encrypted text
     * @return decryptedText
     */
    public static String decrypt(String encryptedText) {
        log.info("Inside EncryptDecrypt ::: decrypt ::: @param String encryptText :" + encryptedText);
        String decryptedText = "";
        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = decoder.decode(encryptedText.getBytes("UTF8"));
            decryptedText = new String(cipher.doFinal(cipherText), "UTF-8");

        } catch (Exception E) {
            // log.info("decrypt Exception : " + E.getMessage(), E);
            E.printStackTrace();
        }
        log.info("Exiting from EncryptDecrypt ::: decrypt");
        return decryptedText;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // // Scanner sc = new Scanner(System.in);
        // // log.info("Enter String : ");
        // // String plainString = sc.nextLine();
        // LocalDateTime today = LocalDateTime.now();
        // log.info("Current DateTime=" + today);
        // String plainString =
        // "1004" + "~" + today.getYear() + "~" + today.getMonth() + "~" +
        // today.getDayOfMonth() + "~" +
        // today.getHour() + "~" + today.getMinute() + "~" + today.getSecond();
        //
        // String encyptStr = encrypt(plainString);
        // String decryptStr = decrypt("SkFKTQx1aIckrj/jOGDTlg==");
        //
        //// log.info("Plain String : " + plainString);
        //// log.info("Encrypt String : " + encyptStr);
        //// log.info("Decrypt String : " + decryptStr);
        //
        //
        // String sampleText = String.format("%s", "OK");
        // log.info(decryptStr);
        //
        //
    }

    public static String emailEncrypt(String plainText) {
        log.info("Inside EncryptDecryptUtil ::: emailEncrypt ::: @param String plainText :" + plainText);
        String encryptedText = "";
        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF8"));
            Base64.Encoder encoder = Base64.getEncoder();
            encryptedText = encoder.encodeToString(cipherText);
            encryptedText = URLEncoder.encode(encryptedText, "UTF-8");
        } catch (Exception E) {
            // log.info("Encrypt Exception : " + E.getMessage(), E);
            E.printStackTrace();
        }
        log.info("Exiting from EncryptDecryptUtil ::: emailEncrypt");
        return encryptedText;
    }

}
