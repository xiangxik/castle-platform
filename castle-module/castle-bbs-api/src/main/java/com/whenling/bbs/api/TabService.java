package com.whenling.bbs.api;

import java.util.List;

import com.whenling.bbs.domain.Tab;

public interface TabService {

	Tab findOne(Long id);
	
	Tab findByCode(String code);
	
	List<Tab> findAll();
	
}
