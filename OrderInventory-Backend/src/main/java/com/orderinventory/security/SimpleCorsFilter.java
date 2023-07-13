package com.orderinventory.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

 

@Component

@Order(Ordered.HIGHEST_PRECEDENCE)

public class SimpleCorsFilter implements Filter {

    private String clientAppUrl = "http://localhost:4200/api/auth/login";

    public SimpleCorsFilter() {

    } 

    @Override

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;

        HttpServletRequest request = (HttpServletRequest) req;

        Map<String, String> map = new HashMap<>();

        String originHeader = request.getHeader("origin");

//        if (originHeader.equalsIgnoreCase(clientAppUrl))

        response.setHeader("Access-Control-Allow-Origin", originHeader);

 

//        response.setHeader("Access-Control-Allow-Methods", "*");

        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

        response.setHeader("Access-Control-Max-Age", "3600");

        response.setHeader("Access-Control-Allow-Headers", "*");

//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization");

 

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {

            response.setStatus(HttpServletResponse.SC_OK);

        } else {

            chain.doFilter(req, res);

        }

    }

    public void init(FilterConfig filterConfig) {

    }
    public void destroy() {

    }

}

 
