package com.castle.quartz.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.castle.quartz.entity.JobEntity;
import com.castle.repo.jpa.EntityService;

@Service
public class JobService extends EntityService<JobEntity, String> {

	@Override
	public <S extends JobEntity> S save(S entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <S extends JobEntity> S saveAndFlush(S entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <S extends JobEntity> List<S> save(Iterable<S> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Iterable<? extends JobEntity> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(JobEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAllInBatch() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteInBatch(Iterable<JobEntity> entities) {
		throw new UnsupportedOperationException();
	}
}
