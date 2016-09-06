package com.whenling.castle.main.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.main.entity.QUserEntity;
import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface UserEntityRepository extends BaseJpaRepository<UserEntity, Long>, QuerydslBinderCustomizer<QUserEntity> {

	UserEntity findByUsername(String username);

	UserEntity findByEmail(String email);

	UserEntity findByMobile(String mobile);

	@Override
	default void customize(QuerydslBindings bindings, QUserEntity root) {
		bindings.bind(root.username).first((path, value) -> path.contains(value));
		bindings.bind(root.name).first((path, value) -> path.contains(value));
		bindings.bind(root.email).first((path, value) -> path.contains(value));
		bindings.bind(root.mobile).first((path, value) -> path.contains(value));
	}
}
