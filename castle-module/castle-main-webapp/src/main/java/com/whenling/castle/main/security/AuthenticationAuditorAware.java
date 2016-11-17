package com.whenling.castle.main.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.security.CustomUserDetails;

@Component
public class AuthenticationAuditorAware implements AuditorAware<UserEntity> {

	@Override
	public UserEntity getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		@SuppressWarnings("unchecked")
		CustomUserDetails<?, UserEntity> user = (CustomUserDetails<?, UserEntity>) principal;
		return user.getCustomUser();
	}

}
