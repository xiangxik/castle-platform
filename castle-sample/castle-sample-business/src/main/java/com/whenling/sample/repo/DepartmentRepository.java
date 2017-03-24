package com.whenling.sample.repo;

import com.whenling.castle.repo.jpa.HierarchicalJpaRepository;
import com.whenling.sample.entity.DepartmentEntity;

public interface DepartmentRepository extends HierarchicalJpaRepository<DepartmentEntity, Long> {

}
