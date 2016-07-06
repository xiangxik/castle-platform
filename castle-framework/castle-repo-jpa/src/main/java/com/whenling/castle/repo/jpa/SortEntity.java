package com.whenling.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.whenling.castle.repo.domain.Sortable;

@MappedSuperclass
public class SortEntity<U, I extends Serializable> extends DataEntity<U, I>implements Sortable {

	private static final long serialVersionUID = -512099056914573545L;

	private Integer sortNo;

	@Override
	public Integer getSortNo() {
		return sortNo;
	}

	@Override
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

}
