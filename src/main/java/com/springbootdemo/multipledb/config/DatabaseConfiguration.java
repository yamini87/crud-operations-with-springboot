package com.springbootdemo.multipledb.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {
	@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", basePackages = "com.springbootdemo.multipledb.employee.repository", transactionManagerRef = "transactionManager")

	public class EmployeeDatabase {
	

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource dataSource) {
    	Map<String, Object> map = new HashMap<>();
    	map.put("hibernate.hbm2ddl.auto", "update");
    	map.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    	return builder.dataSource(dataSource).properties(map).packages("com.springbootdemo.multipledb.model.employee").persistenceUnit("Employee").build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}  
@EnableJpaRepositories(entityManagerFactoryRef = "managerEntityManagerFactory", basePackages = "com.springbootdemo.multipledb.manager.repository", transactionManagerRef = "managerTransactionManager")
    public class ManagerDatabase {
    	
        @Bean(name = "dataSource1")
        @ConfigurationProperties(prefix = "spring.seconddatasource")
        public DataSource dataSource() {
            return DataSourceBuilder.create().build();
        }
        
        
        @Bean(name = "managerEntityManagerFactory")
        public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource dataSource) {
        	Map<String, Object> map = new HashMap<>();
        	map.put("hibernate.hbm2ddl.auto", "update");
        	map.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        	return builder.dataSource(dataSource).properties(map).packages("com.springbootdemo.multipledb.model.manager").persistenceUnit("Manager").build();
        }
        
        
        @Bean(name = "managerTransactionManager")
        public PlatformTransactionManager transactionManager(@Qualifier("managerEntityManagerFactory")EntityManagerFactory entityManagerFactory) {
        	return new JpaTransactionManager(entityManagerFactory);
        }

    }
}

