package com.castle.repo.domain;

public interface OrganizationSupport<T extends OrganizationBean> {

	T getOrg();

	void setOrg(T org);

}
