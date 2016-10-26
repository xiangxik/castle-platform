package com.whenling.castle.bbs.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "bbs_collect")
public class CollectEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 5787028630484058343L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private TopicEntity topic;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private UserEntity collector;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date collectDate;

	public TopicEntity getTopic() {
		return topic;
	}

	public void setTopic(TopicEntity topic) {
		this.topic = topic;
	}

	public UserEntity getCollector() {
		return collector;
	}

	public void setCollector(UserEntity collector) {
		this.collector = collector;
	}

	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

}
