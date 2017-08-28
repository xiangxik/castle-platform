package com.castle.quartz;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class SpringJobFactory extends SimpleJobFactory {

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler) throws SchedulerException {
		Job job = super.newJob(bundle, Scheduler);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(job);
		return job;
	}
}
