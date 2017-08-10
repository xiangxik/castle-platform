package com.castle.repo.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableJpaRepositories(basePackages = { "com.castle" }, includeFilters = {
		@Filter(value = EntityRepository.class, type = FilterType.ASSIGNABLE_TYPE) }, repositoryImplementationPostfix = "Impl", repositoryFactoryBeanClass = EntityRepositoryFactoryBean.class)
@EnableJpaAuditing
public class JpaRepositoryConfigBean {

	@Value("${jdbc.driver_class?:com.mysql.jdbc.Driver}")
	private String jdbcDriverClass;

	@Value("${jdbc.url?:jdbc:mysql://127.0.0.1:3306/mdm?useUnicode=true&characterEncoding=utf-8&useSSL=false}")
	private String jdbcUrl;

	@Value("${jdbc.username?:root}")
	private String jdbcUsername;

	@Value("${jdbc.password?:M_sql5535y19}")
	private String jdbcPassword;

	@Value("${jdbc.multiple?:false}")
	private Boolean multipleDataSource;

	@Value("${hibernate.hbm2ddl.auto?:update}")
	private String hibernateHbm2ddlAuto;

	@Value("${hibernate.dialect?:org.hibernate.dialect.MySQL5Dialect}")
	private String hibernateDialect;

	@Value("${hibernate.show_sql?:true}")
	private String hibernateShowSql;

	@Value("${hibernate.format_sql?:true}")
	private String hibernateFormatSql;

	@Value("${hibernate.current_session_context_class?:org.springframework.orm.hibernate5.SpringSessionContext}")
	private String hibernateCurrentSessionContextClass;

	@Value("${javax.persistence.validation.mode?:none}")
	private String javaxPersistenceValidationMode;

	@Value("${hibernate.query.substitutions?:true 1, false 0}")
	private String hibernateQuerySubstitutions;

	@Value("${hibernate.default_batch_fetch_size?:16}")
	private String hibernateDefaultBatchFetchSize;

	@Value("${hibernate.max_fetch_depth?:4}")
	private String hibernateMaxFetchDepth;

	@Value("${hibernate.enable_lazy_load_no_trans?:true}")
	private String hibernateEnableLazyLoadNoTrans;

	@Value("${hibernate.bytecode.use_reflection_optimizer?:true}")
	private String hibernateBytecodeUseReflectionOptimizer;

	@Value("${hibernate.cache.use_second_level_cache?:false}")
	private String hibernateCacheUseSecondLevelCache;

	@Value("${javax.persistence.sharedCache.mode?:ALL}")
	private String javaxPersistenceSharedCacheMode;

	@Value("${hibernate.generate_statistics?:false}")
	private String hibernateGenerateStatistics;

	@Value("${hibernate.cache.use_query_cache?:false}")
	private String hibernateCacheUseQueryCache;

	@Value("${jpa.packages_scan?:com.castle}")
	private String packageScan;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(Boolean.TRUE);
		vendorAdapter.setShowSql(Boolean.TRUE);

		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(packageScan.split(","));
		factory.setJpaDialect(new HibernateJpaDialect());

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.id.new_generator_mappings", false);
		jpaProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
		jpaProperties.put("hibernate.dialect", hibernateDialect);
		jpaProperties.put("hibernate.show_sql", hibernateShowSql);
		jpaProperties.put("hibernate.format_sql", hibernateFormatSql);
		jpaProperties.put("hibernate.current_session_context_class", hibernateCurrentSessionContextClass);

		jpaProperties.put("javax.persistence.validation.mode", javaxPersistenceValidationMode);
		jpaProperties.put("hibernate.query.substitutions", hibernateQuerySubstitutions);
		jpaProperties.put("hibernate.default_batch_fetch_size", hibernateDefaultBatchFetchSize);
		jpaProperties.put("hibernate.max_fetch_depth", hibernateMaxFetchDepth);
		jpaProperties.put("hibernate.enable_lazy_load_no_trans", hibernateEnableLazyLoadNoTrans);
		jpaProperties.put("hibernate.bytecode.use_reflection_optimizer", hibernateBytecodeUseReflectionOptimizer);

		jpaProperties.put("hibernate.cache.use_second_level_cache", hibernateCacheUseSecondLevelCache);
		// jpaProperties.put("hibernate.cache.region.factory_class",
		// hibernateCacheInfinispanRegionFactoryClass);
		// jpaProperties.put("hibernate.cache.infinispan.cfg",
		// hibernateCacheInfinispanCfg);
		jpaProperties.put("javax.persistence.sharedCache.mode", javaxPersistenceSharedCacheMode);
		jpaProperties.put("hibernate.generate_statistics", hibernateGenerateStatistics);

		jpaProperties.put("hibernate.cache.use_query_cache", hibernateCacheUseQueryCache);
		factory.setJpaProperties(jpaProperties);

		return factory;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	public DataSource dataSource() {
		DataSource mainDataSource = mainDataSource();
		if (multipleDataSource) {
			Map<Object, Object> targetDataSources = new HashMap<>();
			targetDataSources.put("main", mainDataSource);

			ThreadLocalDynamicDataSource dynamicDataSource = new ThreadLocalDynamicDataSource();
			dynamicDataSource.setTargetDataSources(targetDataSources);
			dynamicDataSource.setDefaultTargetDataSource(mainDataSource);

			return dynamicDataSource;
		}
		return new LazyConnectionDataSourceProxy(mainDataSource);
	}

	@Bean(destroyMethod = "close")
	public DataSource mainDataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(jdbcDriverClass);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUsername(jdbcUsername);
		dataSource.setPassword(jdbcPassword);
		dataSource.setIdleConnectionTestPeriodInMinutes(2);
		dataSource.setIdleMaxAge(2, TimeUnit.HOURS);
		dataSource.setMaxConnectionsPerPartition(20);
		dataSource.setMinConnectionsPerPartition(2);
		dataSource.setPartitionCount(3);
		dataSource.setAcquireIncrement(3);
		dataSource.setStatementsCacheSize(10);
		return dataSource;
	}

}
