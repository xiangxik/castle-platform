package com.whenling.sample.repo;

import com.whenling.castle.repo.jpa.BaseJpaRepository;
import com.whenling.sample.entity.AdminEntity;

public interface AdminRepository extends BaseJpaRepository<AdminEntity, Long> {

	AdminEntity findByUsername(String username);
}
