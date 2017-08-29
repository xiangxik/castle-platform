package com.castle.quartz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "V_QRTZ_JOB")
public class JobEntity extends BaseEntity<String> {

	private static final long serialVersionUID = -1361976336063308562L;

	@Column(name = "sched_name")
	private String schedName;

	@Column(name = "job_name")
	private String jobName;

	@Column(name = "job_group")
	private String jobGroup;

	@Column(name = "description")
	private String description;

	@Column(name = "job_class_name")
	private String jobClassName;

	@Column(name = "is_durable")
	private boolean durable;

	@Column(name = "is_update_data")
	private boolean updateData;

	@Column(name = "requests_recovery")
	private boolean requestsRecovery;

	@Transient
	private boolean executing = false;

	@Transient
	private boolean paused = false;

	@Transient
	private boolean hasTriggers = false;

	public String getSchedName() {
		return schedName;
	}

	public void setSchedName(String schedName) {
		this.schedName = schedName;
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

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	public boolean isDurable() {
		return durable;
	}

	public void setDurable(boolean durable) {
		this.durable = durable;
	}

	public boolean isUpdateData() {
		return updateData;
	}

	public void setUpdateData(boolean updateData) {
		this.updateData = updateData;
	}

	public boolean isRequestsRecovery() {
		return requestsRecovery;
	}

	public void setRequestsRecovery(boolean requestsRecovery) {
		this.requestsRecovery = requestsRecovery;
	}

	@Transient
	public boolean isExecuting() {
		return executing;
	}

	public void setExecuting(boolean executing) {
		this.executing = executing;
	}

	@Transient
	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	@Transient
	public boolean isHasTriggers() {
		return hasTriggers;
	}

	public void setHasTriggers(boolean hasTriggers) {
		this.hasTriggers = hasTriggers;
	}

}
