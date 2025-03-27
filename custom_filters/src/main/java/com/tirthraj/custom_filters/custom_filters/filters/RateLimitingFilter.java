package com.tirthraj.custom_filters.custom_filters.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {
    private static final int MAX_REQUESTS = 5;
    private static final long TIME_WINDOW = 60000;  // 60 secs
    private final Map<String, List<Long>> requestTimestamps = new ConcurrentHashMap<>();

    private static final Logger logger = Logger.getLogger(RateLimitingFilter.class.getName());


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getName() == null){
            logger.info("No rate limiting as request for non-authenticated route");
            filterChain.doFilter(request, response);
            return;
        }


        String username = authentication.getName();
        logger.info("Rate Limiter: Username : " + username);

        long currentTime = Instant.now().toEpochMilli();
        requestTimestamps.putIfAbsent(username, new CopyOnWriteArrayList<>());      // It is a thread-safe version of ArrayList.

        List<Long> timestamps = requestTimestamps.get(username);

        synchronized (timestamps){
            timestamps.removeIf(time -> (currentTime - time) > TIME_WINDOW);

            if(timestamps.size() >= MAX_REQUESTS){
                logger.info("Rate Limit exceeded for username : " + username);
                response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Rate limit exceeded. Try Again");
                return;
            }

            timestamps.add(currentTime);
        }

        filterChain.doFilter(request, response);
    }
}
