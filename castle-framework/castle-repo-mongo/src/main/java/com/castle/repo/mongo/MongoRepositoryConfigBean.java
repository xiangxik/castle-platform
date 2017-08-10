package com.castle.repo.mongo;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mapping.model.CamelCaseAbbreviatingFieldNamingStrategy;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.castle.repo.realm.DatabaseResolver;
import com.castle.repo.realm.NullDatabaseResolver;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories(basePackages = { "com.whenling" }, includeFilters = {
		@Filter(value = MongoRepository.class, type = FilterType.ASSIGNABLE_TYPE) }, repositoryImplementationPostfix = "Impl", repositoryFactoryBeanClass = CustomMongoRepositoryFactoryBean.class)
@EnableMongoAuditing
public class MongoRepositoryConfigBean {

	@Value("${mongo.host?:localhost}")
	private String host;

	@Value("${mongo.database?:db}")
	private String database;

	@Value("${mongo.username?:root}")
	private String username;

	@Value("${mongo.password?:Mongodb1234##22}")
	private String password;

	@Autowired(required = false)
	private DatabaseResolver databaseResolver;

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new CustomMongoTemplate(mongoDbFactory(), mappingMongoConverter());
	}

	@Bean
	public MongoMappingContext mongoMappingContext() throws ClassNotFoundException {

		MongoMappingContext mappingContext = new MongoMappingContext();
		mappingContext.setInitialEntitySet(getInitialEntitySet());
		mappingContext.setSimpleTypeHolder(customConversions().getSimpleTypeHolder());
		mappingContext.setFieldNamingStrategy(fieldNamingStrategy());

		return mappingContext;
	}

	@Bean
	public CustomConversions customConversions() {
		return new CustomConversions(Collections.emptyList());
	}

	@Bean
	public MappingMongoConverter mappingMongoConverter() throws Exception {

		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext());
		converter.setCustomConversions(customConversions());

		return converter;
	}

	@Bean
	public MongoDbFactory mongoDbFactory() throws UnknownHostException {
		MongoClient mongoClient = Strings.isNullOrEmpty(username) ? new MongoClient(host)
				: new MongoClient(Lists.newArrayList(new ServerAddress(host)),
						Lists.newArrayList(MongoCredential.createCredential(username, "admin", password.toCharArray())));
		return new SimpleMongoDbFactory(mongoClient, database) {
			@Override
			public DB getDb() throws DataAccessException {
				String databaseName = getDatabaseName();
				return Strings.isNullOrEmpty(databaseName) ? super.getDb() : super.getDb(databaseName);
			}
		};
	}

	private String getDatabaseName() {
		if (databaseResolver == null) {
			databaseResolver = new NullDatabaseResolver();
		}
		return databaseResolver.resolveDatabaseName();
	}

	protected boolean abbreviateFieldNames() {
		return false;
	}

	protected FieldNamingStrategy fieldNamingStrategy() {
		return abbreviateFieldNames() ? new CamelCaseAbbreviatingFieldNamingStrategy() : PropertyNameFieldNamingStrategy.INSTANCE;
	}

	protected Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {

		String basePackage = getMappingBasePackage();
		Set<Class<?>> initialEntitySet = new HashSet<Class<?>>();

		if (StringUtils.hasText(basePackage)) {
			ClassPathScanningCandidateComponentProvider componentProvider = new ClassPathScanningCandidateComponentProvider(false);
			componentProvider.addIncludeFilter(new AnnotationTypeFilter(Document.class));
			componentProvider.addIncludeFilter(new AnnotationTypeFilter(Persistent.class));

			for (BeanDefinition candidate : componentProvider.findCandidateComponents(basePackage)) {
				initialEntitySet.add(ClassUtils.forName(candidate.getBeanClassName(), AbstractMongoConfiguration.class.getClassLoader()));
			}
		}

		return initialEntitySet;
	}

	protected String getMappingBasePackage() {
		return "com.whenling";
	}
}
