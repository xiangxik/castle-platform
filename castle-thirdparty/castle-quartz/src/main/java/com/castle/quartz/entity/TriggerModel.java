package com.castle.quartz.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.TimeOfDay;

public class TriggerModel {

	public static enum Type {
		expression, daily, simple, calendar
	}

	@NotNull
	private String triggerName;

	private String description;
	private String cronExpression;
	private Date startTime;
	private Date endTime;
	private IntervalUnit intervalUnit;
	private Long repeatInterval;
	private Integer repeatCount;
	private TimeOfDay startTimeOfDay;
	private TimeOfDay endTimeOfDay;

	@NotNull
	private Type type = Type.simple;

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public IntervalUnit getIntervalUnit() {
		return intervalUnit;
	}

	public void setIntervalUnit(IntervalUnit intervalUnit) {
		this.intervalUnit = intervalUnit;
	}

	public Long getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(Long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public TimeOfDay getStartTimeOfDay() {
		return startTimeOfDay;
	}

	public void setStartTimeOfDay(TimeOfDay startTimeOfDay) {
		this.startTimeOfDay = startTimeOfDay;
	}

	public TimeOfDay getEndTimeOfDay() {
		return endTimeOfDay;
	}

	public void setEndTimeOfDay(TimeOfDay endTimeOfDay) {
		this.endTimeOfDay = endTimeOfDay;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
