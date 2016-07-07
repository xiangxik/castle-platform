package com.whenling.castle.main.repo;

import com.whenling.castle.main.entity.UserCredentialsEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface UserCredentialsEntityRepository extends BaseJpaRepository<UserCredentialsEntity, Long> {

	UserCredentialsEntity findByAccountAndType(String account, String type);

}
