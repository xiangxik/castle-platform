package com.whenling.castle.shop.repo;

import java.util.List;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.castle.repo.jpa.BaseJpaRepository;
import com.whenling.castle.shop.entity.ProductCategoryEntity;
import com.whenling.castle.shop.entity.ProductEntity;
import com.whenling.castle.shop.entity.QProductEntity;

public interface ProductEntityRepository extends BaseJpaRepository<ProductEntity, Long>, QuerydslBinderCustomizer<QProductEntity> {

	List<ProductEntity> findTop3ByProductCategoryAndIdNot(ProductCategoryEntity productCategory, Long id);
	
	@Override
	default void customize(QuerydslBindings bindings, QProductEntity root) {
		bindings.bind(root.name).first((path, value) -> path.contains(value));
	}
}
