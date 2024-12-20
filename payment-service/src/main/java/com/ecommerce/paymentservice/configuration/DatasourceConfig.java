package com.ecommerce.paymentservice.configuration;

import com.ecommerce.paymentservice.common.utils.properties.ProjectProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    @Autowired
    private ProjectProperties projectProperties;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(projectProperties.getDatasourceUrl());
        hikariConfig.setUsername(projectProperties.getDatasourceUsername());
        hikariConfig.setPassword(projectProperties.getDatasourcePassword());
        hikariConfig.setDriverClassName(projectProperties.getDriverClassName());

        hikariConfig.setMaximumPoolSize(projectProperties.getMaxPoolSize());
        hikariConfig.setMinimumIdle(projectProperties.getMinIdle());
        hikariConfig.setIdleTimeout(projectProperties.getIdleTimeout());
        hikariConfig.setMaxLifetime(projectProperties.getMaxLifetime());
        hikariConfig.setKeepaliveTime(projectProperties.getKeepaliveTime());
        return new HikariDataSource(hikariConfig);
    }
}
