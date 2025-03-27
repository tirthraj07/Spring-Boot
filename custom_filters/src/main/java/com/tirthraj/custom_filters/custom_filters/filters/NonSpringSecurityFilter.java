package com.tirthraj.custom_filters.custom_filters.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

public class NonSpringSecurityFilter extends OncePerRequestFilter {
    private static final Logger logger = Logger.getLogger(NonSpringSecurityFilter.class.getName());


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("This filter is NonSpringSecurityFilter and only applies to /public route");
        filterChain.doFilter(request, response);
    }
}
