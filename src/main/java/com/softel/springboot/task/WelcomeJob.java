package com.softel.springboot.task;

import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import com.softel.springboot.websocket.WiselyResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WelcomeJob implements Job{
	
	@Autowired
	SimpMessagingTemplate template;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    	log.info("定时任务:" + new Date());
    	template.convertAndSend("/topic/getResponse", new WiselyResponse("欢迎体验bootdo,这是一个任务计划，使用了websocket和quzrtz技术，可以在计划列表中取消，欢迎您加入qq群交流学习!" ));
    }

}