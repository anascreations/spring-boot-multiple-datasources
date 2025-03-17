package com.multiple.datasource.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
	private static final String PRIMARY_ENTITY_PACKAGE = "com.multiple.datasource.entity.primary";
	private static final String SECONDARY_ENTITY_PACKAGE = "com.multiple.datasource.entity.secondary";
	private static final String PRIMARY_REPOSITORY_PACKAGE = "com.multiple.datasource.repository.primary";
	private static final String SECONDARY_REPOSITORY_PACKAGE = "com.multiple.datasource.repository.secondary";

	@Configuration
	@EnableJpaRepositories(basePackages = PRIMARY_REPOSITORY_PACKAGE, entityManagerFactoryRef = "primaryEmf", transactionManagerRef = "primaryTm")
	public static class PrimaryDataSourceConfig {
		@Bean
		@Primary
		@ConfigurationProperties("spring.datasource.primary")
		DataSourceProperties primaryDataSourceProperties() {
			return new DataSourceProperties();
		}

		@Bean
		@Primary
		@ConfigurationProperties("spring.datasource.primary.hikari")
		HikariDataSource primaryDataSource() {
			return primaryDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
		}

		@Primary
		@Bean
		LocalContainerEntityManagerFactoryBean primaryEmf(EntityManagerFactoryBuilder builder,
				@Value("${spring.datasource.primary.schema}") String defaultSchema) {
			Map<String, String> properties = jpaProperties(defaultSchema);
			return builder.dataSource(primaryDataSource()).properties(properties).packages(PRIMARY_ENTITY_PACKAGE)
					.persistenceUnit("primary").build();
		}

		@Primary
		@Bean
		PlatformTransactionManager primaryTm(@Qualifier("primaryEmf") EntityManagerFactory emf) {
			return new JpaTransactionManager(emf);
		}
	}

	@Configuration
	@EnableJpaRepositories(basePackages = SECONDARY_REPOSITORY_PACKAGE, entityManagerFactoryRef = "secondaryEmf", transactionManagerRef = "secondaryTm")
	public static class SecondaryDataSourceConfig {
		@Bean
		@ConfigurationProperties("spring.datasource.secondary")
		DataSourceProperties secondaryDataSourceProperties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties("spring.datasource.secondary.hikari")
		HikariDataSource secondaryDataSource() {
			return secondaryDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
		}

		@Bean
		LocalContainerEntityManagerFactoryBean secondaryEmf(EntityManagerFactoryBuilder builder,
				@Value("${spring.datasource.secondary.schema}") String defaultSchema) {
			Map<String, String> properties = jpaProperties(defaultSchema);
			properties.put("hibernate.dialect", "org.hibernate.community.dialect.OracleLegacyDialect");
			return builder.dataSource(secondaryDataSource()).properties(properties).packages(SECONDARY_ENTITY_PACKAGE)
					.persistenceUnit("secondary").build();
		}

		@Bean
		PlatformTransactionManager secondaryTm(@Qualifier("secondaryEmf") EntityManagerFactory emf) {
			return new JpaTransactionManager(emf);
		}
	}

	private static Map<String, String> jpaProperties(String defaultSchema) {
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.physical_naming_strategy",
				"org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
		properties.put("hibernate.default_schema", defaultSchema);
		properties.put("hibernate.id.new_generator_mappings", "true");
		properties.put("hibernate.event.merge.entity_copy_observer", "allow");
		properties.put("hibernate.enable_lazy_load_no_trans", "true");
		properties.put("hibernate.order_by.default_null_ordering", "last");
		return properties;
	}
}
