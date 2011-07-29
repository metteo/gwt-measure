package com.googlecode.gwtmeasure.server;

import com.googlecode.gwtmeasure.server.servlet.MeasureFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

/**
 * @author dmitry.buzdin
 */
public class MeasureFilterTest extends Assert {

    private MeasureFilter filter;
    private FilterChain chain;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private HttpSession session;

    @Before
    public void setUp() throws Exception {
        filter = new MeasureFilter();
        filter.init(mock(FilterConfig.class));

        chain = mock(FilterChain.class);
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);

        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void shouldNotFailWithEmptyHeader() throws Exception {
        filter.doFilter(request, response, chain);
        
        verify(chain).doFilter(request, response);
    }

}
