/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.gwtmeasure.server.internal;

import com.googlecode.gwtmeasure.server.spi.MetricsEventHandler;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class CompositeMetricsEventHandlerTest {

    private CompositeMetricsEventHandler handler;

    @Before
    public void setUp() {
        handler = new CompositeMetricsEventHandler();
    }

    @Test
    public void testOnEvent() throws Exception {
        MetricsEventHandler eventHandler = mock(MetricsEventHandler.class);
        handler.addHandler(eventHandler);
        PerformanceTiming timing = mock(PerformanceTiming.class);

        handler.onEvent(timing);
        
        verify(eventHandler).onEvent(timing);
    }

    @Test
    public void testGetHandlers() throws Exception {
        List<MetricsEventHandler> handlers = handler.getHandlers();

        assertThat(handlers.size(), equalTo(0));
    }

}
