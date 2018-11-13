package com.softel.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.softel.springboot.model.Version;
import com.softel.springboot.websocket.WiselyMessage;
import com.softel.springboot.websocket.WiselyResponse;

@Controller
public class WebsocketController {
	
	@Autowired
	private Version version;
	
	@MessageMapping("/welcome")
	@SendTo("/topic/getResponse")
	public WiselyResponse say(WiselyMessage wiselyMessage) throws Exception{
		Thread.sleep(3000);
		return new WiselyResponse("欢迎：" + wiselyMessage.getName());
	}
	
	@RequestMapping("/index")
    public String index(Model model) {
		model.addAttribute("version", version);
        return "index";
    }
	
}
