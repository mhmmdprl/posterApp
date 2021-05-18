package com.esandmongodb.posterapp.service.Impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.esandmongodb.posterapp.entity.TimerInfo;
import com.esandmongodb.posterapp.repository.SchedulerRepository;
import com.esandmongodb.posterapp.service.SchedulerService;
import com.esandmongodb.posterapp.util.TimerUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchedulerServiceImpl implements SchedulerService {
	@Autowired
	private Scheduler scheduler;

	@Autowired
	private SchedulerRepository schedulerRepository;

	@Override
	@Async
	public void startAllSchedules() {
		List<TimerInfo> timerInfos = this.schedulerRepository.findAll();
		if (timerInfos != null) {
			for (TimerInfo timerInfo : timerInfos) {
				try {
					JobDetail jobDetail = TimerUtil.buildJobDetail(timerInfo);
					if (!scheduler.checkExists(jobDetail.getKey())) {
						Trigger trigger;
						if (timerInfo.isCronJob()) {
							trigger = TimerUtil.buildCronTrigger(timerInfo);
						} else {
							trigger = TimerUtil.buildTrigger(timerInfo);
						}

						scheduler.scheduleJob(jobDetail, trigger);
					}

				} catch (Exception e) {
					log.info("Hata : {}", e);
				}
			}
		}

	}

	@Override
	public void scheduleNewJob(TimerInfo timerInfo) {

		try {
			JobDetail jobDetail = TimerUtil.buildJobDetail(timerInfo);
			if (!scheduler.checkExists(jobDetail.getKey())) {
				Trigger trigger;
				if (timerInfo.isCronJob()) {
					trigger = TimerUtil.buildCronTrigger(timerInfo);

				} else {
					trigger = TimerUtil.buildTrigger(timerInfo);
				}
				scheduler.scheduleJob(jobDetail, trigger);
				this.schedulerRepository.save(timerInfo);
			} else {
				log.error("scheduleNewJobRequest.jobAlreadyExist");
			}
		} catch (Exception e) {
			log.error("Yeni görev olşturulamadı : {}", e);
		}
	}

	@Override
	public List<TimerInfo> getAllRunningTimer() {
		try {
			return scheduler.getJobKeys(GroupMatcher.anyGroup()).stream().map(jobKey -> {

				try {
					final JobDetail jobDetail = scheduler.getJobDetail(jobKey);
					return (TimerInfo) jobDetail.getJobDataMap().get(jobKey.getName());
				} catch (Exception e) {
					log.error("Hata : " + e);
					return null;
				}
			}).filter(Objects::nonNull).collect(Collectors.toList());
		} catch (SchedulerException e) {
			log.error("Liste boş : " + e);
			return Collections.emptyList();
		}
	}

	@Override
	public TimerInfo getRunningTimer(String timerId) {
		try {
			JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));

			if (jobDetail == null) {

				return null;
			}
			return (TimerInfo) jobDetail.getJobDataMap().get(timerId);
		} catch (Exception e) {
			log.error("Hata : " + e);
			return null;
		}
	}

	@Override
	public void updateRunningTimer(TimerInfo timerInfo) {

		Trigger trigger;
		if (timerInfo.isCronJob()) {

			trigger = TimerUtil.buildCronTrigger(timerInfo);
		} else {
			trigger = TimerUtil.buildTrigger(timerInfo);
		}
		try {
			this.scheduler.rescheduleJob(TriggerKey.triggerKey(timerInfo.getClassName()), trigger);
			this.schedulerRepository.save(timerInfo);
		} catch (Exception e) {
			log.error("Failed to update job - {}", timerInfo.getClassName());
		}
	}

	@Override
	public boolean pauseRunningTimer(TimerInfo timerInfo) {
		try {
			this.scheduler.pauseJob(new JobKey(timerInfo.getClassName(), timerInfo.getGroupName()));
			return true;
		} catch (Exception e) {
			log.error("Failed to pause job - {}", timerInfo.getClassName());
			return false;
		}
	}

	@Override
	public boolean resumeRunningTimer(TimerInfo timerInfo) {
		try {
			this.scheduler.resumeJob(new JobKey(timerInfo.getClassName(), timerInfo.getGroupName()));
			return true;
		} catch (Exception e) {
			log.error("Failed to resume job - {}", timerInfo.getClassName());
			return false;
		}
	}

	@Override
	public boolean deleteRunningTimer(TimerInfo timerInfo) {
		try {
			this.scheduler.deleteJob(new JobKey(timerInfo.getClassName(), timerInfo.getGroupName()));
			return true;
		} catch (Exception e) {
			log.error("Failed to delete job - {}", timerInfo.getClassName());
			return false;
		}
	}

}
