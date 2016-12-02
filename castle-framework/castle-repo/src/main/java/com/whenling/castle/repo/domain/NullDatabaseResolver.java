package com.whenling.castle.repo.domain;

public class NullDatabaseResolver implements DatabaseResolver {

	@Override
	public String resolveDatabaseName() {
		return null;
	}

}
