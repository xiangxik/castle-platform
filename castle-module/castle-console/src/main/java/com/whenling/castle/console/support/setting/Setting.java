package com.whenling.castle.console.support.setting;

import java.io.Serializable;

public class Setting implements Serializable {

	private static final long serialVersionUID = -8589392256257858575L;

	private static Setting INSTANCE = new Setting();

	public static Setting getInstance() {
		return INSTANCE;
	}

	private Setting() {
	}

	/** 网站名称 */
	private String siteName;

	/** 联系地址 */
	private String address;

	/** 联系电话 */
	private String phone;

	/** 邮政编码 */
	private String zipCode;

	/** E-mail */
	private String email;

	/** 备案编号 */
	private String certtext;

	/** 是否网站开启 */
	private Boolean isSiteEnabled;

	/** 网站关闭消息 */
	private String siteCloseMessage;

	/** baidu统计ID */
	private String baiduSiteId;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getBaiduSiteId() {
		return baiduSiteId;
	}

	public void setBaiduSiteId(String baiduSiteId) {
		this.baiduSiteId = baiduSiteId;
	}

}
