package com.whenling.castle.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.main.service.UserEntityService;
import com.whenling.castle.security.UserDetailsWithIdentifier;

@Component
public class AuthenticationAuditorAware implements AuditorAware<UserEntity> {

	@Autowired
	private UserEntityService userEntityService;

	@Override
	public UserEntity getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		UserDetailsWithIdentifier<?> user = (UserDetailsWithIdentifier<?>) principal;
		return userEntityService.findOne((Long) user.getId());
	}

}
