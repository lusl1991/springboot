package com.softel.springboot.util;

import java.util.Locale;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class I18n {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private Language language;
	
	private static I18n i18n;
	
	private static final String DEFAULT_MESSAGEKEY = "messagekey.notexisted";
	
	@PostConstruct
	public void init(){
		i18n = this;
		i18n.messageSource = this.messageSource;
	}

	public String getMessage(String messageKey){
		Locale locale = getLocale();
		try {
			return i18n.messageSource.getMessage(messageKey, null, locale);
		} catch (Exception e) {
			return i18n.messageSource.getMessage(DEFAULT_MESSAGEKEY, new Object[]{messageKey}, locale);
		}
	}
	
	public Locale getLocale(){
		String currentlanguage = language.getLocale();
		return new Locale(currentlanguage);
		//return LocaleContextHolder.getLocale();
	}
}
