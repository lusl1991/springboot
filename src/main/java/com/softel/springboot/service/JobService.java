package com.softel.springboot.service;

import org.quartz.SchedulerException;

public interface JobService {

	void initSchedule() throws SchedulerException;
	
}
