package com.softel.springboot.quartz.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.softel.springboot.domain.ScheduleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @title: QuartzManager.java
 * @description: 计划任务管理
 *
 */
@Service
@ComponentScan
@Slf4j
public class QuartzManager {
	
	@Autowired
	private Scheduler scheduler;
	
	/**
	 * 添加任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@SuppressWarnings("unchecked")
	public void addJob(ScheduleJob job) {
		System.out.println("hello");
		try {
			// 创建jobDetail实例，绑定Job实现类
			// 指明job的名称，所在组的名称，以及绑定job类

			Class<? extends Job> jobClass = (Class<? extends Job>) (Class.forName(job.getBeanClass()).newInstance().getClass());
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getJobName(), job.getJobGroup()).build();
			// 定义调度触发规则
			// 使用cornTrigger规则
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(job.getJobName(), job.getJobGroup())// 触发器key
					.startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
					.withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression())).startNow().build();
			// 把作业和触发器注册到任务调度中
			scheduler.scheduleJob(jobDetail, trigger);
			// 启动
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改任务
	 * @param job
	 * @return
	 */
    public Boolean updateTask(ScheduleJob job) {
        String jobName = job.getJobName(),
                jobGroup = job.getJobGroup(),
                cronExpression = job.getCronExpression(),
                jobDescription = job.getDescription();
        try {
            if (!checkExists(jobName, jobGroup)) {
                log.error(String.format("Job不存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
                return false;
            }
            JobKey jobKey = new JobKey(jobName, jobGroup);
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(cronScheduleBuilder).build();
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            jobDetail.getJobBuilder().withDescription(jobDescription);
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            log.error("类名不存在或执行表达式错误");
            return false;
        }
        return true;
    }

	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}
	
	/**
     * 验证是否存在
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }

	/**
	 * 更新job时间表达式
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
	}
	
}