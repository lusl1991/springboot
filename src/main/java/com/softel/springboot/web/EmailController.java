package com.softel.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.softel.springboot.model.Email;
import com.softel.springboot.queue.MailQueue;
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
	
	/**
	 * quene
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/quene")
    public void quene() throws Exception {
		Email email = new Email();
		email.setSubject("64656ytr4644242424");
		email.setContent("64656ytr4644242424");
		email.setTemplate("welcome");
		email.setEmail(new String[]{"lusl1991@163.com","1803145077@qq.com"});
		for(int i=0; i<10000; i++) {
			MailQueue.getMailQueue().produce(email);
		}
    }
	
}