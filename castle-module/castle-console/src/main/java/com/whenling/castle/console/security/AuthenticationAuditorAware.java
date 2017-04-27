package com.whenling.castle.console.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.whenling.castle.console.entity.Admin;
import com.whenling.castle.console.security.AdminDetailsService.CurrentUserDetails;

@Component
public class AuthenticationAuditorAware implements AuditorAware<Admin> {

	@Override
	public Admin getCurrentAuditor() {
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
