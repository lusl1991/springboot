package com.softel.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.softel.springboot.model.Email;
import com.softel.springboot.service.EmailService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@ActiveProfiles("163")
public class EmailTest {
	
	@Autowired
	private EmailService emailService;
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Test
	public void testSendSimple() throws Exception {
		Email email = new Email();
		email.setEmail(new String[]{"825729444@qq.com"});
		email.setSubject("你个小逗比");
		email.setContent("科帮网欢迎您");
        emailService.send(email);
	}

}
