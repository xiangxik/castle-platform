package com.whenling.castle.main.repo;

import com.whenling.castle.main.entity.MenuEntity;
import com.whenling.castle.repo.jpa.HierarchicalJpaRepository;

public interface MenuEntityRepository extends HierarchicalJpaRepository<MenuEntity, Long> {

	MenuEntity findByCode(String code);

}
