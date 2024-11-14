package com.airtel.buyer.airtelboe.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "erpEntityManagerFactory", transactionManagerRef = "erpTransactionManager", basePackages = {
        "com.airtel.buyer.airtelboe.repository.erp" })
public class ErpDataSourceConfiguration {

    @Bean(name = "erpDataSourceProperties")
    @ConfigurationProperties("spring.datasource-erp")
    public DataSourceProperties erpDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "erpDataSource")
    @ConfigurationProperties("spring.datasource-erp.configuration")
    public DataSource erpDataSource(
            @Qualifier("erpDataSourceProperties") DataSourceProperties erpDataSourceProperties) {
        return erpDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "erpEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean erpEntityManagerFactory(
            EntityManagerFactoryBuilder erpEntityManagerFactoryBuilder,
            @Qualifier("erpDataSource") DataSource erpDataSource) {

        Map<String, String> erpJpaProperties = new HashMap<>();
        erpJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        // secondaryJpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");

        return erpEntityManagerFactoryBuilder
                .dataSource(erpDataSource)
                .packages("com.airtel.buyer.airtelboe.model.erp")
                .persistenceUnit("erpDataSource")
                .properties(erpJpaProperties)
                .build();
    }

    @Bean(name = "erpTransactionManager")
    public PlatformTransactionManager erpTransactionManager(
            @Qualifier("erpEntityManagerFactory") EntityManagerFactory erpEntityManagerFactory) {

        return new JpaTransactionManager(erpEntityManagerFactory);
    }
}
