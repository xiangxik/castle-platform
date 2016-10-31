package com.whenling.bbs.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.whenling.bbs.api.TopicService;
import com.whenling.bbs.domain.Topic;
import com.whenling.castle.bbs.entity.TabEntity;
import com.whenling.castle.bbs.entity.TopicEntity;
import com.whenling.castle.bbs.service.TopicEntityService;
import com.whenling.castle.main.entity.UserEntity;

@com.alibaba.dubbo.config.annotation.Service
@Service
public class TopicServiceProvider implements TopicService {

	@Autowired
	private TopicEntityService topicEntityService;

	@Override
	public Topic findOne(Long id) {
		return toDetailDomain(topicEntityService.findOne(id));
	}

	@Override
	public Page<Topic> findAll(Pageable pageable) {
		return topicEntityService.findAll(pageable).map(this::toSimpleDomain);
	}

	public Topic toDetailDomain(TopicEntity entity) {
		Topic topic = toSimpleDomain(entity);
		topic.setContent(entity.getContent());
		return topic;
	}

	public Topic toSimpleDomain(TopicEntity entity) {
		Topic topic = new Topic();
		topic.setId(entity.getId());
		topic.setTitle(entity.getTitle());
		topic.setGood(entity.isGood());
		topic.setPublishedDate(entity.getPublishedDate());

		UserEntity publisher = entity.getPublisher();
		if (publisher != null) {
			topic.setPublisherId(publisher.getId());
			topic.setPublisherName(publisher.getName());
		}

		topic.setReplyCount(entity.getReplyCount());
		topic.setStick(entity.isStick());

		TabEntity tab = entity.getTab();
		if (tab != null) {
			topic.setTabId(tab.getId());
			topic.setTabName(tab.getName());
			topic.setTabCode(tab.getCode());
		}

		topic.setViewCount(entity.getViewCount());
		return topic;
	}

}
