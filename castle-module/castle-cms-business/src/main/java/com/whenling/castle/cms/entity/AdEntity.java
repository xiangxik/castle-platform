package com.whenling.castle.cms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.SortEntity;

@Entity
@Table(name = "cms_ad")
public class AdEntity extends SortEntity<UserEntity, Long> {

	private static final long serialVersionUID = -7067117077980949498L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 文本 */
		text,
		
		/** 代码 */
		code,

		/** 图片 */
		image,

		/** flash */
		flash,
		
		/** 视频 */
		video
	}

	/** 标题 */
	@NotNull
	@Size(min = 1, max = 200)
	@Column(nullable = false, length = 200)
	private String title;

	/** 类型 */
	@NotNull
	@Column(nullable = false, length = 50)
	private Type type;

	/** 内容 */
	@Lob
	private String content;

	/** 路径 */
	@Size(max = 200)
	@Column(length = 200)
	private String path;

	/** 起始日期 */
	@DateTimeFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
	private Date beginDate;

	/** 结束日期 */
	@DateTimeFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
	private Date endDate;

	/** 链接地址 */
	@Size(max = 200)
	@Column(length = 200)
	private String url;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private AdPositionEntity adPosition;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AdPositionEntity getAdPosition() {
		return adPosition;
	}

	public void setAdPosition(AdPositionEntity adPosition) {
		this.adPosition = adPosition;
	}

}
