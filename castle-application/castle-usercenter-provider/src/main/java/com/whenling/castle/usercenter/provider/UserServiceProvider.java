package com.whenling.castle.usercenter.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.main.service.UserEntityService;
import com.whenling.castle.usercenter.api.UserService;
import com.whenling.castle.usercenter.domain.User;

@com.alibaba.dubbo.config.annotation.Service
@Service
public class UserServiceProvider implements UserService {

	@Autowired
	private UserEntityService userEntityService;

	@Override
	public User findByUsername(String username) {
		return toUser(userEntityService.findByUsername(username));
	}

	@Override
	public User findByEmail(String email) {
		return toUser(userEntityService.findByEmail(email));
	}

	@Override
	public User findByMobile(String mobile) {
		return toUser(userEntityService.findByMobile(mobile));
	}

	@Override
	public String getPassword(User user) {
		if (user.getId() == null) {
			return null;
		}
		UserEntity userEntity = userEntityService.findOne(user.getId());
		return userEntity == null ? null : userEntity.getPassword();
	}

	@Override
	public User register(User user, String encodedPassword) {
		UserEntity userEntity = toEntity(user);
		userEntity.setPassword(encodedPassword);
		return toUser(userEntityService.save(userEntity));
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
		user.setLastLoginDate(entity.getLastLoginDate());
		user.setLastLoginIp(entity.getLastLoginIp());
		return user;
	}

	protected UserEntity toEntity(User user) {
		if (user == null) {
			return null;
		}

		UserEntity userEntity = user.getId() == null ? userEntityService.newEntity() : userEntityService.findOne(user.getId());
		userEntity.setName(user.getName());
		userEntity.setMobile(user.getMobile());
		userEntity.setEmail(user.getEmail());
		userEntity.setLastLoginDate(user.getLastLoginDate());
		userEntity.setLastLoginIp(user.getLastLoginIp());
		return userEntity;
	}

	@Override
	public User findOne(Long id) {
		return toUser(userEntityService.findOne(id));
	}

	@Override
	public User update(User user) {
		Assert.notNull(user);
		Assert.notNull(user.getId());

		return toUser(userEntityService.save(toEntity(user)));
	}

}
