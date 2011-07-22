package com.googlecode.gwtmeasure.server.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author dmitry.buzdin
 */
public final class JsInjectorFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        ServletResponseWrapper wrapper = new ServletResponseWrapper(response);
        chain.doFilter(request, wrapper);

        wrapper.flushBuffer();
    }

    public void destroy() {
    }

}
