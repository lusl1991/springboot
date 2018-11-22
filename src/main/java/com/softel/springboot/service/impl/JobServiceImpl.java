package com.softel.springboot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softel.springboot.domain.ScheduleJob;
import com.softel.springboot.domain.TaskDO;
import com.softel.springboot.quartz.utils.QuartzManager;
import com.softel.springboot.service.JobService;
import com.softel.springboot.util.ScheduleJobUtils;

@Service
public class JobServiceImpl implements JobService {
	
	@Autowired
	private QuartzManager quartzManager;

	@Override
	public void initSchedule() throws SchedulerException {
		// 这里获取任务信息数据
		List<TaskDO> jobList = mock();
		for (TaskDO scheduleJob : jobList) {
			if ("1".equals(scheduleJob.getJobStatus())) {
				ScheduleJob job = ScheduleJobUtils.entityToData(scheduleJob);
				quartzManager.addJob(job);
			}

		}
	}
	
	public List<TaskDO> mock() {
		List<TaskDO> list = new ArrayList<>();
		TaskDO taskDO = new TaskDO();
		
		taskDO.setId(2l);
		taskDO.setCronExpression("0/10 * * * * ?");
		taskDO.setMethodName("run1");
		taskDO.setIsConcurrent("1");
		taskDO.setDescription("");
		taskDO.setUpdateBy("4028ea815a3d2a8c015a3d2f8d2a0002");
		taskDO.setBeanClass("com.softel.springboot.task.WelcomeJob");
		taskDO.setCreateDate(new Date());
		taskDO.setJobStatus("1");
		taskDO.setJobGroup("group1");
		taskDO.setUpdateDate(new Date());
		taskDO.setCreateBy("lusl");
		taskDO.setSpringBean("");
		taskDO.setJobName("welcomJob");
		
		list.add(taskDO);
		return list;
	}

}