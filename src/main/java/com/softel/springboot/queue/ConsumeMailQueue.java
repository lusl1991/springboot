package com.softel.springboot.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.softel.springboot.model.Email;
import com.softel.springboot.service.EmailService;
import lombok.extern.slf4j.Slf4j;

/**
 * 发送邮件线程
 * @author Administrator
 *
 */
@Component
@Slf4j
public class ConsumeMailQueue {
	
	@Autowired
	EmailService mailService;
	
	@PostConstruct
	public void startThread() {
		log.info("启动发送邮件线程任务");
		ExecutorService e = Executors.newFixedThreadPool(2);// 两个大小的固定线程池
		e.submit(new PollMail(mailService));
		e.submit(new PollMail(mailService));
	}

	class PollMail implements Runnable {
		
		EmailService mailService;

		public PollMail(EmailService mailService) {
			this.mailService = mailService;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Email mail = MailQueue.getMailQueue().consume();
					if (mail != null) {
						log.info("剩余邮件总数:{}",MailQueue.getMailQueue().size());
						mailService.sendFreemarker(mail);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@PreDestroy
	public void stopThread() {
		log.info("destroy");
	}
	
}