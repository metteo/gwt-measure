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

package com.googlecode.gwtmeasure.server;

import com.googlecode.gwtmeasure.server.spi.MetricsEventHandler;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MetricsProcessorTest extends Assert {

    private MetricsProcessor processor;
    private JsonDecoder decoder;
    private MetricsEventHandler handler;
    private HttpServletRequest request;

    @Before
    public void setUp() {
        decoder = mock(JsonDecoder.class);
        handler = mock(MetricsEventHandler.class);
        request = mock(HttpServletRequest.class);

        processor = new MetricsProcessor(decoder, handler, null);
    }

    @Test
    public void shouldBeOkWhenNoHeaders() {
        processor.extractAndProcess(request);
    }

    @Test
    public void shouldExtractFromMultipleHeaders() {
        when(request.getHeader(Constants.HEADER_RESULT)).thenReturn("1");

        PerformanceTiming timing = new PerformanceTiming();

        when(decoder.decodeTimings("1")).thenReturn(Collections.singletonList(timing));

        processor.extractAndProcess(request);

        verify(handler, times(1)).onEvent(any(PerformanceTiming.class));
    }

}
