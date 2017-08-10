package com.castle.repo.mongo.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

public class MongoModule extends Module {

	@Autowired
	private MongoOperations mongoOperations;

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
		context.addSerializers(new MongoSerializers());
		context.addDeserializers(new MongoDeserializers(mongoOperations));
	}

}
