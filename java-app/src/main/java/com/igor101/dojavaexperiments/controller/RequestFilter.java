package com.igor101.dojavaexperiments.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

@Component
public class RequestFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        log.info("Intercepting http request from {}...", servletRequest.getRemoteAddr());
        if (servletRequest instanceof HttpServletRequest req) {
            log.info("Headers:");
            var headers = Collections.list(req.getHeaderNames());
            headers.forEach(h -> log.info("{} - {}", h, Collections.list(req.getHeaders(h))));
            log.info("");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
