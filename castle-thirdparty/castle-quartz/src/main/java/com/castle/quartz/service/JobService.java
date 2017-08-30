package com.castle.quartz.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castle.quartz.entity.JobEntity;
import com.castle.quartz.entity.JobModel;
import com.castle.quartz.entity.TriggerModel;
import com.castle.repo.jpa.EntityService;
import com.google.common.base.Objects;

@Service
public class JobService extends EntityService<JobEntity, String> {

	@Autowired
	private Scheduler scheduler;

	public void submit(JobModel jobModel) throws Exception {
		@SuppressWarnings("unchecked")
		Class<? extends Job> jobClass = (Class<? extends Job>) ClassUtils.getClass(jobModel.getJobClassName());
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobModel.getJobName(), jobModel.getJobGroup())
				.requestRecovery(jobModel.isRequestsRecovery()).storeDurably(true).withDescription(jobModel.getDescription())
				.setJobData(new JobDataMap(jobModel.getData())).build();
		scheduler.addJob(jobDetail, true);

		List<TriggerModel> triggerModels = jobModel.getTriggerModels();
		if (triggerModels != null) {
			for (TriggerModel triggerModel : triggerModels) {
				Date startTime = triggerModel.getStartTime();
				Date endTime = triggerModel.getEndTime();
				TriggerModel.Type type = triggerModel.getType();

				TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
						.withIdentity(triggerModel.getTriggerName(), jobModel.getJobGroup()).forJob(jobDetail);
				if (startTime != null) {
					triggerBuilder.startAt(startTime);
				}

				if (endTime != null) {
					triggerBuilder.endAt(endTime);
				}

				if (Objects.equal(type, TriggerModel.Type.expression)) {
					triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(triggerModel.getCronExpression()));
				} else if (Objects.equal(type, TriggerModel.Type.daily)) {
					DailyTimeIntervalScheduleBuilder dailyTimeIntervalScheduleBuilder = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule();
					if (triggerModel.getStartTimeOfDay() != null) {
						dailyTimeIntervalScheduleBuilder.startingDailyAt(triggerModel.getStartTimeOfDay());
					}
					if (triggerModel.getEndTimeOfDay() != null) {
						dailyTimeIntervalScheduleBuilder.endingDailyAt(triggerModel.getEndTimeOfDay());
					}
					dailyTimeIntervalScheduleBuilder.withInterval(triggerModel.getRepeatInterval().intValue(), triggerModel.getIntervalUnit());
					if (triggerModel.getRepeatCount() != null) {
						dailyTimeIntervalScheduleBuilder.withRepeatCount(triggerModel.getRepeatCount());
					}
					triggerBuilder.withSchedule(dailyTimeIntervalScheduleBuilder);
				} else if (Objects.equal(type, TriggerModel.Type.simple)) {
					SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
					if (triggerModel.getRepeatCount() != null) {
						simpleScheduleBuilder.withRepeatCount(triggerModel.getRepeatCount());
					}
					if (Objects.equal(triggerModel.getIntervalUnit(), IntervalUnit.HOUR)) {
						simpleScheduleBuilder.withIntervalInHours(triggerModel.getRepeatInterval().intValue());
					} else if (Objects.equal(triggerModel.getIntervalUnit(), IntervalUnit.MINUTE)) {
						simpleScheduleBuilder.withIntervalInMinutes(triggerModel.getRepeatInterval().intValue());
					} else if (Objects.equal(triggerModel.getIntervalUnit(), IntervalUnit.SECOND)) {
						simpleScheduleBuilder.withIntervalInSeconds(triggerModel.getRepeatInterval().intValue());
					}
					triggerBuilder.withSchedule(simpleScheduleBuilder);
				} else if (Objects.equal(type, TriggerModel.Type.calendar)) {
					triggerBuilder.withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
							.withInterval(triggerModel.getRepeatInterval().intValue(), triggerModel.getIntervalUnit()));
				}

				scheduler.scheduleJob(triggerBuilder.build());
			}
		}
	}

	@Override
	public <S extends JobEntity> S save(S entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <S extends JobEntity> S saveAndFlush(S entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <S extends JobEntity> List<S> save(Iterable<S> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Iterable<? extends JobEntity> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(JobEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAllInBatch() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteInBatch(Iterable<JobEntity> entities) {
		throw new UnsupportedOperationException();
	}
}
