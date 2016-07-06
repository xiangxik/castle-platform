package com.whenling.castle.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class LifeCyclePostProcessor implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired(required = false)
	private List<LifeCycleListener> lifeCycleListeners;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext applicationContext = event.getApplicationContext();
		if (applicationContext.getParent() == null) {// root 启动完成
			if (lifeCycleListeners != null) {
				lifeCycleListeners.forEach(listener -> listener.onRootContextRefreshed());
			}
		} else {
			if (lifeCycleListeners != null) {
				lifeCycleListeners.forEach(listener -> listener.onServletContextRefreshed());
			}
		}
	}

}
