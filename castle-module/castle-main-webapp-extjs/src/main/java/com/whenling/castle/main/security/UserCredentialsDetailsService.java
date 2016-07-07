package com.whenling.castle.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.main.repo.UserEntityRepository;
import com.whenling.castle.security.UserDetailsWithIdentifier;

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

		return new UserDetailsWithIdentifier<>(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.isEnabled(), !userEntity.isAccountExpired(),
				!userEntity.isCredentialsExpired(), !userEntity.isLocked(), AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

}
