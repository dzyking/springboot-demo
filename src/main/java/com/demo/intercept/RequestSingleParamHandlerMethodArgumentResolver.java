package com.demo.intercept;

import com.demo.annotation.RequestSingleParam;
import com.demo.entity.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Objects;

/**
 * RequestSingleParam参数解析器 实现 HandlerMethodArgumentResolver 接口
 * 从request中解析出参数
 */
@Slf4j
public class RequestSingleParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String POST = "post";
    private static final String APPLICATION_JSON = "application/json";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestSingleParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new BusinessException("参数不能为空");
        }
        String contentType = Objects.requireNonNull(request).getContentType();
        if (contentType == null || !contentType.contains(APPLICATION_JSON)) {
            log.error("解析参数异常，contentType需为{}", APPLICATION_JSON);
            throw new RuntimeException("解析参数异常，contentType需为application/json");
        }
        if (!POST.equalsIgnoreCase(request.getMethod())) {
            log.error("解析参数异常，请求类型必须为post");
            throw new RuntimeException("解析参数异常，请求类型必须为post");
        }
        RequestSingleParam requestSingleParam = parameter.getParameterAnnotation(RequestSingleParam.class);
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
        int rd;
        while ((rd = reader.read(buf)) != -1) {
            sb.append(buf, 0, rd);
        }
      /*  JSONObject jsonObject = JSONObject.parseObject(sb.toString());
        if (requestSingleParam == null) {
            throw new BusinessException("参数不能为空");
        }
        String value = requestSingleParam.value();
        return jsonObject.get(value);*/
        return null;
    }

}
