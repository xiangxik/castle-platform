package com.whenling.castle.shop.repo;

import com.whenling.castle.repo.jpa.HierarchicalJpaRepository;
import com.whenling.castle.shop.entity.ProductCategoryEntity;

public interface ProductCategoryEntityRepository extends HierarchicalJpaRepository<ProductCategoryEntity, Long> {

}
