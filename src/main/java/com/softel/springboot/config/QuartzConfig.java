package com.softel.springboot.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import com.softel.springboot.quartz.factory.JobFactory;

@Configuration
public class QuartzConfig {
	
	@Autowired
	private JobFactory jobFactory;
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setJobFactory(jobFactory);
		return factory;
	}
	
	@Bean(name = "scheduler")
	public Scheduler scheduler() {
		return schedulerFactoryBean().getScheduler();
	}
	
}