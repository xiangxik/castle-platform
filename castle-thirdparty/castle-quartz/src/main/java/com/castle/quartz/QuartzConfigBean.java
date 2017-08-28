package com.castle.quartz;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class QuartzConfigBean {

	@Autowired
	private ObjectFactory<DataSource> dataSource;

	@Autowired
	private ObjectFactory<PlatformTransactionManager> transactionManager;

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setSchedulerName("DEFAULT");
		schedulerFactoryBean.setDataSource(dataSource.getObject());
		schedulerFactoryBean.setTransactionManager(transactionManager.getObject());
		schedulerFactoryBean.setAutoStartup(true);
		// Properties quartzProperties = new Properties();
		// schedulerFactoryBean.setQuartzProperties(quartzProperties);
		return schedulerFactoryBean;
	}
}
