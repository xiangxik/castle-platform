package com.whenling.castle.main.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.main.entity.OrganizationEntity;
import com.whenling.castle.main.entity.QOrganizationEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface OrganizationEntityRepository extends BaseJpaRepository<OrganizationEntity, Long>, QuerydslBinderCustomizer<QOrganizationEntity> {
	@Override
	default void customize(QuerydslBindings bindings, QOrganizationEntity root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
		bindings.bind(root.code).first((path, value) -> path.contains(value));
	}
}
