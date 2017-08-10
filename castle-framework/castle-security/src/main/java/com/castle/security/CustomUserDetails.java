package com.castle.security;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class CustomUserDetails<I extends Serializable, U> extends User implements UserDetails {

	private static final long serialVersionUID = 8063484673226426535L;

	private final I id;

	public CustomUserDetails(I id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		this.id = id;
	}

	public I getId() {
		return id;
	}

	public abstract U getCustomUser();
}
