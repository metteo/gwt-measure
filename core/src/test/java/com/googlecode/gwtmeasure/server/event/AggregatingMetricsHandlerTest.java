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

package com.googlecode.gwtmeasure.server.event;

import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class AggregatingMetricsHandlerTest extends Assert {

    private AggregatingMetricsHandler handler;
    private EventCache cache;
    private MetricConsumer consumer;

    @Before
    public void setUp() throws Exception {
        cache = mock(EventCache.class);
        consumer = mock(MetricConsumer.class);
        handler = new AggregatingMetricsHandler(cache, consumer);
    }

    @Test
    public void testOnEvent() throws Exception {
        PerformanceTiming timing = mock(PerformanceTiming.class);

        when(cache.findMatch(timing)).thenReturn(null);

        handler.onEvent(timing);

        verify(cache).findMatch(timing);
        verify(cache).append(timing);
    }

    @Test
    public void testOnEvent_Match() throws Exception {
        PerformanceTiming timing = createMetric("A", Constants.SUB_SYSTEM_RPC, "0", Constants.TYPE_START, 1);
        PerformanceTiming match = createMetric("A", Constants.SUB_SYSTEM_RPC, "0", Constants.TYPE_END, 3);

        when(cache.findMatch(timing)).thenReturn(match);

        handler.onEvent(timing);

        PerformanceMetric expectedMetric = new PerformanceMetric();
        expectedMetric.setEventGroup("0");
        expectedMetric.setModuleName("A");
        expectedMetric.setSubSystem(Constants.SUB_SYSTEM_RPC);
        expectedMetric.setTime(2);

        verify(cache).findMatch(timing);
        verify(consumer).publish(expectedMetric);
    }

    private PerformanceTiming createMetric(String module, String subSystem, String group, String type, long millis) {
        PerformanceTiming timing = mock(PerformanceTiming.class);

        when(timing.getModuleName()).thenReturn(module);
        when(timing.getSubSystem()).thenReturn(subSystem);
        when(timing.getEventGroup()).thenReturn(group);
        when(timing.getType()).thenReturn(type);
        when(timing.getMillis()).thenReturn(millis);

        return timing;
    }

}
