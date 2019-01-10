package com.softel.springboot.util;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

@Component
public class Language {
	
	@Autowired
	private HttpServlet httpServlet;
	
	@Autowired
	private Cookies cookies;
	
	@Value(value = "${support.language}")
	private String support_language;
	
	private static String DEFAULT_LANGUAGE = "en_US";
	
	private String[] languages = null;
	
	@PostConstruct
	public void init(){
		this.languages = StringUtils.isEmpty(support_language) ? new String[]{DEFAULT_LANGUAGE} : support_language.split(",");
	}
	
	public String getLocale(){
		String current_language = getLanguage();
		for(String language : languages){
			if(current_language.equals(language)){
				return current_language;
			}
		}
		return DEFAULT_LANGUAGE;
	}
	
	private String getLanguage(){
		String language = cookies.getCookieValue("language", httpServlet.getHttpRequest());
		return language == null ? DEFAULT_LANGUAGE : language;
	}
	
}

