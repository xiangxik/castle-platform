package com.whenling.castle.usercenter.webapp.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.whenling.castle.core.helper.Patterns;
import com.whenling.castle.security.UserDetailsWithIdentifier;
import com.whenling.castle.usercenter.api.UserService;
import com.whenling.castle.usercenter.domain.User;

@Component
public class RemoteUserDetailsService implements UserDetailsService {

	@Reference
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		if (Patterns.isMobile(username)) {
			user = userService.findByMobile(username);
		} else if (Patterns.isEmail(username)) {
			user = userService.findByEmail(username);
		} else if (Patterns.isUsername(username)) {
			user = userService.findByUsername(username);
		}
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}

		return new UserDetailsWithIdentifier<>(user.getId(), user.getUsername(), userService.getPassword(user), user.isEnabled(), !user.isAccountExpired(), !user.isCredentialsExpired(),
				!user.isLocked(), AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

}
