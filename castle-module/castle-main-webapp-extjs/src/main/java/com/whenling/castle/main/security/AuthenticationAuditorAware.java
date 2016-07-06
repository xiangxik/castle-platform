package com.whenling.castle.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.whenling.castle.main.entity.ManagerEntity;
import com.whenling.castle.main.service.ManagerEntityService;
import com.whenling.castle.security.UserDetailsWithIdentifier;

@Component
public class AuthenticationAuditorAware implements AuditorAware<ManagerEntity> {

	@Autowired
	private ManagerEntityService managerEntityService;

	@Override
	public ManagerEntity getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		UserDetailsWithIdentifier<?> user = (UserDetailsWithIdentifier<?>) principal;
		return managerEntityService.findOne((Long) user.getId());
	}

}
