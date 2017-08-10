package com.castle.repo.jpa.json;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

/**
 * jpa模块
 * 
 * @作者 孔祥溪
 * @创建时间 2015年10月5日 下午10:24:34
 */
public class JpaModule extends Module {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public String getModuleName() {
		return JpaModule.class.getSimpleName();
	}

	@Override
	public Version version() {
		return Version.unknownVersion();
	}

	@Override
	public void setupModule(SetupContext context) {
		context.addSerializers(new JpaSerializers(entityManager));
		context.addDeserializers(new JpaDeserializers(entityManager));
	}

}
