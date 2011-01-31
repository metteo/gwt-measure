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

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String result = httpRequest.getHeader(Constants.HEADER_RESULT);
            if (null != result) {
                handleMetrics(result);
            }
        }

        chain.doFilter(request, response);
    }

    private void handleMetrics(String result) {
        String[] metrics = result.split("\\@");
        for (String metric : metrics) {
            PerformanceMetrics performanceMetrics = PerformanceMetrics.decode(metric);
            System.out.println(performanceMetrics);
        }
    }

    public void destroy() {
    }

}
