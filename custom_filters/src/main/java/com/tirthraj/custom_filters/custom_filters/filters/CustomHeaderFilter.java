package com.tirthraj.custom_filters.custom_filters.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class CustomHeaderFilter extends OncePerRequestFilter {
    private static final Logger logger = Logger.getLogger(CustomHeaderFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // adding header to the request object (will not be sent to the client)
        String decodedToken = "user123";
        request.setAttribute("x-decodedToken",decodedToken);

        logger.info("Added x-decodedToken in request object");

        // adding header to response Object (will be sent to the client)
        response.setHeader("x-customHeader","XYZ");

        logger.info("Added x-customHeader in response object");

        // Go forward to the next filter

        filterChain.doFilter(request, response);

        // The next code will run when it returns from the next filter

        String newDecodedToken = (String) request.getAttribute("x-decodedToken");

        if(newDecodedToken != null)
            logger.info("Decoded Token back from CustomHeaderExtractorFilter: " + newDecodedToken);
        else
            logger.info("Unable to extract x-decodedToken from CustomHeaderExtractorFilter");
    }
}
