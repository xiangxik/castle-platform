package com.whenling.castle.main.security;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.main.service.UserEntityService;
import com.whenling.castle.security.UserDetailsWithIdentifier;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private UserEntityService userEntityService;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		Authentication authentication = event.getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetailsWithIdentifier) {
			Serializable id = ((UserDetailsWithIdentifier<?>) principal).getId();
			UserEntity userEntity = userEntityService.findOne((Long) id);
			userEntity.setLastLoginDate(new Date());

			WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
			userEntity.setLastLoginIp(details.getRemoteAddress());
			userEntityService.save(userEntity);
		}

	}

}
