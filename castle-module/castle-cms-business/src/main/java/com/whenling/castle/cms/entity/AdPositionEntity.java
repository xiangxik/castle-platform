package com.whenling.castle.cms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "cms_ad_position")
public class AdPositionEntity extends DataEntity<UserEntity, Long> {

	private static final long serialVersionUID = -3174643793695808162L;

	/** 名称 */
	@NotNull
	@Size(min = 1, max = 200)
	@Column(nullable = false, length = 200)
	private String name;

	/** 代号 */
	@NotNull
	@Size(min = 1, max = 200)
	@Column(nullable = false, length = 200)
	private String code;

	/** 宽度 */
	@NotNull
	@Min(1)
	@Column(nullable = false)
	private Integer width;

	/** 高度 */
	@NotNull
	@Min(1)
	@Column(nullable = false)
	private Integer height;

	/** 描述 */
	@Size(min = 1, max = 200)
	@Column(nullable = false, length = 200)
	private String description;

	/** 模板 */
	@NotNull
	@Lob
	private String template;

	@OneToMany(mappedBy = "adPosition", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@OrderBy("sortNo asc")
	private Set<AdEntity> ads = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Set<AdEntity> getAds() {
		return ads;
	}

	public void setAds(Set<AdEntity> ads) {
		this.ads = ads;
	}
}
