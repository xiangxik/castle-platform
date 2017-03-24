package com.whenling.sample.repo;

import com.whenling.castle.repo.jpa.BaseJpaRepository;
import com.whenling.sample.entity.UserEntity;

public interface UserRepository extends BaseJpaRepository<UserEntity, Long> {

}
