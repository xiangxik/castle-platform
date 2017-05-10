package com.whenling.castle.website.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.whenling.castle.data.entity.User;
import com.whenling.castle.data.repo.UserRepository;
import com.whenling.castle.security.CustomUserDetails;

public class DefaultDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}

		return new CurrentUserDetails(user.getId(), user.getUsername(), user.getPassword(), true, true, true, true,
				AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

	public class CurrentUserDetails extends CustomUserDetails<Long, User> {

		public CurrentUserDetails(Long id, String username, String password, boolean enabled, boolean accountNonExpired,
				boolean credentialsNonExpired, boolean accountNonLocked,
				Collection<? extends GrantedAuthority> authorities) {
			super(id, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
					authorities);
		}

		private static final long serialVersionUID = 8220061317304759492L;

		@Override
		public User getCustomUser() {
			return userRepository.getOne(getId());
		}
	}
}
