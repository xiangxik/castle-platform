package com.whenling.castle.usercenter.api;

import com.whenling.castle.usercenter.domain.User;

public interface UserService {

	User findByUsername(String username);

	User findByEmail(String email);

	User findByMobile(String mobile);

	User findOne(Long id);

	String getPassword(User user);

	User update(User user);

	User register(User user, String encodedPassword);
}
