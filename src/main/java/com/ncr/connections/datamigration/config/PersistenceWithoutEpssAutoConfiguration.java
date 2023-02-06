package com.ncr.connections.datamigration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
@PropertySource({ "classpath:persistence-multiple-db.properties" })
@EnableJpaRepositories(basePackages = "com.ncr.connections.datamigration.car.withoutepss",
        entityManagerFactoryRef = "dataMigrationWithoutEpssEntityManager",
        transactionManagerRef = "connections210xTransactionManager")
public class PersistenceWithoutEpssAutoConfiguration {

    @Autowired
    private Environment env;

    public PersistenceWithoutEpssAutoConfiguration() {
        super();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean dataMigrationWithoutEpssEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(connections210xDataSource());
        em.setPackagesToScan("com.ncr.connections.datamigration.car.withoutepss");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        em.setMappingResources("META-INF/withoutEpss/car_orm.xml");
        return em;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.old.datasource")
    public DataSource connections210xDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public PlatformTransactionManager connections210xTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(dataMigrationWithoutEpssEntityManager().getObject());
        return transactionManager;
    }

}
