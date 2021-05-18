package com.esandmongodb.posterapp.service;

import java.util.List;

import com.esandmongodb.posterapp.entity.TimerInfo;

public interface SchedulerService {

	
	public void startAllSchedules();
	
	public void scheduleNewJob(TimerInfo timerInfo);
	
	public List<TimerInfo> getAllRunningTimer();
	
	public TimerInfo getRunningTimer(String timerId);
	
	public void updateRunningTimer(TimerInfo timerInfo);
	
	public boolean pauseRunningTimer(TimerInfo timerInfo);
	
	public boolean resumeRunningTimer(TimerInfo timerInfo);
	
	public boolean deleteRunningTimer(TimerInfo timerInfo);
}
