package com.demo.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RSAUtil {

    private static Map<Integer, String> keyMap = new HashMap<>();  //用于封装随机产生的公钥与私钥

    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        genKeyPair();
        //加密字符串
        String message = "NEj@U#6y";
        String str = "{\"password\": \"123456\",\"phoneNum\": \"15555555566\"}";
        System.out.println("随机生成的公钥为:" + keyMap.get(0));
        System.out.println("随机生成的私钥为:" + keyMap.get(1));
        String messageEn = encrypt(str, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMTgtcBQLsii0B6/y1yQVSGw5JXgUxVOD3l8/p/cXtoldKSMTqCuB5ZuIQsuQgf6DXR4SUNXW9BbKIC75ebEtb/Eyq6i6pH+B4b/xILVsKzynjfmodGK6cmEY+9tUsPxpO/bVnCvskODJ8oMjE9jFD3UTVPpbwd+HS7qQS2CzWnwIDAQAB");
        System.out.println(str + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIxOC1wFAuyKLQHr/LXJBVIbDkleBTFU4PeXz+n9xe2iV0pIxOoK4Hlm4hCy5CB/oNdHhJQ1db0FsogLvl5sS1v8TKrqLqkf4Hhv/EgtWwrPKeN+ah0YrpyYRj721Sw/Gk79tWcK+yQ4MnygyMT2MUPdRNU+lvB34dLupBLYLNafAgMBAAECgYBewHoVa+D8o5gfmyJwFdC9ET6Ft7QkC6FeNC0aZeKcSC1RInGxaSUNd7il9FnxxJ93o3naqXvY6pcWH3AxzkrZU/ob6EXPdlDBYz5m/x7a2R0og+ywLDN/tp/QpDlTurq6+aOSuuNIO3mgsvpWJqlTWgmJk4q3OOzncp2ZOep4sQJBANFg8TAtdf/t/E+lsV8ELdkjLa+Mmf5nePv8xCU/abzpM1kKYb6aSjxSmd7g7V0IGRa6Z8KZHCvd3sJIRhvZxdkCQQCri70LYiBAHIx+1D4CwA23N1zY/IDBQw/RBJbUvkocie5MnO2sXDTF51K6n4otewkgjFyfhZKdSCQLqR3/7903AkAxZsUhj+hijV1V0YtrFspWjEVQQUVoUDOsiPQisbaaGw/Bd4Cefzr6xqaFft6ib7GzuiGXOZb7BqtzfagFFDnpAkBzb6w97cEwA0YTzrUP2rcgFHQ5W+k0o/EPwVvSO17Zx4OBMJTLSJ0Gk7xa8yLjqgihS3LEsfjQOAxxgFM8t+ePAkEAhlRJ9EmgL99t5n9lmvqD1oYYNBWHmrQ1N4KshaygQ5gkkdgA87PbeMfYKogz8ifdhA7J7Eyv+l5t25xH4UjNXg==");
        System.out.println("还原后的字符串为:" + messageDe);
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(2048, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0, publicKeyString);  //0表示公钥
        keyMap.put(1, privateKeyString);  //1表示私钥
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) {
        String result = "";
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            result = Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));

        } catch (Exception e) {
            log.error("加密失败");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * RSA私钥解密
     *
     * @param str        解密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) {
        String result = "";
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            result = new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            log.error("解密失败");
            e.printStackTrace();
        }
        return result;
    }

}


