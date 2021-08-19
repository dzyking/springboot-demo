package com.demo.intercept;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.config.RsaConfig;
import com.demo.entity.SecretHttpMessage;
import com.demo.entity.exception.BusinessException;
import com.demo.util.SecretUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.BindException;
import java.nio.charset.Charset;

/**
 * @author dzy
 * @date 2021/7/23
 * @desc
 */
@Slf4j
@ControllerAdvice
@ConditionalOnProperty(prefix = "rsa", name = "enabled", havingValue = "true")
@EnableConfigurationProperties({RsaConfig.class})
public class SecretRequestAdvice extends RequestBodyAdviceAdapter {

    @Autowired
    private RsaConfig rsaConfig;

    /**
     * 是否支持加密消息体
     *
     * @param methodParameter methodParameter
     * @return true/false
     */
    private boolean supportSecretRequest(MethodParameter methodParameter) {
        if (!rsaConfig.isScanAnnotation()) {
            return true;
        }
        //判断class是否存在注解
        if (methodParameter.getContainingClass().getAnnotation(rsaConfig.getAnnotationClass()) != null) {
            return true;
        }
        //判断方法是否存在注解
        return methodParameter.getMethodAnnotation(rsaConfig.getAnnotationClass()) != null;
    }


    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        //如果有注解
        boolean supportSafeMessage = supportSecretRequest(parameter);
        if (supportSafeMessage) {
            String httpBody = decryptBody(inputMessage);
            if (httpBody == null) {
                throw new BindException("解密失败");
            }
            //返回处理后的消息体给messageConvert
            return new SecretHttpMessage(new ByteArrayInputStream(httpBody.getBytes()), inputMessage.getHeaders());
        } else {
            return inputMessage;
        }
    }

    /**
     * 解密
     *
     * @param inputMessage 消息体
     * @return 明文
     */
    private String decryptBody(HttpInputMessage inputMessage) throws IOException {
        InputStream encryptStream = inputMessage.getBody();
        String encryptBody = StreamUtils.copyToString(encryptStream, Charset.defaultCharset());
        return SecretUtil.rsaDecrypt(encryptBody, rsaConfig.getPrivateKey());
    }

    /**
     * AES+RSA组合解密
     *
     * @param inputMessage 消息体
     * @return 明文
     */
    private String decryptBodyCombination(HttpInputMessage inputMessage) {
        String original;
        try {
            InputStream encryptStream = inputMessage.getBody();
            String encryptBody = StreamUtils.copyToString(encryptStream, Charset.defaultCharset());
            JSONObject jsonObject = JSON.parseObject(encryptBody);
            String key = String.valueOf(jsonObject.get("key"));
            String content = String.valueOf(jsonObject.get("content"));
            // 1.先使用RSA私钥解密出AESKey
            String AESKey = SecretUtil.rsaDecrypt(key, rsaConfig.getPrivateKey());
            log.info("AESKey:" + AESKey);
            // 2.使用AESKey解密内容
            original = SecretUtil.aesDecrypt(content, AESKey);
            log.info("原文:" + original);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("解密失败");
        }

        return original;
    }
}
