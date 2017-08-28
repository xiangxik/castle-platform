package com.castle.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.castle.email.EmailService;

public class SendEmailJob implements Job {

	@Autowired
	private EmailService emailService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getMergedJobDataMap();

		emailService.send(dataMap.getString("toMail"), dataMap.getString("subject"), dataMap.getString("content"));
	}

}
