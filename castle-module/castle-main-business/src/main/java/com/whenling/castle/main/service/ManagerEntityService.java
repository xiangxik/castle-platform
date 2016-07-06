package com.whenling.castle.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.whenling.castle.main.entity.ManagerEntity;

@Service
public class ManagerEntityService extends EntityService<ManagerEntity, Long> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean changePassword(ManagerEntity manager, String oldPassword, String newPassword) {
		boolean matched = Strings.isNullOrEmpty(manager.getPassword()) ? true
				: passwordEncoder.matches(oldPassword, manager.getPassword());
		if (!matched) {
			return false;
		}

		manager.setPassword(passwordEncoder.encode(newPassword));
		return true;
	}

}
