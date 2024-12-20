package com.ecommerce.productservice.configuration;

import com.ecommerce.productservice.common.utils.log.Slf4jMDCFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Slf4jMDCFilterConfig {
    @Bean
    public FilterRegistrationBean<Slf4jMDCFilter> servletRegistrationBean() {
        final FilterRegistrationBean<Slf4jMDCFilter> registrationBean = new FilterRegistrationBean<>();
        final Slf4jMDCFilter filter = new Slf4jMDCFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
