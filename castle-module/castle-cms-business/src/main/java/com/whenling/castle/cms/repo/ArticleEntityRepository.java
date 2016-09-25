package com.whenling.castle.cms.repo;

import java.util.List;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.cms.entity.ArticleEntity;
import com.whenling.castle.cms.entity.QArticleEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface ArticleEntityRepository extends BaseJpaRepository<ArticleEntity, Long>, QuerydslBinderCustomizer<QArticleEntity> {

	List<ArticleEntity> findTop2ByOrderByCreatedDateDesc();
	
	@Override
	default void customize(QuerydslBindings bindings, QArticleEntity root) {
		bindings.bind(root.title).first((path, value) -> path.contains(value));
	}

}
