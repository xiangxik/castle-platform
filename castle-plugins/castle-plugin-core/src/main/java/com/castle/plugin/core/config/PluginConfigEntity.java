package com.castle.plugin.core.config;

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

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.castle.repo.domain.Disabledable;
import com.castle.repo.domain.Sortable;
import com.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "tbl_plugin_config")
public class PluginConfigEntity extends BaseEntity<Long>
		implements Disabledable, Sortable, Comparable<PluginConfigEntity> {

	private static final long serialVersionUID = 411030854001556269L;

	@Column(nullable = false, updatable = false, unique = true, length = 100)
	private String pluginId;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tbl_plugin_config_attribute")
	@MapKeyColumn(name = "name", length = 100)
	private Map<String, String> attributes = new HashMap<>();

	private Integer sortNo;

	private boolean disabled = false;

	public String getPluginId() {
		return pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Integer getSortNo() {
		return sortNo;
	}

	@Override
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	@Override
	public int compareTo(PluginConfigEntity o) {
		return new CompareToBuilder().append(getSortNo(), o.getSortNo()).append(getId(), o.getId()).toComparison();
	}

	@Override
	public boolean isDisabled() {
		return disabled;
	}

	@Override
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public void markDisabled() {
		this.disabled = true;
	}

	/**
	 * 获取属性值
	 * 
	 * @param name
	 *            属性名称
	 * @return 属性值
	 */
	@Transient
	public String getAttribute(String name) {
		if (getAttributes() != null && name != null) {
			return getAttributes().get(name);
		} else {
			return null;
		}
	}

	/**
	 * 设置属性值
	 * 
	 * @param name
	 *            属性名称
	 * @param value
	 *            属性值
	 */
	@Transient
	public void setAttribute(String name, String value) {
		if (getAttributes() != null && name != null) {
			getAttributes().put(name, value);
		}
	}
}
