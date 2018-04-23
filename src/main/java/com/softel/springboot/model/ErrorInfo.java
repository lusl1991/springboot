package com.softel.springboot.model;

import lombok.Data;

@Data
public class ErrorInfo {
	
	private String objectname;

	private String fieldname;
	
	private String errormsg;
	
}
