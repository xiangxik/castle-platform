package com.whenling.castle.main.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.whenling.castle.main.entity.AdminEntity;
import com.whenling.castle.main.repo.AdminEntityRepository;
import com.whenling.castle.security.CustomUserDetails;

@Service
public class AdminDetailsService implements UserDetailsService {

	@Autowired
	private AdminEntityRepository adminEntityRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AdminEntity adminEntity = adminEntityRepository.findByUsername(username);
		if (adminEntity == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}

		return new EntityUserDetails(adminEntity.getId(), adminEntity.getUsername(), adminEntity.getPassword(), true, true, true, true,
				AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

	public class EntityUserDetails extends CustomUserDetails<Long, AdminEntity> {

		private static final long serialVersionUID = 8220061317304759492L;

		public EntityUserDetails(Long id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
				boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
			super(id, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		}

		@Override
		public AdminEntity getCustomUser() {
			return adminEntityRepository.getOne(getId());
		}

	}
}
