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

    private MetricsProcessor metricsProcessor;
    private NetworkEventProducer networkEventProducer;

    public void init(FilterConfig filterConfig) throws ServletException {
        MeasureContext context = MeasureContext.instance();
        metricsProcessor = context.getBean(MetricsProcessor.class);
        networkEventProducer = context.getBean(NetworkEventProducer.class);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (!metricsProcessor.isProcessed(httpRequest)) {
            networkEventProducer.requestReceived(httpRequest);
            metricsProcessor.extractAndProcess(httpRequest);
            metricsProcessor.markAsProcessed(httpRequest);
            chain.doFilter(request, response);
            networkEventProducer.reponseSent(httpRequest);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        MeasureContext.instance().reset();
    }

}
