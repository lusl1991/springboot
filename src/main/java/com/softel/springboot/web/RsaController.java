package com.softel.springboot.web;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import com.softel.springboot.util.RSAUtils;

/**
 * 客户端和服务端的rsa加密、解密
 * @author Administrator
 *
 */
@Controller
public class RsaController {

	@RequestMapping(value = "rsa", method=RequestMethod.GET)
	public String doGet(HttpServletRequest req, Model model){
		try{
			//返回n和e到页面上，有这两个值就能得出公钥
			model.addAttribute("n", RSAUtils.getModulus());
			model.addAttribute("e", RSAUtils.getPublicExponent());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "rsa";
		
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "rsatest")
	@ResponseBody
	public Map test(WebRequest req) {
		try{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String str = req.getParameter("data");
			String decryptResult = RSAUtils.decryptPrivate(str);
			//以上是前端js加密，后端java解密
			
			//以下是后端java加密，前端js解密
			String encryptResult = RSAUtils.encryptPrivate(decryptResult);
			resultMap.put("encryptResult", encryptResult);
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
