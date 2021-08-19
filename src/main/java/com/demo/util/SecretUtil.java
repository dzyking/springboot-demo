package com.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @desc AES+RSA混合加密
 * 客户端使用随机产生的16位AES的密钥对参数进行AES加密,通过使用RSA公钥对AES密钥进行公钥加密.
 * <p>
 * 服务端对加密后的AES密钥进行RSA私钥解密,拿到密钥原文,对加密后的参数进行AES解密,拿到原始内容.
 */
@Slf4j
public class SecretUtil {

    private static final String SECRET = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String RSAPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlYIwxN8f77oZlXBOuhzj+qym8NckvFLJPhtihMLL0xEpwQqK8zvOv7DwUwcfW/JrKBG/oOZbPW6OAUjsWQTBp8imJlA43rTMXTPsAwTllM2aGZdWXw2sYsgaq9864b8KjTuXCSewUWZJoQ3aAgoJDLSOpP7SOd/fpcLc/AjXzCB2auYRR4G3+W+39akGwHOqvnQzz2dwzlekhVtkywfLzt0nll8W4CkGEI+pq9Gy5R5XViJsvj7tLCy3DtBWHcKT0/WmoEOojqESjsY09SWWOKG+KjJfrABWJK4r1OcVrGWUkr/VRhpDGzPXD9LiU05efayK6BHJsX5RMBDkIZDlHQIDAQAB";
    private static final String RSAPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCVgjDE3x/vuhmVcE66HOP6rKbw1yS8Usk+G2KEwsvTESnBCorzO86/sPBTBx9b8msoEb+g5ls9bo4BSOxZBMGnyKYmUDjetMxdM+wDBOWUzZoZl1ZfDaxiyBqr3zrhvwqNO5cJJ7BRZkmhDdoCCgkMtI6k/tI539+lwtz8CNfMIHZq5hFHgbf5b7f1qQbAc6q+dDPPZ3DOV6SFW2TLB8vO3SeWXxbgKQYQj6mr0bLlHldWImy+Pu0sLLcO0FYdwpPT9aagQ6iOoRKOxjT1JZY4ob4qMl+sAFYkrivU5xWsZZSSv9VGGkMbM9cP0uJTTl59rIroEcmxflEwEOQhkOUdAgMBAAECggEASKGsy5eRzkZcc2rR9DxgzMDfgRUjtZGKTm/DVrfDiVBHNo7Gng+qCxoMRmmpZGPKhvbIX4RJQvjYZ+tjVoOnCLnDbc1/M0ImHglBWN4iIj4X5OLOhPMBkiOgQWr7EfxbRe1zHHF5iLHmhyVRibgSWWfF/IYG3zLRVZfq/03UeEdxP2JineQGKRC1HThIWtETMQYi/CC6jUT56BJfXRfz+TsAMflFU1BKLaJtJf15YXe49mal+5Ais60PKoA+042Zki4WXnqWST09qFa2S2BX6IGkB/A4Yb9YhxzSkPW7P53kaGqqmcgwKF7TbOAbSCyh7VemQX10jooXE4twS6m3wQKBgQD5l8TUiQ0985oOe0OaldJ6uKvideAzOJwm2fZsHcgssCNE6RU93+s+5Z5qz7H3CkL0SvSa3U9ULZPP6au8Ltcnn7A/am0zXbFsBa7dLwTJeJGXf97WleuAMnxax+0sG133OwUP5UeUEVK9iFjveC8EL2Xe4DQE5lzkt38JG1k+6QKBgQCZWLR6iuiuNtwgFEB8qD8CsDPexHzxaEII3J4MgmnDKj+nvyzf5SnPGLOAsZ2j5vD7E4tg33Sa2GwDTqNDj8dPlL8J3oYoOhcbaykj0G5h3ckApvq3HIxd3MujTQKP/5BvYSklSL4l9MXHb52E/Eh0vmpHi362C/9uQkp0k6tcFQKBgQDp1GvMidFkFtJfes+tBWu5GUW+1ZwDoKd6Jn0ccRJV+3YvWwZRf3RO6qPtgUNDI9PyNj7Oz0NAo6p1AH+pv2BGeO6Pms9PbhmIEBLwmNotPc14V42XD8EfyyyBiAgEaGcx9ZMafzz91PTlJ9XaE4hIrXcMxJM3e/HPPLHBAGQt8QKBgD3HafaQrgs52NcnsY9HLROFL3CJEpkjWq89SoNZqwhQphKLx6VwKrbfibnIVO0qZl2Q0y8090JG/MUcUlQuwaqDD7AHMV9Vf+ew9YMTPtU4x/j2TL2mx7os8PGaXfTrjiIBL9VwiAK8qU2e3wQrq3k1GZY3UdY/4SlnlZ1/vKK9AoGBAOLSza86VOlyldqqEG30/9Zuyx2s20tbu421YVOzMknIVfDwhHEnCKSZzjKZgruGlF4kTU6TuvSC/IVLbyHdgTmPEpCRh/DvfyS9WH3G4VXWD1B3Nu+DAE0gQLghKjNIkDRewhC27WLUmOZo1L2Ph28vfxUZorF2fEMY+MKvLlSO";

