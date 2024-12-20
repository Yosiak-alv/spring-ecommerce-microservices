package com.ecommerce.orderservice.configuration;

import com.ecommerce.orderservice.common.utils.properties.ProjectProperties;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private ProjectProperties projectProperties;

    @Primary
    @Bean("restTemplate")
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder()
                .requestFactory(this::requestFactory)
                .build();
    }

    private HttpComponentsClientHttpRequestFactory requestFactory() {
        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(projectProperties.getConnectionRequestTimeout()))
                .setResponseTimeout(Timeout.ofMilliseconds(projectProperties.getSocketTimeout()))
                .setConnectTimeout(Timeout.ofMilliseconds((projectProperties.getConnectionTimeout())))
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(projectProperties.getMaxConnTotal());
        connectionManager.setDefaultMaxPerRoute(projectProperties.getMaxConnPerRoute());

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
}
