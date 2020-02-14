package com.example.demo;

import java.util.Date;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.entity.MessageData;
import com.example.demo.repository.MessageDataRepository;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableTransactionManagement
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUsername("user");
		dataSource.setPassword("pass");
		dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "com.example.demo" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());

		return em;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("spring.datasource.initialization-mode","always");
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("spring.jpa.hibernate.ddl-auto", "none");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

		return properties;
	}

	@Bean
	public CommandLineRunner demo(MessageDataRepository repository) {
		return (args) -> {
			// save a few
			repository.save(new MessageData(new byte[10], new Date(), new Date()));
			repository.save(new MessageData(new byte[10], new Date(), new Date()));

			// fetch all
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (MessageData messageData : repository.findAll()) {
				log.info(messageData.toString());
			}
			log.info("");

			// fetch an individual by ID
			MessageData customer = repository.findById(1L);
			log.info("Customer found with findById(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
