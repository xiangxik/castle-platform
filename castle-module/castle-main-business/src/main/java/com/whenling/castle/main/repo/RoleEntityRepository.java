package com.whenling.castle.main.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.main.entity.QRoleEntity;
import com.whenling.castle.main.entity.RoleEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface RoleEntityRepository extends BaseJpaRepository<RoleEntity, Long>, QuerydslBinderCustomizer<QRoleEntity> {
	@Override
	default void customize(QuerydslBindings bindings, QRoleEntity root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
		bindings.bind(root.code).first((path, value) -> path.contains(value));
	}
}
