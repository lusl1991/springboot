package com.softel.springboot.websocket;

import lombok.Data;

@Data
public class WiselyResponse {

	private String responseMessage;

	public WiselyResponse(String responseMessage) {
		super();
		this.responseMessage = responseMessage;
	}
	
}
