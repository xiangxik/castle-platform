package com.whenling.castle.repo.mongo.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

public class MongoModule extends Module {

	@Override
	public String getModuleName() {
		return MongoModule.class.getSimpleName();
	}

	@Override
	public Version version() {
		return Version.unknownVersion();
	}

	@Override
	public void setupModule(SetupContext context) {
		// TODO Auto-generated method stub
		
	}

}
