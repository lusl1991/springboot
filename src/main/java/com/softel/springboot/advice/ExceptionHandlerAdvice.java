package com.softel.springboot.advice;

import com.softel.springboot.enums.ResultCode;
import com.softel.springboot.exception.ResultException;
import com.softel.springboot.util.JSONUtil;
import com.softel.springboot.util.Result;
import com.softel.springboot.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
@SuppressWarnings("rawtypes")
@Slf4j
public class ExceptionHandlerAdvice implements ResponseBodyAdvice {

    private ThreadLocal<Object> modelHolder = new ThreadLocal<>();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleIllegalParamException(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String tips = "参数不合法";
        if (errors.size() > 0) {
            tips = errors.get(0).getDefaultMessage();
        }
        return ResultUtils.warn(ResultCode.PARAMETER_ERROR, tips);
    }

    @ExceptionHandler(ResultException.class)
    public Result handleResultException(ResultException e, HttpServletRequest request) {
    	if(log.isDebugEnabled()){
    		log.debug("uri={} | requestBody={}", request.getRequestURI(), JSONUtil.toJson(modelHolder.get()));
    	}
        return ResultUtils.warn(e.getResultCode());
    }
    
    /**
     * 请求方法错误
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
    	if(log.isDebugEnabled()){
    		log.debug("uri={} | requestBody={}", request.getRequestURI(), JSONUtil.toJson(modelHolder.get()));
    	}
        return ResultUtils.warn(ResultCode.METHOD_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest request) {
    	if(log.isErrorEnabled()){
    		log.error("uri={} | requestBody={}", request.getRequestURI(), JSONUtil.toJson(modelHolder.get()), e);
    	}
        return ResultUtils.warn(ResultCode.WEAK_NET_WORK);
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        modelHolder.set(webDataBinder.getTarget());
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

	@Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
    		Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        modelHolder.remove();
        return body;
    }
}
