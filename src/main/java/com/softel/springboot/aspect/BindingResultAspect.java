package com.softel.springboot.aspect;

import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import com.softel.springboot.enums.ResultCode;
import com.softel.springboot.model.ErrorInfo;
import com.softel.springboot.util.I18n;
import com.softel.springboot.util.ResultUtils;

@Aspect
@Component
public class BindingResultAspect {
	
	private static final Logger log = LoggerFactory.getLogger(BindingResultAspect.class);
	
	@Autowired
	private I18n i18n;
	
	@Pointcut("@annotation(com.softel.springboot.annotation.BindingResultAnnotation)")
	public void bingdindResultAspect() {
		
	}

	@Around("BindingResultAspect.bingdindResultAspect()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
		BindingResult bindingResult = null;
        for(Object object : joinPoint.getArgs()){
            if(object instanceof BindingResult){
                bindingResult = (BindingResult) object;
            }
        }
        if(bindingResult != null){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if(fieldErrors.size() > 0){
            	List<ErrorInfo> list = new ArrayList<>();
            	for(FieldError fieldError : fieldErrors){
            		ErrorInfo errorInfo = new ErrorInfo();
            		String objectname = fieldError.getObjectName();
            		String fieldname = fieldError.getField();
            		String errormsg = i18n.getMessage(fieldError.getDefaultMessage());
            		errorInfo.setFieldname(fieldname);
            		errorInfo.setErrormsg(errormsg);
            		list.add(errorInfo);
            		log.info("{} 对象中 {} 参数校验错误: {}", objectname, fieldname, errormsg);
            	};
            	return ResultUtils.warn(ResultCode.PARAMETER_ERROR, list);
            }
        }
        return joinPoint.proceed();
	}
	
}
