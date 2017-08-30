package com.castle.quartz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.terracotta.quartz.wrappers.TriggerWrapper.TriggerState;

import com.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "V_QRTZ_TRIGGER")
public class TriggerEntity extends BaseEntity<String> {

	private static final long serialVersionUID = -6382495088261670747L;

	@Column(name = "sched_name")
	private String schedName;

	@Column(name = "trigger_name")
	private String triggerName;

	@Column(name = "trigger_group")
	private String triggerGroup;

	@Column(name = "job_name")
	private String jobName;

	@Column(name = "job_group")
	private String jobGroup;

	@Column(name = "description")
	private String description;

	@Column(name = "next_fire_time")
	private Date nextFireTime;

	@Column(name = "prev_fire_time")
	private Date prevFireTime;

	@Column(name = "start_time")
	private Date startTime;

	@Column(name = "end_time")
	private Date endTime;

	@Column(name = "priority")
	private Integer priority;

	@Enumerated(EnumType.STRING)
	@Column(name = "trigger_state")
	private TriggerState triggerState;

	@Column(name = "trigger_type")
	private String triggerType;

	@Column(name = "misfire_instr")
	private Short misfireInstr;

	public String getSchedName() {
		return schedName;
	}

	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public Date getPrevFireTime() {
		return prevFireTime;
	}

	public void setPrevFireTime(Date prevFireTime) {
		this.prevFireTime = prevFireTime;
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public TriggerState getTriggerState() {
		return triggerState;
	}

	public void setTriggerState(TriggerState triggerState) {
		this.triggerState = triggerState;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public Short getMisfireInstr() {
		return misfireInstr;
	}

	public void setMisfireInstr(Short misfireInstr) {
		this.misfireInstr = misfireInstr;
	}
}
