package com.demo.intercept;

import com.demo.annotation.RequestSingleParamHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Description : web请求拦截器
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
                .excludePathPatterns("/user/register", "/user/smsCode", "/user/login")
                .excludePathPatterns("/swagger-ui.html/**", "/v2/**", "/webjars/**", "/swagger-resources/**")
                .excludePathPatterns("/public/**", "/test/**");
        super.addInterceptors(registry);
    }

    /**
     * 解决swagger-ui 无法访问
     * 两个方法都需要重写，只加任何一个都无法生效
     *
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * @author: lrj
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


    /**
     * 自定义注解参数数解析器
     *
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RequestSingleParamHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    public RequestSingleParamHandlerMethodArgumentResolver requestStringParamHandlerMethodArgumentResolver() {
        return new RequestSingleParamHandlerMethodArgumentResolver();
    }

}
