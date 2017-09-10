package com.castle.security;

import org.springframework.data.domain.AuditorAware;

public class AuthObject<T> {

	private AuditorAware<T> auditorAware;

	public AuthObject(AuditorAware<T> auditorAware) {
		this.auditorAware = auditorAware;
	}

	public T getCurrentUser() {
		return auditorAware == null ? null : auditorAware.getCurrentAuditor();
	}
}
