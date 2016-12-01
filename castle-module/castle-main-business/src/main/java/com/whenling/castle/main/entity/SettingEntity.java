package com.whenling.castle.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "sys_setting")
public class SettingEntity extends DataEntity<AdminEntity, Long> {

	private static final long serialVersionUID = -7311556460000491508L;

	/** 网站名称 */
	@NotNull
	@Size(min = 2, max = 50)
	@Column(length = 200)
	private String siteName;

	/** logo */
	@Column(length = 200)
	private String logo;

	/** adminLoginBanner */
	@Column(length = 200)
	private String adminLoginBanner;

	/** 介绍 */
	@Lob
	private String introduction;

	/** 热门搜索 */
	@Column(length = 200)
	private String hotSearch;

	/** 联系地址 */
	@Column(length = 500)
	private String address;

	/** 联系人 */
	@Column(length = 100)
	private String contact;

	/** 联系电话1 */
	@Column(length = 50)
	private String phone1;

	/** 联系电话2 */
	@Column(length = 50)
	private String phone2;

	/** 邮政编码 */
	@Column(length = 50)
	private String zipCode;

	/** E-mail */
	@Column(length = 50)
	private String email;

	/** 备案编号 */
	@Column(length = 100)
	private String certtext;

	/** 是否网站开启 */
	private Boolean isSiteEnabled;

	/** 网站关闭消息 */
	@Column(length = 500)
	private String siteCloseMessage;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAdminLoginBanner() {
		return adminLoginBanner;
	}

	public void setAdminLoginBanner(String adminLoginBanner) {
		this.adminLoginBanner = adminLoginBanner;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getHotSearch() {
		return hotSearch;
	}

	public void setHotSearch(String hotSearch) {
		this.hotSearch = hotSearch;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCerttext() {
		return certtext;
	}

	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}

	public Boolean getIsSiteEnabled() {
		return isSiteEnabled;
	}

	public void setIsSiteEnabled(Boolean isSiteEnabled) {
		this.isSiteEnabled = isSiteEnabled;
	}

	public String getSiteCloseMessage() {
		return siteCloseMessage;
	}

	public void setSiteCloseMessage(String siteCloseMessage) {
		this.siteCloseMessage = siteCloseMessage;
	}
}
