package com.whenling.castle.plugin.model;

public class PluginModel {

	public static PluginModel convert(Plugin plugin) {
		PluginModel model = new PluginModel();
		model.setId(plugin.getId());
		model.setName(plugin.getName());
		model.setVersion(plugin.getVersion());
		model.setAuthor(plugin.getAuthor());
		model.setSettingView(plugin.getSettingView());
		model.setInstallUrl(plugin.getInstallUrl());
		model.setUninstallUrl(plugin.getUninstallUrl());
		model.setSettingUrl(plugin.getSettingUrl());
		model.setIsEnabled(plugin.getIsEnabled());
		model.setIsInstalled(plugin.getIsInstalled());
		model.setSortNo(plugin.getSortNo());
		return model;
	}

	private String id;
	private String name;
	private String version;
	private String author;
	private String settingView;
	private String installUrl;
	private String uninstallUrl;
	private String settingUrl;
	private Boolean isInstalled;
	private Boolean isEnabled;
	private Integer sortNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSettingView() {
		return settingView;
	}

	public void setSettingView(String settingView) {
		this.settingView = settingView;
	}

	public String getInstallUrl() {
		return installUrl;
	}

	public void setInstallUrl(String installUrl) {
		this.installUrl = installUrl;
	}

	public String getUninstallUrl() {
		return uninstallUrl;
	}

	public void setUninstallUrl(String uninstallUrl) {
		this.uninstallUrl = uninstallUrl;
	}

	public String getSettingUrl() {
		return settingUrl;
	}

	public void setSettingUrl(String settingUrl) {
		this.settingUrl = settingUrl;
	}

	public Boolean getIsInstalled() {
		return isInstalled;
	}

	public void setIsInstalled(Boolean isInstalled) {
		this.isInstalled = isInstalled;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
}
