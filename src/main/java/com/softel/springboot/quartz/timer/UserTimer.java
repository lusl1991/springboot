package com.softel.springboot.quartz.timer;

import java.util.Date;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.scheduling.quartz.QuartzJobBean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserTimer extends QuartzJobBean {
	
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        JobKey jobKey = (JobKey)dataMap.get("jobKey");
		log.info("执行定时任务，执行时间：{}，执行参数：jobKey = {}", new Date(), jobKey);
	}

}
