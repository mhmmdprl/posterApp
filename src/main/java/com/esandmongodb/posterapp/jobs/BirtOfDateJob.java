package com.esandmongodb.posterapp.jobs;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esandmongodb.posterapp.entity.Author;
import com.esandmongodb.posterapp.entity.TimerInfo;
import com.esandmongodb.posterapp.repository.AuthorRepository;
import com.esandmongodb.posterapp.service.EmailService;
import com.esandmongodb.posterapp.util.BirtOfDateUtil;



@Component
public class BirtOfDateJob implements Job{

	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private EmailService emailService;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobDataMap jobDataMap=context.getJobDetail().getJobDataMap();
		TimerInfo timerInfo=(TimerInfo) jobDataMap.get(this.getClass().getName());
		List<Author>  authors=this.authorRepository.findAllActive();
		for (Author author : authors) {
			
			if (BirtOfDateUtil.checkBirtOfDate(author.getBirtOfDate())) {
				
				this.emailService.sendSimpleMessage(author.getEmail(), "Doğum Günü", author.getName()+timerInfo.getCallBackData());
			}
			
		}
	}

}
