package com.whenling.castle.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.whenling.castle.main.entity.AdminEntity;
import com.whenling.castle.main.entity.UserEntity;

@Service
public class PasswordService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean changeUserPassword(UserEntity user, String oldPassword, String newPassword) {
		boolean matched = Strings.isNullOrEmpty(user.getPassword()) ? true : passwordEncoder.matches(oldPassword, user.getPassword());
		if (!matched) {
			return false;
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		return true;
	}

	public boolean changeAdminPassword(AdminEntity admin, String oldPassword, String newPassword) {
		boolean matched = Strings.isNullOrEmpty(admin.getPassword()) ? true : passwordEncoder.matches(oldPassword, admin.getPassword());
		if (!matched) {
			return false;
		}

		admin.setPassword(passwordEncoder.encode(newPassword));
		return true;
	}

}
