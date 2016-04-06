package com.itechart.kalenik.config;


import com.itechart.kalenik.utils.Const;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages= {"com.itechart.kalenik.dao.repository"})
public class PersistenceJPAConfig {

    private static Logger logger = LoggerFactory.getLogger(PersistenceJPAConfig.class);

    @Autowired
    private Environment env;

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        logger.debug("transaction configuring");
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        logger.debug("hibernate configuring");
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        factory.setPackagesToScan(Const.ENTITIES_DIRECTORY);
        factory.setDataSource(dataSource());

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factory.setJpaVendorAdapter(vendorAdapter);

        logger.debug("env: {}",env);
        Properties properties = new Properties();
        properties.setProperty(Const.HIBERNATE_DIALECT, env.getProperty(Const.HIBERNATE_DIALECT));
        properties.setProperty(Const.HIBERNATE_JDBC_CONNECTION_POOL_SIZE, env.getProperty(Const.HIBERNATE_JDBC_CONNECTION_POOL_SIZE));
        properties.setProperty(Const.HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS, env.getProperty(Const.HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS));
        properties.setProperty(Const.HIBERNATE_CACHE_PROVIDER_CLASS, env.getProperty(Const.HIBERNATE_CACHE_PROVIDER_CLASS));
        properties.setProperty(Const.HIBERNATE_SHOW_SQL, env.getProperty(Const.HIBERNATE_SHOW_SQL));
        properties.setProperty(Const.HIBERNATE_HBM2DDL_AUTO, env.getProperty(Const.HIBERNATE_HBM2DDL_AUTO));
        factory.setJpaProperties(properties);

        return factory;
    }

    @Bean
    public DataSource dataSource() {
        logger.debug("JDBC datasource configuring");
        BasicDataSource dataSource = new BasicDataSource();

logger.debug("env variables: driver: {}, url: {}, username: {}, password: {}",env.getProperty(Const.JDBC_DRIVER),env.getProperty(Const.JDBC_URL),env.getProperty(Const.JDBC_USERNAME),env.getProperty(Const.JDBC_PASSWORD));
        dataSource.setDriverClassName(env.getProperty(Const.JDBC_DRIVER));
        dataSource.setUrl(env.getProperty(Const.JDBC_URL));
        dataSource.setUsername(env.getProperty(Const.JDBC_USERNAME));
        dataSource.setPassword(env.getProperty(Const.JDBC_PASSWORD));

        return dataSource;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}

