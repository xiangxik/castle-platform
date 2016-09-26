package com.whenling.castle.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "cms_job")
public class JobEntity extends DataEntity<UserEntity, Long> {

	private static final long serialVersionUID = 6047805369721349513L;

	/** 标题 */
	@Column(nullable = false, length = 200)
	private String title;
	
	@Lob
	private String content;
	
	/** 是否列出 */
	private Boolean list;
	
	/** 发送简历到此邮箱 */
	@Column(length = 200)
	private String resumeToMail;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getList() {
		return list;
	}

	public void setList(Boolean list) {
		this.list = list;
	}

	public String getResumeToMail() {
		return resumeToMail;
	}

	public void setResumeToMail(String resumeToMail) {
		this.resumeToMail = resumeToMail;
	}
	
}
