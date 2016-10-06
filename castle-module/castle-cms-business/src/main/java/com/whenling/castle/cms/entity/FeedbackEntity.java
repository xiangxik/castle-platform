package com.whenling.castle.cms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "cms_contact")
public class FeedbackEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 1006783662830110403L;

	@NotNull
	@Size(max = 100)
	@Column(length = 100)
	private String name;

	@NotNull
	@Size(max = 100)
	@Column(length = 100)
	private String email;

	@NotNull
	@Size(max = 200)
	@Column(length = 200)
	private String subject;

	@Lob
	private String message;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
