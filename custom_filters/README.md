Below is an example of a comprehensive README file that walks you through Java servlet filters and Spring Security filters using the examples from our tutorial. You can save this as **README.md** in your project.

---

# Spring Boot Filters Tutorial

This tutorial covers the fundamentals of servlet filters and Spring Security filters. It explains when and how filters are invoked, the role of `OncePerRequestFilter`, the usage of `filterChain.doFilter()`, and how to add filters both with and without Spring Security. We also discuss route-specific filters, post-processing responses, and the differences between Spring Security filters and non-Spring Security filters.
---

## 1. What Are Filters?

Filters are components that **intercept HTTP requests and responses** in a Java web application. They allow you to perform tasks such as:
- Logging requests
- Adding or modifying HTTP headers
- Enforcing rate limits
- Performing authentication and authorization checks

Filters are part of the **Servlet API** and can be used in any Java web application, not just those that use Spring Security.

---

## 2. When Are Filters Invoked?

Filters are invoked **before and after** a request reaches a servlet or controller:
- **Pre-processing:** Code before the request is handled (e.g., logging, authentication).
- **Post-processing:** Code that executes after the controller returns a response (e.g., modifying headers).

The order in which filters execute is determined by their registration and, in Spring Security, by the filter chain order.

---

## 3. What Is `OncePerRequestFilter`?

`OncePerRequestFilter` is a Spring-provided abstract class that guarantees a filter is **executed only once per request**. This is particularly useful in Spring Security to avoid executing the same filter multiple times during request dispatching (e.g., during forwards or includes).

Example of Filter

```java
package com.tirthraj.custom_filters.custom_filters.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    public static final Logger logger = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Incoming Request: " + request.getMethod() + " " + request.getRequestURI());
        filterChain.doFilter(request, response);
    }
}

```

---

## 4. Understanding `filterChain.doFilter()`

Inside a filter, the method `filterChain.doFilter(request, response)`:
- **Passes control** to the next filter in the chain or eventually to the target servlet/controller.
- Ensures that the same **`HttpServletRequest` and `HttpServletResponse` objects** are passed along the chain.
- Allows you to perform **post-processing** after the controller has generated the response by placing code **after** the `doFilter()` call.

---

## 5. Request and Response in the Filter Chain

- **Request:** Filters can add attributes (using `setAttribute()`) to the `HttpServletRequest` object. These attributes are used for inter-filter communication and are **not sent** to the client.
- **Response:** Filters can add headers to the `HttpServletResponse` object. Modifications made to the response (like headers) will be visible to the client.

---

## 6. Adding Filters: With and Without Spring Security

### **With Spring Security**
Spring Security maintains its own filter chain for processing security-related tasks. You can add filters using methods such as:
- `addFilterBefore(filter, referenceFilter.class)`
- `addFilterAfter(filter, referenceFilter.class)`
- `addFilterAt(filter, referenceFilter.class)`

These methods allow you to precisely control where your custom filter fits into the Spring Security chain. For example, if you want to run a custom header extractor after a header filter:

```java
httpSecurity.addFilterBefore(customHeaderFilter, UsernamePasswordAuthenticationFilter.class);
httpSecurity.addFilterAfter(customHeaderExtractorFilter, CustomHeaderFilter.class);
```

### **Without Spring Security**
If you need a filter outside of the Spring Security chain (e.g., global servlet filters), you can register them using:
- The `@Component` annotation (automatic registration), or
- A `FilterRegistrationBean` for more control over URL patterns and order.

Example using `FilterRegistrationBean`:
```java
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<NonSpringSecurityFilter> filter1() {
        FilterRegistrationBean<NonSpringSecurityFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new NonSpringSecurityFilter());
        // Use Servlet-style URL pattern (e.g., "/public/*")
        registrationBean.addUrlPatterns("/public/*");
        registrationBean.setOrder(1);  // Lower order means higher priority
        return registrationBean;
    }
}
```

