package com.ecommerce.orderservice.common.utils.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Data
@Order(1)
@Component
public class ProjectProperties {

    /*
     * REST Configuration Properties
     */
    @Value("${app.config.rest.connect-timeout}")
    private int connectionTimeout;

    @Value("${app.config.rest.connection-request-timeout}")
    private int connectionRequestTimeout;

    @Value("${app.config.rest.socket-timeout}")
    private int socketTimeout;

    @Value("${app.config.rest.max-conn-total}")
    private int maxConnTotal;

    @Value("${app.config.rest.max-conn-per-route}")
    private int maxConnPerRoute;

    /*
     * Integration Service URLs
     */
    @Value("${integration.product-service.url}")
    private String productServiceUrl;

    @Value("${integration.payment-service.url}")
    private String paymentServiceUrl;

    /*
     * Database Configuration Properties
     */
    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maxPoolSize;

    @Value("${spring.datasource.hikari.minimum-idle}")
    private int minIdle;

    @Value("${spring.datasource.hikari.idle-timeout}")
    private long idleTimeout;

    @Value("${spring.datasource.hikari.max-lifetime}")
    private long maxLifetime;

    @Value("${spring.datasource.hikari.keepalive-time}")
    private long keepaliveTime;

}
