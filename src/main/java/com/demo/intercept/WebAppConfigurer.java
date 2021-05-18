package com.demo.intercept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author :lrj
 * @ClassName :WebAppConfigurer
 * @Description :
 * @date :2021/5/6 16:13
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurationSupport {

    @Bean
    LoginTokenFilter loginTokenFilter() {
        return new LoginTokenFilter();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTokenFilter())
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui.html/**", "/v2/**", "/webjars/**", "/swagger-resources/**");
        super.addInterceptors(registry);
    }

    /**
     * 解决swagger-ui 无法访问
     * 两个方法都需要重写，只加任何一个都无法生效
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * @Description: 下面三个方法是配置全局utf-8
     * @Date: 2021/5/7
     * @return: org.springframework.http.converter.HttpMessageConverter<java.lang.String>
     */
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(
                StandardCharsets.UTF_8);
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }
}
