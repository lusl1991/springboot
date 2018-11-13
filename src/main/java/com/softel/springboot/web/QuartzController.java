package com.softel.springboot.web;

import java.util.UUID;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.softel.springboot.quartz.timer.UserTimer;

@RestController
public class QuartzController {
	
	private static JobKey jobKey = null;
	
	@Autowired
    private Scheduler scheduler;

	@RequestMapping("/start")
	public JobKey start() throws SchedulerException{
		//任务名
		String name = UUID.randomUUID().toString();
		//任务所属分组 
		String group = UserTimer.class.getName();
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
		jobKey = new JobKey(name, group);
		//创建任务 
		JobDetail jobDetail = JobBuilder.newJob(UserTimer.class).withIdentity(jobKey).build();
		//传递参数
		jobDetail.getJobDataMap().put("jobKey", jobKey);
		//创建任务触发器 
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name,group).withSchedule(scheduleBuilder).build();
		//将触发器与任务绑定到调度器内 
		scheduler.scheduleJob(jobDetail, trigger);
		return jobKey;
	}
	
	@RequestMapping("/stop")
	public JobKey stop() throws SchedulerException{
		scheduler.deleteJob(jobKey);
		return jobKey;
	}
}
