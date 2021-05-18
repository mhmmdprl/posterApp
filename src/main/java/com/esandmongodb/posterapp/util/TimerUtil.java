package com.esandmongodb.posterapp.util;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.esandmongodb.posterapp.entity.TimerInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimerUtil {
	public static JobDetail buildJobDetail(final TimerInfo timerInfo) {

		final JobDataMap dataMap = new JobDataMap();

		dataMap.put(timerInfo.getClassName(), timerInfo);

		try {
			return JobBuilder.newJob(Class.forName(timerInfo.getClassName()).asSubclass(Job.class))
					.withIdentity(timerInfo.getClassName(), timerInfo.getGroupName()).setJobData(dataMap).build();
		} catch (ClassNotFoundException e) {
			log.info("Hata : {}", e);
		}
		return null;

	}

	public static Trigger buildTrigger(final TimerInfo timerInfo) {

		SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInMilliseconds(timerInfo.getRepeatIntervalMs());

		if (timerInfo.isRunForever()) {

			builder = builder.repeatForever();
		} else {

			builder = builder.withRepeatCount(timerInfo.getTotalFireCount() - 1);

		}

		return TriggerBuilder.newTrigger().withIdentity(timerInfo.getClassName(), timerInfo.getGroupName())
				.withSchedule(builder)

				.startAt(new Date(System.currentTimeMillis() + timerInfo.getIntialOffSetMs())).build();
	}

	public static Trigger buildCronTrigger(final TimerInfo timerInfo) {
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(timerInfo.getCronExpression());

		return TriggerBuilder.newTrigger().withIdentity(timerInfo.getClassName(), timerInfo.getGroupName())
				.withSchedule(cronScheduleBuilder)

				.startAt(new Date(System.currentTimeMillis() + timerInfo.getIntialOffSetMs())).build();
	}
}
