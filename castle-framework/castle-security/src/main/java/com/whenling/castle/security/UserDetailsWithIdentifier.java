package com.whenling.castle.security;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsWithIdentifier<I extends Serializable> extends User implements UserDetails {

	private static final long serialVersionUID = -7547219125237818065L;

	private I id;

	public UserDetailsWithIdentifier(I id, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		this.id = id;
	}

	public I getId() {
		return id;
	}

}
