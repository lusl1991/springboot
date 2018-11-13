package com.softel.springboot.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import com.softel.springboot.model.Email;
import com.softel.springboot.queue.MailQueue;
import com.softel.springboot.service.EmailService;
import com.softel.springboot.util.EmailUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
    private JavaMailSender javaMailSender;//执行者
	
    @Autowired
    public Configuration configuration;//freemarker
    
    @Autowired
    private SpringTemplateEngine templateEngine;//thymeleaf
    
    @Value("${spring.mail.username}")
    public String USER_NAME;//发送者

    @Override
    public void send(Email email) throws Exception {
        EmailUtil emailUtil = new EmailUtil();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USER_NAME);
        message.setTo(email.getEmail());
        message.setSubject(email.getSubject());
        message.setText(email.getContent());
        emailUtil.start(javaMailSender, message);
    }

    @Override
    public void sendHtml(Email email) throws Exception {
    	EmailUtil emailUtil = new EmailUtil();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(USER_NAME);
        helper.setTo(email.getEmail());
        helper.setSubject(email.getSubject());
        helper.setText("<html><body><img src=\"cid:springcloud\" ></body></html>",true);
        // 发送图片
        File file = ResourceUtils.getFile("C://Users//Administrator//Desktop//girl.jpg");
        helper.addInline("jpg", file);
        
        // 发送excel
        file = ResourceUtils.getFile("D://success9.xls");
        helper.addAttachment("xls", file);
        
        // 发送附件
        file = ResourceUtils.getFile("D://VC_RED.cab");
        helper.addAttachment("Redis", file);
        emailUtil.startHtml(javaMailSender, message);
    }

    @Override
    public void sendFreemarker(Email email) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(USER_NAME);
        helper.setTo(email.getEmail());
        helper.setSubject(email.getSubject());
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", email.getContent());
        Template template = configuration.getTemplate(email.getTemplate()+".flt");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        helper.setText(text, true);
        javaMailSender.send(message);
    }

    @Override
    public void sendThymeleaf(Email email) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(USER_NAME);
        helper.setTo(email.getEmail());
        helper.setSubject(email.getSubject());
        Context context = new Context();
        context.setVariable("email", email);
        String text = templateEngine.process(email.getTemplate(), context);
        helper.setText(text, true);
        javaMailSender.send(message);
    }
    
    @Override
	public void sendQueue(Email mail) throws Exception {
		MailQueue.getMailQueue().produce(mail);
	}
	
}