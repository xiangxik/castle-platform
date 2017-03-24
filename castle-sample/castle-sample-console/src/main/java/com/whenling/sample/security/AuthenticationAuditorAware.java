package com.whenling.sample.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.whenling.sample.entity.AdminEntity;
import com.whenling.sample.security.AdminDetailsService.CurrentUserDetails;

@Component
public class AuthenticationAuditorAware implements AuditorAware<AdminEntity> {

	@Override
	public AdminEntity getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof CurrentUserDetails) {
			return ((CurrentUserDetails) principal).getCustomUser();
		}
		return null;
	}

}
