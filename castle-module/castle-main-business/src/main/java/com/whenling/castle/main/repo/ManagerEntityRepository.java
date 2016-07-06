package com.whenling.castle.main.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.main.entity.ManagerEntity;
import com.whenling.castle.main.entity.QManagerEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface ManagerEntityRepository
		extends BaseJpaRepository<ManagerEntity, Long>, QuerydslBinderCustomizer<QManagerEntity> {

	ManagerEntity findByUsername(String username);

	@Override
	default void customize(QuerydslBindings bindings, QManagerEntity root) {
		bindings.bind(root.username).first((path, value) -> path.contains(value));
		bindings.bind(root.name).first((path, value) -> path.contains(value));
		bindings.bind(root.email).first((path, value) -> path.contains(value));
		bindings.bind(root.mobile).first((path, value) -> path.contains(value));

		bindings.excluding(root.password);
	}
}
