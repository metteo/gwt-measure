package com.google.code.gwtmeasure.server;

import com.google.code.gwtmeasure.shared.Constants;
import com.google.code.gwtmeasure.shared.PerformanceMetrics;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author dmitry.buzdin
 */
public class MeasureFilter implements Filter {

    private MetricsProcessor metricsProcessor;

    public void init(FilterConfig filterConfig) throws ServletException {
        metricsProcessor = new MetricsProcessor();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            metricsProcessor.extractAndProcess((HttpServletRequest) request);
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
    }

}
