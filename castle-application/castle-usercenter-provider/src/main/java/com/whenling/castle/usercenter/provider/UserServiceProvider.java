package com.whenling.castle.usercenter.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.castle.integration.dubbo.Provider;
import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.main.repo.UserEntityRepository;
import com.whenling.castle.usercenter.api.UserService;
import com.whenling.castle.usercenter.domain.User;

@Provider
@Service
public class UserServiceProvider implements UserService {

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Override
	public void test() {
		System.out.println("test");
	}

	@Override
	public User findByUsername(String username) {
		return toUser(userEntityRepository.findByUsername(username));
	}

	@Override
	public User findByEmail(String email) {
		return toUser(userEntityRepository.findByEmail(email));
	}

	@Override
	public User findByMobile(String mobile) {
		return toUser(userEntityRepository.findByMobile(mobile));
	}

	@Override
	public String getPassword(User user) {
		if (user.getId() == null) {
			return null;
		}
		UserEntity userEntity = userEntityRepository.findOne(user.getId());
		return userEntity == null ? null : userEntity.getPassword();
	}

	protected User toUser(UserEntity entity) {
		if (entity == null) {
			return null;
		}

		User user = new User();
		user.setId(entity.getId());
		user.setUsername(entity.getUsername());
		user.setName(entity.getName());
		user.setEmail(entity.getEmail());
		user.setMobile(entity.getMobile());
		return user;
	}

	protected UserEntity toEntity(User user) {
		if (user == null) {
			return null;
		}
		return null;
	}

}
