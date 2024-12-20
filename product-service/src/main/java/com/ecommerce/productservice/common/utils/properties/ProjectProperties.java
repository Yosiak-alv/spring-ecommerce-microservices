package com.ecommerce.productservice.common.utils.properties;

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
    @Value("${integration.fake-store-api.url}")
    private String fakeStoreApiUrl;
}
