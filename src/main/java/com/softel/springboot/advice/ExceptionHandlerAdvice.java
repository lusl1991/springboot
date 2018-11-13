package com.softel.springboot.advice;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import com.softel.springboot.enums.ResultCode;
import com.softel.springboot.exception.ResultException;
import com.softel.springboot.model.ErrorInfo;
import com.softel.springboot.util.I18n;
import com.softel.springboot.util.JSONUtil;
import com.softel.springboot.util.Result;
import com.softel.springboot.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionHandlerAdvice {
	
	@Autowired
	private I18n i18n;

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
    
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest request) {
    	if(log.isErrorEnabled()){
    		log.error("uri={} | requestBody={}", request.getRequestURI(), JSONUtil.toJson(modelHolder.get()), e);
    	}
    	
    	// 不支持的请求方法异常
    	if (e instanceof HttpRequestMethodNotSupportedException) {
    		return ResultUtils.warn(ResultCode.METHOD_ERROR);
    	}
    	
        return ResultUtils.warn(ResultCode.WEAK_NET_WORK);
    }
    
    /**
     * 参数校验异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e, HttpServletRequest request) {
    	BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    	List<ErrorInfo> list = new ArrayList<>();
    	for (FieldError fieldError : fieldErrors) {
    		ErrorInfo errorInfo = new ErrorInfo();
    		String objectname = fieldError.getObjectName();
    		String fieldname = fieldError.getField();
    		String errormsg = i18n.getMessage(fieldError.getDefaultMessage());
    		errorInfo.setObjectname(objectname);
    		errorInfo.setFieldname(fieldname);
    		errorInfo.setErrormsg(errormsg);
    		list.add(errorInfo);
    		if (log.isInfoEnabled()) {
    			log.info("{}对象中{}参数校验错误:{}", objectname, fieldname, errormsg);
    		}
    	};
    	
    	if(log.isErrorEnabled()){
    		log.error("uri={} | requestBody={}", request.getRequestURI(), JSONUtil.toJson(modelHolder.get()));
    	}
    	
    	return ResultUtils.warn(ResultCode.PARAMETER_ERROR, list);
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        modelHolder.set(webDataBinder.getTarget());
    }

}