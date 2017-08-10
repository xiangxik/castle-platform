package com.castle.repo.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 避免第三方服务进行序列化时出错。建立的代理类。主要用于参数传输
 * 
 * @author ken
 *
 */
public class PageRequestProxy extends PageRequest {

	private static final long serialVersionUID = 7224707181900601053L;

	public PageRequestProxy() {
		this(0, 20, null);
	}

	public PageRequestProxy(int page, int size, Sort sort) {
		super(page, size, sort);
	}

}
