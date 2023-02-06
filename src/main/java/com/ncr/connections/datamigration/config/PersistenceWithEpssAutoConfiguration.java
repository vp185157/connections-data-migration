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
@EnableJpaRepositories(basePackages = "com.ncr.connections.datamigration.car.withepss", entityManagerFactoryRef =
        "dataMigrationWithEpssEntityManager",
        transactionManagerRef = "connections3xTransactionManager")
public class PersistenceWithEpssAutoConfiguration {

    @Autowired
    private Environment env;

    public PersistenceWithEpssAutoConfiguration() {
        super();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean dataMigrationWithEpssEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(connections3xDataSource());
        em.setPackagesToScan("com.ncr.connections.datamigration.car.withepss");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        em.setMappingResources("META-INF/withEpss/car_orm.xml");
        return em;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.new.datasource")
    public DataSource connections3xDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public PlatformTransactionManager connections3xTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(dataMigrationWithEpssEntityManager().getObject());
        return transactionManager;
    }
}
