package com.tirthraj.custom_filters.custom_filters.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<NonSpringSecurityFilter> filter1(){
        System.out.println("Registering NonSpringSecurityFilter for /public");
        FilterRegistrationBean<NonSpringSecurityFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new NonSpringSecurityFilter());
        registrationBean.addUrlPatterns("/public/*");       // Use "/public/*" instead of "/public/**". In Servlet filters, the correct pattern should be "/public/*", not "/public/**" because /** is a Spring Security-style
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
