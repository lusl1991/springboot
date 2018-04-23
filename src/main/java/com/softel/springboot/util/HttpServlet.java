package com.softel.springboot.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

@Component
public class HttpServlet {
	
	public ServletRequestAttributes getServletRequestAttributes(){
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		return sra;
	}
	
	public RequestContext getRequestContext(){
		return new RequestContext(getHttpRequest());
	}
	
	public HttpServletRequest getHttpRequest(){
		ServletRequestAttributes attr=getServletRequestAttributes();
		return attr.getRequest();
	}
	
	public HttpServletResponse getHttpResponse(){
		ServletRequestAttributes attr=getServletRequestAttributes();
		return attr.getResponse();
	}
	
	public String getI18nMessage(String key){
		return getRequestContext().getMessage(key);
	}
	
	public String getLocale(){
		return new Language().getLocale();
	}

}
