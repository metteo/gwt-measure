package com.google.code.gwtmeasure.server;

import com.google.code.gwtmeasure.shared.Constants;

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
                System.out.println("Measurement result : " + result);
            }
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
    }

}
