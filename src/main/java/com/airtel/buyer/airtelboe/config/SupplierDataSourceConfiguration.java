package com.airtel.buyer.airtelboe.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "supplierEntityManagerFactory", transactionManagerRef = "supplierTransactionManager", basePackages = {
        "com.airtel.buyer.airtelboe.repository.supplier" })
public class SupplierDataSourceConfiguration {

    @Primary
    @Bean(name = "supplierDataSourceProperties")
    @ConfigurationProperties("spring.datasource-supplier")
    public DataSourceProperties supplierDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "supplierDataSource")
    @ConfigurationProperties("spring.datasource-supplier.configuration")
    public DataSource supplierDataSource(
            @Qualifier("supplierDataSourceProperties") DataSourceProperties supplierDataSourceProperties) {
        return supplierDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "supplierEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean supplierEntityManagerFactory(
            EntityManagerFactoryBuilder supplierEntityManagerFactoryBuilder,
            @Qualifier("supplierDataSource") DataSource supplierDataSource) {

        Map<String, String> supplierJpaProperties = new HashMap<>();
        supplierJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        // primaryJpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");

        return supplierEntityManagerFactoryBuilder
                .dataSource(supplierDataSource)
                .packages("com.airtel.buyer.airtelboe.model.supplier")
                .persistenceUnit("supplierDataSource")
                .properties(supplierJpaProperties)
                .build();
    }

    @Primary
    @Bean(name = "supplierTransactionManager")
    public PlatformTransactionManager supplierTransactionManager(
            @Qualifier("supplierEntityManagerFactory") EntityManagerFactory supplierEntityManagerFactory) {

        return new JpaTransactionManager(supplierEntityManagerFactory);
    }
}
