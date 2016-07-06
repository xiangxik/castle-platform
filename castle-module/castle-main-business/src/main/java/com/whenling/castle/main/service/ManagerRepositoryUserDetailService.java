package com.whenling.castle.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.whenling.castle.main.entity.ManagerEntity;
import com.whenling.castle.main.repo.ManagerEntityRepository;
import com.whenling.castle.security.UserDetailsWithIdentifier;

@Service
public class ManagerRepositoryUserDetailService implements UserDetailsService {

	@Autowired
	private ManagerEntityRepository managerEntityRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ManagerEntity managerEntity = managerEntityRepository.findByUsername(username);
		if (managerEntity == null) {
			throw new UsernameNotFoundException("Could not find user " + username);
		}
		return new UserDetailsWithIdentifier<>(managerEntity.getId(), username, managerEntity.getPassword(),
				managerEntity.isEnabled(), !managerEntity.isAccountExpired(), !managerEntity.isCredentialsExpired(),
				!managerEntity.isLocked(), AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

}
