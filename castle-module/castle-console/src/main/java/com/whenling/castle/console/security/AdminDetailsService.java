package com.whenling.castle.console.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.whenling.castle.console.entity.Admin;
import com.whenling.castle.console.repo.AdminRepository;
import com.whenling.castle.security.CustomUserDetails;

public class AdminDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Admin admin = adminRepository.findByUsername(username);
		if (admin == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}

		return new CurrentUserDetails(admin.getId(), admin.getUsername(), admin.getPassword(), true, true, true, true,
				AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

	public class CurrentUserDetails extends CustomUserDetails<Long, Admin> {

		public CurrentUserDetails(Long id, String username, String password, boolean enabled, boolean accountNonExpired,
				boolean credentialsNonExpired, boolean accountNonLocked,
				Collection<? extends GrantedAuthority> authorities) {
			super(id, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
					authorities);
		}

		private static final long serialVersionUID = 8220061317304759492L;

		@Override
		public Admin getCustomUser() {
			return adminRepository.getOne(getId());
		}
	}
}
