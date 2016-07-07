package com.whenling.castle.usercenter.api;

import com.whenling.castle.usercenter.domain.User;

public interface UserService {

	User findByUsername(String username);

	User findByEmail(String email);

	User findByMobile(String mobile);

	String getPassword(User user);

	void test();

	User register(User user, String encodedPassword);
}
