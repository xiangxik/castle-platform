package com.whenling.castle.usercenter.webapp.security;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.whenling.castle.security.CustomUserDetails;
import com.whenling.castle.usercenter.api.UserService;
import com.whenling.castle.usercenter.domain.User;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Reference
	private UserService userService;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		Authentication authentication = event.getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof CustomUserDetails) {
			Serializable id = ((CustomUserDetails<?, ?>) principal).getId();
			User user = userService.findOne((Long) id);
			user.setLastLoginDate(new Date());

			WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
			user.setLastLoginIp(details.getRemoteAddress());
			userService.update(user);
		}

	}

}
