package com.whenling.castle.console.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "tbl_contact_us")
@EntityListeners(value = { AuditingEntityListener.class })
public class ContactUs extends BaseEntity<Long> {

	private static final long serialVersionUID = -5588671575578891563L;

	@NotNull
	@Size(max = 200)
	private String name;

	@NotNull
	@Size(max = 200)
	private String phone;

	@NotNull
	@Size(max = 500)
	@Column(length = 1000)
	private String message;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
