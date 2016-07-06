package com.whenling.castle.main.security;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.whenling.castle.main.entity.ManagerEntity;
import com.whenling.castle.main.service.ManagerEntityService;
import com.whenling.castle.security.UserDetailsWithIdentifier;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private ManagerEntityService managerEntityService;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		Authentication authentication = event.getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetailsWithIdentifier) {
			Serializable id = ((UserDetailsWithIdentifier<?>) principal).getId();
			ManagerEntity managerEntity = managerEntityService.findOne((Long) id);
			managerEntity.setLastLoginDate(new Date());

			WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
			managerEntity.setLastLoginIp(details.getRemoteAddress());
			managerEntityService.save(managerEntity);
		}

	}

}
