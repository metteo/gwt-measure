/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.gwtmeasure.server.servlet;

import com.googlecode.gwtmeasure.server.MetricsProcessor;
import com.googlecode.gwtmeasure.server.internal.NetworkEventProducer;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class HttpRequestHandlerTest {

    private static HttpRequestHandler handler;
    private static MetricsProcessor processor;
    private static NetworkEventProducer producer;
    private static HttpRequestHandler.ServletClosure closure;
    private static HttpServletRequest request;

    @Before
    public void setUp() {
        processor = mock(MetricsProcessor.class);
        producer = mock(NetworkEventProducer.class);

        handler = new HttpRequestHandler(processor, producer);

        closure = mock(HttpRequestHandler.ServletClosure.class);
        request = mock(HttpServletRequest.class);
    }

    @Test
    public void shouldProcess() throws Exception {
        when(processor.isProcessed(request)).thenReturn(false);

        handler.process(request, closure);

        verify(producer).requestReceived(request);
        verify(processor).extractAndProcess(request);
        verify(processor).markAsProcessed(request);
        verify(closure).execute();
        verify(producer).reponseSent(request);
    }

    @Test
    public void shouldProcess_AlreadyProcessed() throws Exception {
        when(processor.isProcessed(request)).thenReturn(true);

        handler.process(request, closure);

        verify(closure).execute();
        verifyZeroInteractions(producer);
    }

}
