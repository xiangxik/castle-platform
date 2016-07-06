package com.whenling.castle.usercenter.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whenling.castle.usercenter.domain.User;

public interface UserService {

	Page<User> findAll(Pageable pageable);

	void test();
}
