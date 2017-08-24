package com.castle.repo.jpa;

import com.castle.repo.domain.OrganizationBean;

public interface WithOrgAware<T extends OrganizationBean> {

	T getCurrentOrg();
}
