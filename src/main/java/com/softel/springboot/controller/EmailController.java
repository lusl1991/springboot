package com.softel.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softel.springboot.model.Email;
import com.softel.springboot.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	/**
	 * normal
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/normal")
    public void send() throws Exception {
		Email email = new Email();
		email.setEmail(new String[]{"825729444@qq.com"});
		email.setSubject("JavaMailSender邮件测试");
		email.setContent("love小仙女");
        emailService.send(email);
    }
	
	/**
	 * html
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/html")
    public void sendHtml() throws Exception {
		Email email = new Email();
		email.setEmail(new String[]{"lusl1991@163.com","825729444@qq.com"});
		email.setSubject("JavaMailSender邮件测试");
		email.setContent("love小仙女");
        emailService.sendHtml(email);;
    }
	
	/**
	 * freemarker
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/freemarker")
    public void freemarker() throws Exception {
		Email email = new Email();
		email.setEmail(new String[]{"lusl1991@163.com","825729444@qq.com"});
		email.setSubject("JavaMailSender邮件测试");
		email.setContent("love小仙女");
		email.setTemplate("welcome");
        emailService.sendFreemarker(email);
    }
	
	/**
	 * thymeleaf
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/thymeleaf")
    public void thymeleaf() throws Exception {
		Email email = new Email();
		email.setEmail(new String[]{"lusl1991@163.com","825729444@qq.com"});
		email.setSubject("JavaMailSender邮件测试");
		email.setContent("love小仙女");
		email.setTemplate("welcome");
        emailService.sendThymeleaf(email);
    }
	
}
