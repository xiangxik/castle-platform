package com.whenling.castle.cms.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.cms.entity.QQuestionEntity;
import com.whenling.castle.cms.entity.QuestionEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface QuestionEntityRepository extends BaseJpaRepository<QuestionEntity, Long>, QuerydslBinderCustomizer<QQuestionEntity> {

	@Override
	default void customize(QuerydslBindings bindings, QQuestionEntity root) {
		bindings.bind(root.title).first((path, value) -> path.contains(value));
	}
	
}
