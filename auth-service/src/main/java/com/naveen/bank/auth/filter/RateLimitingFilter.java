package com.naveen.bank.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(5)
public class RateLimitingFilter extends OncePerRequestFilter {

    private final Map<String, Integer> requestCount = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();

        int count = requestCount.getOrDefault(ip, 0);

        if (count >= 100) {

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());

            response.getWriter().write("Too Many Requests");

            return;
        }

        requestCount.put(ip, count + 1);

        filterChain.doFilter(request, response);
    }
}