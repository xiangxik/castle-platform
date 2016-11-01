package com.whenling.main.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.base.Strings;
import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.main.service.UserEntityService;
import com.whenling.main.api.UserService;
import com.whenling.main.domain.User;

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
		Assert.notNull(user);
		Assert.isNull(user.getId());

		UserEntity userEntity = userEntityService.newEntity();
		userEntity.setName(user.getName());
		userEntity.setUsername(user.getUsername());
		userEntity.setMobile(user.getMobile());
		userEntity.setEmail(user.getEmail());
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
		user.setAvatar(entity.getPhoto());
		user.setLastLoginDate(entity.getLastLoginDate());
		user.setLastLoginIp(entity.getLastLoginIp());

		user.setAge(entity.getAge());
		user.setBirthday(entity.getBirthday());
		user.setSex(entity.getSex());

		return user;
	}

	@Override
	public User findOne(Long id) {
		return toUser(userEntityService.findOne(id));
	}

	@Override
	public User update(User user) {
		Assert.notNull(user);
		Assert.notNull(user.getId());

		UserEntity userEntity = userEntityService.findOne(user.getId());
		userEntity.setName(user.getName());
		userEntity.setPhoto(user.getAvatar());
		userEntity.setAge(user.getAge());
		userEntity.setBirthday(user.getBirthday());
		userEntity.setSex(user.getSex());
		userEntity.setLastLoginDate(user.getLastLoginDate());
		userEntity.setLastLoginIp(user.getLastLoginIp());
		userEntity.setSex(user.getSex());

		return toUser(userEntityService.save(userEntity));
	}

	@Override
	public void changePassword(User user, String newEncodedPassword) {
		Assert.notNull(user);
		Assert.notNull(user.getId());

		Assert.isTrue(!Strings.isNullOrEmpty(newEncodedPassword));

		UserEntity userEntity = userEntityService.findOne(user.getId());
		userEntity.setPassword(newEncodedPassword);
		userEntityService.save(userEntity);
	}

}
