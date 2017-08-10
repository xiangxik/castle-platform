package com.castle.repo.mongo;

public class SortDoc<U> extends DataDoc<U> {

	private static final long serialVersionUID = -9044614489676006577L;

	private Integer sortNo;

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

}
