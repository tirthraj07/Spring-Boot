package com.tirthraj.custom_filters.custom_filters.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class CustomHeaderExtractorFilter extends OncePerRequestFilter {
    private final Logger logger = Logger.getLogger(CustomHeaderExtractorFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String decodedToken = (String) request.getAttribute("x-decodedToken");
        if(decodedToken == null){
            logger.info("Unable to process token from previous filter");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unable to process token from previous filter");
            return;
        }

        logger.info("DecodedToken " + decodedToken);

        filterChain.doFilter(request, response);

        logger.info("Changing decoded token");
        request.setAttribute("x-decodedToken","user321");
    }
}
