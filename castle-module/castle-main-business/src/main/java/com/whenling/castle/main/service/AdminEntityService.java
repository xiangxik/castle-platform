package com.whenling.castle.main.service;

import org.springframework.stereotype.Service;

import com.whenling.castle.main.entity.AdminEntity;

@Service
public class AdminEntityService extends EntityService<AdminEntity, Long> {

	public AdminEntity defaultCreate(String name, String username, String encodedPassword, String mobile, String email) {
		AdminEntity admin = newEntity();
		admin.setRoot(true);
		admin.setName(name);
		admin.setUsername(username);
		admin.setPassword(encodedPassword);
		admin.setMobile(mobile);
		admin.setEmail(email);
		return save(admin);
	}
}
