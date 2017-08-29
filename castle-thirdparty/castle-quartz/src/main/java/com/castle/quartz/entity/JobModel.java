package com.castle.quartz.entity;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class JobModel {
	private String schedName;

	@NotNull
	private String jobName;

	@NotNull
	private String jobGroup;

	private String description;
	@NotNull
	private String jobClassName;

	private boolean durable = true;
	private boolean updateData;
	private boolean requestsRecovery;
	private Map<String, Object> data;

	@Valid
	private List<TriggerModel> triggerModels;

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

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public List<TriggerModel> getTriggerModels() {
		return triggerModels;
	}

	public void setTriggerModels(List<TriggerModel> triggerModels) {
		this.triggerModels = triggerModels;
	}

}
