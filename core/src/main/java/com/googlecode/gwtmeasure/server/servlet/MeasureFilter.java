package com.googlecode.gwtmeasure.server.servlet;

import com.googlecode.gwtmeasure.server.MeasureContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author dmitry.buzdin
 */
public final class MeasureFilter implements Filter {

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
