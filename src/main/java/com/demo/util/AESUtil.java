package com.demo.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * @version V1.0
 * @desc AES 加密工具类
 */
public class AESUtil {

    private static final String IV_STRING = "A-16-Byte-String";
    private static final String charset = "UTF-8";
    private static final String Key = "ahfhflkjf6846asn";

    // 加密
    public static String aesEncryptString(String content) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {
        byte[] contentBytes = content.getBytes(charset);
        byte[] keyBytes = Key.getBytes(charset);
        byte[] encryptedBytes = aesEncryptBytes(contentBytes, keyBytes);
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encryptedBytes);
    }

    /**
     * @Description: 密码加密
     * @Date: 2021/5/8
     * @return: java.lang.String
     */
    public static String aesEncryptPassword(String password) throws NoSuchPaddingException {
        try {
            return aesEncryptString(password);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 解密
    public static String aesDecryptString(String content) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {
        Decoder decoder = Base64.getDecoder();
        byte[] encryptedBytes = decoder.decode(content);
        byte[] keyBytes = Key.getBytes(charset);
        byte[] decryptedBytes = aesDecryptBytes(encryptedBytes, keyBytes);
        return new String(decryptedBytes, charset);
    }

    public static byte[] aesEncryptBytes(byte[] contentBytes, byte[] keyBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {
        return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE);
    }

    public static byte[] aesDecryptBytes(byte[] contentBytes, byte[] keyBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {
        return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE);
    }

    private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        byte[] initParam = IV_STRING.getBytes(charset);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, secretKey, ivParameterSpec);

        return cipher.doFinal(contentBytes);
    }

    /**
     * AES解密
     * 微信解密
     *
     * @param data           //密文，被加密的数据
     * @param session_key    //秘钥
     * @param iv             //偏移量
     * @param encodingFormat //解密后的结果需要进行的编码
     */
    public static String decrypt(String data, String session_key, String iv, String encodingFormat) throws Exception {
        Decoder decoder = Base64.getDecoder();
        // 被加密的数据
        byte[] encryptedBytes = decoder.decode(data);
        // 加密秘钥
        byte[] keyBytes = session_key.getBytes(encodingFormat);
        // 偏移量
        byte[] initParam = iv.getBytes(encodingFormat);
        try {
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            if (null != decryptedBytes && decryptedBytes.length > 0) {
                return new String(decryptedBytes, encodingFormat);
            }
            return null;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
        String s = null;
        try {
            s = aesEncryptPassword("123456");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }
}
