package com.whenling.castle.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.HierarchicalEntity;

@Entity
@Table(name = "sys_organization")
public class OrganizationEntity extends HierarchicalEntity<ManagerEntity, Long, OrganizationEntity> {

	private static final long serialVersionUID = -7362271235798217688L;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(nullable = false, length = 50)
	private String code;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(nullable = false, length = 100)
	private String name;

	@NotNull
	@Column(nullable = false, length = 50)
	private OrgType type;

	@Size(max = 100)
	@Column(length = 200)
	private String address;

	@Size(max = 50)
	@Column(length = 50)
	private String zipCode;

	@Size(max = 20)
	@Column(length = 50)
	private String master;

	@Pattern(regexp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$")
	@Column(length = 50)
	private String phone;

	@Size(max = 20)
	@Column(length = 50)
	private String fax;

	@Pattern(regexp = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
	@Column(length = 50)
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "primary_leader_id")
	private ManagerEntity primaryLeader;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "secondary_leader_id")
	private ManagerEntity secondaryLeader;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OrgType getType() {
		return type;
	}

	public void setType(OrgType type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ManagerEntity getPrimaryLeader() {
		return primaryLeader;
	}

	public void setPrimaryLeader(ManagerEntity primaryLeader) {
		this.primaryLeader = primaryLeader;
	}

	public ManagerEntity getSecondaryLeader() {
		return secondaryLeader;
	}

	public void setSecondaryLeader(ManagerEntity secondaryLeader) {
		this.secondaryLeader = secondaryLeader;
	}

	public enum OrgType {
		company, department, group
	}
}
