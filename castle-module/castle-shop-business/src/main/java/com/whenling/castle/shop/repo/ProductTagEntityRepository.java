package com.whenling.castle.shop.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.repo.jpa.BaseJpaRepository;
import com.whenling.castle.shop.entity.ProductTagEntity;
import com.whenling.castle.shop.entity.QProductTagEntity;

public interface ProductTagEntityRepository extends BaseJpaRepository<ProductTagEntity, Long>, QuerydslBinderCustomizer<QProductTagEntity> {

	@Override
	default void customize(QuerydslBindings bindings, QProductTagEntity root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
	}
}
