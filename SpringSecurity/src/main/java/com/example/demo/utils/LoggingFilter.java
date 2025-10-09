package com.example.demo.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingFilter extends OncePerRequestFilter{
	private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("Incoming Request: {} {}", request.getMethod(), request.getRequestURI());

        request.getHeaderNames().asIterator()
               .forEachRemaining(header -> logger.info("Header: {} = {}", header, request.getHeader(header)));

       
        filterChain.doFilter(request, response);


        logger.info("Outgoing Response: {} {}", response.getStatus(), request.getRequestURI());
		
	}
}
