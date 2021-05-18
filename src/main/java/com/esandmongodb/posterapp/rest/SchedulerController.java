package com.esandmongodb.posterapp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esandmongodb.posterapp.entity.TimerInfo;
import com.esandmongodb.posterapp.service.DbSequenceService;
import com.esandmongodb.posterapp.service.SchedulerService;

@RestController
@RequestMapping("/jobs")
public class SchedulerController extends BaseController {

	@Autowired
	private DbSequenceService dqSequenceService;
	@Autowired
	private SchedulerService scheduleService;

	@PostMapping("/start")
	public void startAllJob() {

		this.scheduleService.startAllSchedules();
	}

	@PreAuthorize("hasAuthority('/jobs/new_POST')")
	@PostMapping("/new")
	public void newJob(@RequestBody TimerInfo timerInfo) {

		timerInfo.setId(this.dqSequenceService.getSeq(TimerInfo.seqName));

		this.scheduleService.scheduleNewJob(timerInfo);

	}

	@PreAuthorize("hasAuthority('/jobs_GET')")
	@GetMapping
	public List<TimerInfo> getAllJobs() {

		return this.scheduleService.getAllRunningTimer();
	}

	@PreAuthorize("hasAuthority('/jobs/update_PUT')")
	@PutMapping("/update")
	public void updateJob(@RequestBody TimerInfo timerInfo) {

		this.scheduleService.updateRunningTimer(timerInfo);
	}

	@PreAuthorize("hasAuthority('/jobs/pause_POST')")
	@PostMapping("/pause")
	public void pauseJob(@RequestBody TimerInfo timerInfo) {

		this.scheduleService.pauseRunningTimer(timerInfo);
	}

	@PreAuthorize("hasAuthority('/jobs/resume_POST')")
	@PostMapping("/resume")
	public void resumeJob(@RequestBody TimerInfo timerInfo) {

		this.scheduleService.resumeRunningTimer(timerInfo);
	}

	@PreAuthorize("hasAuthority('/jobs/delete_DELETE')")
	@DeleteMapping("/delete")
	public void deleteJob(@RequestBody TimerInfo timerInfo) {

		this.scheduleService.deleteRunningTimer(timerInfo);
	}
}
