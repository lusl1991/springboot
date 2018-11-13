package com.softel.springboot.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	
    private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(6);

    private final AtomicInteger count = new AtomicInteger(1);

    public void start(final JavaMailSender javaMailSender,final SimpleMailMessage message) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (count.get() == 2) {
                        service.shutdown();
                        logger.error("the task is down");
                    }
                    logger.info("start send email and the index is " + count);
                    javaMailSender.send(message);
                    logger.info("send email success");
                }catch (Exception e){
                    logger.error("send email fail" , e);
                }
            }
        });
    }
    
    public void startHtml(final JavaMailSender javaMailSender,final MimeMessage message) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (count.get() == 2) {
                        service.shutdown();
                        logger.info("the task is down");
                    }
                    logger.info("start send email and the index is " + count);
                    javaMailSender.send(message);
                    logger.info("send email success");
                }catch (Exception e){
                    logger.error("send email fail" , e);
                }

            }
        });
    }
    
}