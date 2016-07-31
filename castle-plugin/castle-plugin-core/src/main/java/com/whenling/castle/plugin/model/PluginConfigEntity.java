package com.whenling.castle.plugin.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.SortEntity;

@Entity
@Table(name = "plugin_config")
public class PluginConfigEntity extends SortEntity<UserEntity, Long> {

	private static final long serialVersionUID = 6374792677775419596L;

	/** 插件ID */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	private String pluginId;

	/** 是否启用 */
	@Column(nullable = false)
	private Boolean isEnabled;

	/** 属性 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "plugin_config_attribute")
	@MapKeyColumn(name = "name", length = 100)
	private Map<String, String> attributes = new HashMap<String, String>();

	public String getPluginId() {
		return pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	@Transient
	public String getAttribute(String name) {
		if (getAttributes() != null && name != null) {
			return getAttributes().get(name);
		} else {
			return null;
		}
	}

	@Transient
	public void setAttribute(String name, String value) {
		if (getAttributes() != null && name != null) {
			getAttributes().put(name, value);
		}
	}
}
