package com.demo.config;

import com.demo.annotation.SecretBody;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;


@Data
@Component
@ConfigurationProperties(prefix = "rsa")
public class RsaConfig {

    /**
     * 是否开启
     */
    private boolean enabled;
    /**
     * 是否扫描注解
     */
    private boolean scanAnnotation;
    /**
     * 扫描自定义注解
     */
    private Class<? extends Annotation> annotationClass = SecretBody.class;

    private String publicKey;

    private String privateKey;

}
