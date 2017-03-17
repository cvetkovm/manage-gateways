package org.cvetkov.martin.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.cvetkov.martin.repository.GatewayJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@Configuration
@ComponentScan(basePackages={"org.cvetkov.martin.model","org.cvetkov.martin.repository","org.cvetkov.martin.repository"},
excludeFilters={
		@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)
})
@EnableJpaRepositories(basePackageClasses={GatewayJpaRepository.class})
@EnableTransactionManagement
public class RootConfig {
	@Autowired
	DataSource dataSource;


	@Bean
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL).addScript("db/sql/create-db.sql").addScript("db/sql/insert-data.sql").build();
		return db;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("org.cvetkov.martin.model");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}
}