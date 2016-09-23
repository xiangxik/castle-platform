package com.whenling.castle.cms.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.cms.entity.ArticleTagEntity;
import com.whenling.castle.cms.entity.QArticleTagEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface ArticleTagEntityRepository extends BaseJpaRepository<ArticleTagEntity, Long>, QuerydslBinderCustomizer<QArticleTagEntity> {

	@Override
	default void customize(QuerydslBindings bindings, QArticleTagEntity root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
	}
}
