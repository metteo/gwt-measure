package com.googlecode.gwtmeasure.server.servlet;

import com.googlecode.gwtmeasure.server.MeasureContext;
import com.googlecode.gwtmeasure.server.MetricsProcessor;
import com.googlecode.gwtmeasure.server.internal.NetworkEventProducer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dmitry.buzdin
 */
public class MeasureFilter implements Filter {

    private HttpRequestHandler handler;

    public void init(FilterConfig filterConfig) throws ServletException {
        MeasureContext context = MeasureContext.instance();
        handler = context.getBean(HttpRequestHandler.class);
    }

    public void doFilter(final ServletRequest request, final ServletResponse response,
                            final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        handler.process(httpRequest, new HttpRequestHandler.ServletClosure() {
            public void execute() throws IOException, ServletException {
                chain.doFilter(request, response);
            }
        });
    }

    public void destroy() {
        MeasureContext.instance().reset();
    }

}
