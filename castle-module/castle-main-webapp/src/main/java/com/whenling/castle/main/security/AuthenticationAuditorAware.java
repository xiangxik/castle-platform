package com.whenling.castle.main.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.whenling.castle.main.entity.AdminEntity;
import com.whenling.castle.security.CustomUserDetails;

@Component
public class AuthenticationAuditorAware implements AuditorAware<AdminEntity> {

	@Override
	public AdminEntity getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		@SuppressWarnings("unchecked")
		CustomUserDetails<?, AdminEntity> user = (CustomUserDetails<?, AdminEntity>) principal;
		return user.getCustomUser();
	}

}
