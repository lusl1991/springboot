package com.softel.springboot.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import com.softel.springboot.util.IPUtil;
import com.softel.springboot.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@WebFilter(filterName = "请求过滤器", urlPatterns = "/*",
	initParams={
			@WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.icon")// 忽略资源  
	}
)
@Order(2)//filter执行顺序，值越小，越先执行
@Slf4j
public class RequestFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String ip = IPUtil.getIpAddr(httpServletRequest);
		String url = httpServletRequest.getRequestURI();
		String params = JSONUtil.toJson(request.getParameterMap());
		if(log.isDebugEnabled()){
			log.debug("ip = {}, url = {}, params = {}", ip, url, params);
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
}
