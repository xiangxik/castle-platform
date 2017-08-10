package com.castle.integration.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;

@Service
public class LifeCyclePostProcessor implements ApplicationListener<ApplicationContextEvent> {

	@Autowired(required = false)
	private List<LifeCycleListener> lifeCycleListeners;

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if (event instanceof ContextStartedEvent) {
			ApplicationContext applicationContext = event.getApplicationContext();
			if (applicationContext.getParent() == null) {// root 开始
				System.out.println("Castle Framework Starting...");
			}
		}

		if (event instanceof ContextRefreshedEvent) {
			ApplicationContext applicationContext = event.getApplicationContext();
			if(lifeCycleListeners != null) {
				AnnotationAwareOrderComparator.sort(lifeCycleListeners);
			}
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

}
