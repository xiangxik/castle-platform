package com.whenling.bbs.api;

import org.springframework.data.domain.Page;

import com.whenling.bbs.domain.Topic;

public interface TopicService {

	Topic findOne(Long id);

	Page<Topic> findAll();

}
