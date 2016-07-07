package com.whenling.castle.usercenter.provider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.whenling.castle.integration.dubbo.Provider;
import com.whenling.castle.usercenter.api.UserService;
import com.whenling.castle.usercenter.domain.User;

@Provider
@Service
public class UserServiceProvider implements UserService {

	@Override
	public Page<User> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public void test() {
		System.out.println("test");
	}

}
