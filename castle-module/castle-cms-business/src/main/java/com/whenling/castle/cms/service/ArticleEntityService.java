package com.whenling.castle.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.castle.cms.entity.ArticleEntity;
import com.whenling.castle.cms.repo.ArticleEntityRepository;
import com.whenling.castle.main.service.EntityService;

@Service
public class ArticleEntityService extends EntityService<ArticleEntity, Long> {

	@Autowired
	private ArticleEntityRepository articleEntityRepository;
	
	public List<ArticleEntity> findTop2() {
		return articleEntityRepository.findTop2ByOrderByCreatedDateDesc();
	}
	
}
