package com.whenling.castle.main.repo;

import com.whenling.castle.main.entity.AdminEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface AdminEntityRepository extends BaseJpaRepository<AdminEntity, Long> {

	AdminEntity findByUsername(String username);

}
