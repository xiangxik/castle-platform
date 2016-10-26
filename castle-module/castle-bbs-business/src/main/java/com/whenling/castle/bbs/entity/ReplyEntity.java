package com.whenling.castle.bbs.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "bbs_reply")
public class ReplyEntity extends DataEntity<UserEntity, Long> {

	private static final long serialVersionUID = -4477556038002519944L;

	@Lob
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private TopicEntity topic;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private UserEntity replyer;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date replyDate;

	public TopicEntity getTopic() {
		return topic;
	}

	public void setTopic(TopicEntity topic) {
		this.topic = topic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserEntity getReplyer() {
		return replyer;
	}

	public void setReplyer(UserEntity replyer) {
		this.replyer = replyer;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

}
