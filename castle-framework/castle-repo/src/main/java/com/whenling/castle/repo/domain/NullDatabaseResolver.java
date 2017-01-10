package com.whenling.castle.repo.domain;

public class NullDatabaseResolver implements DatabaseResolver {

	@Override
	public String resolveDatabaseName() {
		return null;
	}

	@Override
	public void setCurrentDatabase(String database) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefaultDatabase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
