package com.whenling.sample.repo;

import com.whenling.castle.repo.jpa.HierarchicalJpaRepository;
import com.whenling.sample.entity.MenuItemEntity;

public interface MenuItemRepository extends HierarchicalJpaRepository<MenuItemEntity, Long> {

}
