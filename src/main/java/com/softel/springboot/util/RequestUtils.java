package com.softel.springboot.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

	/**
	 * 获取request请求头
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRequestHeaders(HttpServletRequest request) {
	    Map<String, String> map = new HashMap<String, String>();
	    Enumeration<String> enumeration = request.getHeaderNames();
	    while (enumeration.hasMoreElements()) {
	        String key = (String) enumeration.nextElement();
	        String value = request.getHeader(key);
	        map.put(key, value);
	    }
	    return map;
	}
	
	/**
	 * 获取请求参数
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRequestParams(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> enumeration = request.getParameterNames();  
		while (enumeration.hasMoreElements()) {  
			 String key = (String) enumeration.nextElement();
		     String value = request.getParameter(key);
		     map.put(key, value);
		}
		return map;
	}
	
}