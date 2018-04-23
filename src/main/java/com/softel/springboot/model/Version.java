package com.softel.springboot.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
public class Version {

	@Value("${project.date}")
	private String date;
	
	@Value("${project.version}")
	private String ver;

}
