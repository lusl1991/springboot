package com.softel.springboot.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserEntityVo {
	
	private Integer id;
	
	private Integer sex;
	
	private String username;
	
}