    public static void main(String[] args) throws Exception {
        //post请求参数
        String param = "4E1B9C6D7C6BFC772E01D4CE1E9DE629";
        //RAS加密后的AESKey
        String key = "NT2Ee0iZbQ1FJHqWwsxPTk8NQ/FNFlPUJQ2GbX4WxE/w1r2YJZjqK+bEb5TpBUFPvue4774te0fD\n" +
                "ZJ5L7pAG9t97Tgj4md4FBHtj3MVQbMHFugTeI061FdtLh1doPGE6uSf6+OxacsQjMLePVDl10oBI\n" +
                "8wJMdtdmU70Nh1qYL2wmZSZX+E3ifwEOuUvhhztbtLgK+qCjSty3gZRjEf6j6qV5QLQ8gAoZhlwJ\n" +
                "qqRNdqvwsYKl2G1JiAxpEM/2Swk90v+znvZps1oeHOHfL7KpOQs58Cz7MbT1RpbGF6SuIY1nruaI\n" +
                "1OldIBc1KM6FGEVTcS1q/ij+gJg9cB9JJjF9yQ==";
        //AES加密后的密文
        String content = "yN4xGlksley6qG5y57juxcb1FBMlra6Lgc/NRRW0/HkgY6N0xFRgz3/8XylRAC9vxoK292rCLLQnGcOXd9K3cQ==";

        // 1.先使用RSA私钥解密出AESKey
        String AESKey = rsaDecrypt(key, RSAPrivateKey);
        log.info("AESKey:" + AESKey);
        // 2.使用AESKey解密内容
        String original = aesDecrypt(content, AESKey);
        log.info("param:" + original);

        /*String rsaEncrypt = rsaEncrypt(param, RSAPublicKey);
        log.info("rsaEncrypt: " + rsaEncrypt);
        String rsaDecrypt = rsaDecrypt(rsaEncrypt, RSAPrivateKey);
        log.info("rsaDecrypt: " + rsaDecrypt);*/
    }

    /**
     * RSA公钥加密
     *
     * @param str 加密字符串
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String rsaEncrypt(String str) {
        String result = "";
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(RSAPublicKey);
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
    public static String rsaDecrypt(String str, String privateKey) {
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

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器
        keyPairGen.initialize(2048, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        log.info("RSA公钥:" + publicKeyString);
        log.info("RSA私钥:" + privateKeyString);
    }

    /**
     * AES加密ECB模式PKCS5Padding填充方式
     *
     * @param str 字符串
     * @param key 密钥
     * @return 加密字符串
     * @throws Exception 异常信息
     */
    public static String aesEncrypt(String str, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, SECRET));
        byte[] doFinal = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.encodeBase64(doFinal));
    }

    /**
     * AES解密ECB模式PKCS7Padding填充方式
     *
     * @param str 字符串
     * @param key 密钥
     * @return 解密字符串
     * @throws Exception 异常信息
     */
    public static String aesDecrypt(String str, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, SECRET));
        byte[] doFinal = cipher.doFinal(Base64.decodeBase64(str));
        return new String(doFinal);
    }

}
