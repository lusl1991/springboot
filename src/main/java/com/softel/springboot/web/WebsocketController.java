package com.softel.springboot.web;

import java.util.List;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.softel.springboot.domain.ScheduleJob;
import com.softel.springboot.enums.ResultCode;
import com.softel.springboot.model.Version;
import com.softel.springboot.quartz.utils.QuartzManager;
import com.softel.springboot.util.Result;
import com.softel.springboot.util.ResultUtils;
import com.softel.springboot.websocket.WiselyMessage;
import com.softel.springboot.websocket.WiselyResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WebsocketController {
	
	@Autowired
	private Version version;
	
	@Autowired
	private QuartzManager quartzManager;
	
	@MessageMapping("/welcome")
	@SendTo("/topic/getResponse")
	public WiselyResponse say(WiselyMessage wiselyMessage) throws Exception{
		Thread.sleep(3000);
		return new WiselyResponse("欢迎：" + wiselyMessage.getName());
	}
	
	@RequestMapping("/index")
    public String index(Model model) {
		model.addAttribute("version", version);
        return "index";
    }
	
	@RequestMapping("/getAllJob")
	@ResponseBody
    public Result getAllJob() {
		log.info("获取所有定时任务");
		List<ScheduleJob> list = null;
		try {
			list = quartzManager.getAllJob();
		} catch (SchedulerException e) {
			e.printStackTrace();
			return ResultUtils.warn(ResultCode.WEAK_NET_WORK);
		}
        return ResultUtils.success(list);
    }
	
	@RequestMapping("/getRunningJob")
	@ResponseBody
    public Result getRunningJob() {
		log.info("获取正在运行的定时任务");
		List<ScheduleJob> list = null;
		try {
			list = quartzManager.getRunningJob();
		} catch (SchedulerException e) {
			e.printStackTrace();
			return ResultUtils.warn(ResultCode.WEAK_NET_WORK);
		}
        return ResultUtils.success(list);
    }
	
	@RequestMapping("/pauseJob")
	@ResponseBody
    public Result pauseJob(ScheduleJob scheduleJob) {
		log.info("暂停定时任务:[jobName:{},jobGroup:{}]", scheduleJob.getJobName(), scheduleJob.getJobGroup());
		try {
			quartzManager.pauseJob(scheduleJob);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return ResultUtils.warn(ResultCode.WEAK_NET_WORK);
		}
        return ResultUtils.success();
    }
	
	@RequestMapping("/runAJobNow")
	@ResponseBody
    public Result runAJobNow(ScheduleJob scheduleJob) {
		log.info("立即运行一次定时任务:[jobName:{},jobGroup:{}]", scheduleJob.getJobName(), scheduleJob.getJobGroup());
		try {
			quartzManager.runAJobNow(scheduleJob);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return ResultUtils.warn(ResultCode.WEAK_NET_WORK);
		}
        return ResultUtils.success();
    }
	
	@RequestMapping("/resumeJob")
	@ResponseBody
    public Result resumeJob(ScheduleJob scheduleJob) {
		log.info("恢复定时任务:[jobName:{},jobGroup:{}]", scheduleJob.getJobName(), scheduleJob.getJobGroup());
		try {
			quartzManager.resumeJob(scheduleJob);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return ResultUtils.warn(ResultCode.WEAK_NET_WORK);
		}
        return ResultUtils.success();
    }
	
}
