package com.whenling.castle.bbs.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whenling.castle.bbs.entity.TabEntity;
import com.whenling.castle.bbs.entity.TopicEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface TopicEntityRepository extends BaseJpaRepository<TopicEntity, Long> {

	Page<TopicEntity> findByTab(TabEntity tab, Pageable pageable);
	
	Page<TopicEntity> findByGoodTrue(Pageable pageable);
	
}