---

## 7. Default Filters and Their Order in Spring Security

Spring Security registers a chain of filters by default. Some key filters (in approximate order) include:
- **SecurityContextPersistenceFilter:** Manages the security context.
- **UsernamePasswordAuthenticationFilter:** Handles form login authentication.
- **BasicAuthenticationFilter:** Processes HTTP Basic authentication.
- **BearerTokenAuthenticationFilter:** Deals with token-based authentication.
- **FilterSecurityInterceptor:** Enforces authorization.

Understanding this order is crucial when inserting your custom filters relative to these defaults.

---

## 8. Adding Route-Specific Filters

Sometimes you need a filter to run only on specific URL patterns. There are two common approaches:

### **Method 1: FilterRegistrationBean with URL Patterns**
Use `FilterRegistrationBean` to restrict a filter to certain URL patterns:
```java
registrationBean.addUrlPatterns("/public/*");
```

### **Method 2: Checking the Request Path in the Filter**
Inside the filter, conditionally execute logic based on the request URL:
```java
if (request.getRequestURI().startsWith("/admin/")) {
    // Execute filter logic for /admin routes
}
```

#### **Example: Two Filters on the Same Route**
- **Filter1** logs `"This is Filter1"` and applies to `/api/private/*` and `/api/common/*`.
- **Filter2** logs `"This is Filter2"` and applies only to `/api/private/*`.

Registration using `FilterRegistrationBean`:
```java
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<Filter1> filter1() {
        FilterRegistrationBean<Filter1> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new Filter1());
        registrationBean.addUrlPatterns("/api/private/*", "/api/common/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<Filter2> filter2() {
        FilterRegistrationBean<Filter2> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new Filter2());
        registrationBean.addUrlPatterns("/api/private/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
```

---

## 9. Post-Processing the Response

Filters can process the response **after** the controller has generated it. This is done by placing logic **after** the `filterChain.doFilter(request, response)` call. For example:

```java
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
    // Pre-processing logic here
    filterChain.doFilter(request, response);  // Passes control to the next filter/controller
    // Post-processing logic here
    response.addHeader("X-Post-Controller", "Executed after controller");
}
```

Alternatively, you can use a **HandlerInterceptor** (for Spring MVC) if you need to work at the controller level.

---

## 10. Spring Security Filters vs. Non-Spring Security Filters

| **Aspect**              | **Spring Security Filters**                                   | **Non-Spring Security Filters**                        |
|-------------------------|---------------------------------------------------------------|--------------------------------------------------------|
| **Registration**        | Added via `httpSecurity.addFilter*()`                         | Registered via `@Component` or `FilterRegistrationBean`|
| **Execution Context**   | Run inside the Spring Security filter chain                   | Run as standard servlet filters (global or route-specific) |
| **Order & Control**     | Positioned relative to security filters (e.g., BasicAuthenticationFilter) | Order controlled by `FilterRegistrationBean.setOrder()`|
| **Typical Use Cases**   | Authentication, authorization, CSRF protection, etc.          | Logging, adding headers, rate limiting, request modification |

---

## 11. Conclusion and Further References

In this tutorial, we've seen how to:
- Use and configure filters with and without Spring Security.
- Understand the flow and importance of `filterChain.doFilter()`.
- Add route-specific filters.
- Perform both pre- and post-processing on the request/response.
- Distinguish between Spring Security filters and non-Spring Security filters.

### **Further Reading**
- [Spring Security Reference Documentation](https://docs.spring.io/spring-security/site/docs/current/reference/html5/)
- [Spring Boot Servlet Filters](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-add-servlet-filters)
- [Baeldung’s Guide to Spring Security Filters](https://www.baeldung.com/spring-security-filters)

---

Feel free to extend this tutorial with more examples and advanced use cases like custom authentication filters or JWT processing. Happy coding!