package com.whenling.castle.template.thymeleaf.expression;

import org.springframework.security.core.Authentication;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;

import com.whenling.castle.security.CustomUserDetails;

public class AuthVariable {

	private static final AuthVariable INSTANCE = new AuthVariable();

	private AuthVariable() {
	}

	public Object getCurrentUser() {
		Authentication authentication = AuthUtils.getAuthenticationObject();
		if (authentication == null) {
			return null;
		}

		CustomUserDetails<?, ?> userDetails = (CustomUserDetails<?, ?>) authentication.getPrincipal();
		return userDetails.getCustomUser();
	}

	public static AuthVariable getInstance() {
		return INSTANCE;
	}

}
