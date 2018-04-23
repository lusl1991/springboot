package com.softel.springboot.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;

public class IPUtil {

	/**
	 * 获取客户端ip地址
	 * @param res
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest res) {
	     String ipAddress ="";
	     ipAddress = res.getHeader("x-forwarded-for");
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	    	 ipAddress = res.getHeader("Proxy-Client-IP");
	     }
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	         ipAddress = res.getHeader("WL-Proxy-Client-IP");
	     }
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	    	 ipAddress = res.getRemoteAddr();
	    	 if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
	    		 //根据网卡取本机配置的IP
	    		 InetAddress inet=null;
	    		 try {
	    			 inet = InetAddress.getLocalHost();
	    		 } catch (UnknownHostException e) {
	    			 e.printStackTrace();
	    		 }
	    		 ipAddress= inet.getHostAddress();
	    	 }
	     }
	     
	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
	         if(ipAddress.indexOf(",")>0){
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
	         }
	     }
	     return ipAddress;
	}
}
