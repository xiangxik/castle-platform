package com.whenling.castle.main.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.main.repo.UserEntityRepository;
import com.whenling.castle.security.CustomUserDetails;

@Service
public class UserCredentialsDetailsService implements UserDetailsService {

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userEntityRepository.findByUsername(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}

		return new EntityUserDetails(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.isEnabled(), !userEntity.isAccountExpired(), !userEntity.isCredentialsExpired(),
				!userEntity.isLocked(), AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

	public class EntityUserDetails extends CustomUserDetails<Long, UserEntity> {

		private static final long serialVersionUID = 8220061317304759492L;

		public EntityUserDetails(Long id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
				Collection<? extends GrantedAuthority> authorities) {
			super(id, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		}

		@Override
		public UserEntity getCustomUser() {
			return userEntityRepository.findOne(getId());
		}

	}
}
