package com.whenling.bbs.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whenling.bbs.domain.Topic;

public interface TopicService {

	Topic findOne(Long id);

	Page<Topic> findAll(Pageable pageable);
	
	void save(Topic topic);

}
