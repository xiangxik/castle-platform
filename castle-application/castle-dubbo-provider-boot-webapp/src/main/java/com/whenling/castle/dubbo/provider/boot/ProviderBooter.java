package com.whenling.castle.dubbo.provider.boot;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.whenling.castle.core.StaticConfigSupplier;
import com.whenling.mdm.provider.support.DubboProviderMain;

public class ProviderBooter implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		StaticConfigSupplier.append("config.properties");
		
		try {
			DubboProviderMain.main(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
