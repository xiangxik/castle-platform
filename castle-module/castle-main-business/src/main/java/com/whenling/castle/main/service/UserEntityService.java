package com.whenling.castle.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.main.repo.UserEntityRepository;

@Service
public class UserEntityService extends EntityService<UserEntity, Long> {

	@Autowired
	private UserEntityRepository userEntityRepository;

	public UserEntity findByUsername(String username) {
		return userEntityRepository.findByUsername(username);
	}

	public UserEntity findByEmail(String email) {
		return userEntityRepository.findByEmail(email);
	}

	public UserEntity findByMobile(String mobile) {
		return userEntityRepository.findByMobile(mobile);
	}

}
