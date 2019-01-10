package com.softel.springboot.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import com.softel.springboot.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 拦截请求无效
 * @author Administrator
 *
 */
@Slf4j
@Component
@ControllerAdvice
public class LogRequestBodyAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
     	Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
    		Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
    		Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
        	Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = parameter.getMethod();
        String classMappingUri = getClassMappingUri(method.getDeclaringClass());
        String methodMappingUri = getMethodMappingUri(method);
        if (!methodMappingUri.startsWith("/")) {
            methodMappingUri = "/" + methodMappingUri;
        }
        if (log.isDebugEnabled()) {
        	log.debug("uri={} | requestBody={}", classMappingUri + methodMappingUri, JSONUtil.toJson(body));
        }
        return body;
    }

    private String getMethodMappingUri(Method method) {
        RequestMapping methodDeclaredAnnotation = method.getDeclaredAnnotation(RequestMapping.class);
        return methodDeclaredAnnotation == null ? "" : getMaxLength(methodDeclaredAnnotation.value());
    }

    private String getClassMappingUri(Class<?> declaringClass) {
        RequestMapping classDeclaredAnnotation = declaringClass.getDeclaredAnnotation(RequestMapping.class);
        return classDeclaredAnnotation == null ? "" : getMaxLength(classDeclaredAnnotation.value());
    }

    private String getMaxLength(String[] strings) {
        String methodMappingUri = "";
        for (String string : strings) {
            if (string.length() > methodMappingUri.length()) {
                methodMappingUri = string;
            }
        }
        return methodMappingUri;
    }
}
