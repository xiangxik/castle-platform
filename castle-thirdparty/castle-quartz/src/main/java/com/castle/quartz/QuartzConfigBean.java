package com.castle.quartz;

import javax.sql.DataSource;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class QuartzConfigBean {

	@Value("${quartz.schedulerName?:DEFAULT}")
	private String schedulerName;

	@Autowired
	private ObjectFactory<DataSource> dataSource;

	@Autowired
	private ObjectFactory<PlatformTransactionManager> transactionManager;

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setSchedulerName(schedulerName);
		schedulerFactoryBean.setDataSource(dataSource.getObject());
		schedulerFactoryBean.setTransactionManager(transactionManager.getObject());
		schedulerFactoryBean.setAutoStartup(true);
		schedulerFactoryBean.setJobFactory(jobFactory());
		// Properties quartzProperties = new Properties();
		// schedulerFactoryBean.setQuartzProperties(quartzProperties);
		return schedulerFactoryBean;
	}

	@Bean
	public JobFactory jobFactory() {
		return new SpringJobFactory();
	}
}
