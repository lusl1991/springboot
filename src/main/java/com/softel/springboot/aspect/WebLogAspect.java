package com.softel.springboot.aspect;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.softel.springboot.util.IPUtil;
import com.softel.springboot.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Order(Integer.MAX_VALUE)
@Slf4j
public class WebLogAspect {
	
    @Pointcut("execution(public * com.softel.springboot.web.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        
        Map<String, String> headers = RequestUtils.getRequestHeaders(request);
		Map<String, String> params = RequestUtils.getRequestParams(request);
		
		String url = request.getRequestURL().toString();
		String http_method = request.getMethod();
		String ip = IPUtil.getIpAddr(request);
		String class_method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
		
		log.trace("请求地址:{}, 请求方法:{}, 请求头:{}, 请求参数:{}, ip地址:{}, 处理方法: {}", url, http_method, headers, params, ip, class_method);

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
//        log.info("RESPONSE : " + ret);
    }

}