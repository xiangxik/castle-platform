package com.whenling.sample.model;

import java.io.Serializable;

public class Domain<I extends Serializable> implements Serializable {

	private static final long serialVersionUID = -1457122496567259973L;

	private I id;

	public I getId() {
		return id;
	}

	public void setId(I id) {
		this.id = id;
	}

}
