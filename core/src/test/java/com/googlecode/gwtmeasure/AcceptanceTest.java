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

package com.googlecode.gwtmeasure;

import com.googlecode.gwtmeasure.server.MeasureContext;
import com.googlecode.gwtmeasure.server.event.AggregatingMetricsHandler;
import com.googlecode.gwtmeasure.server.event.MetricConsumer;
import com.googlecode.gwtmeasure.server.event.PerformanceMetric;
import com.googlecode.gwtmeasure.server.event.RawEventStorage;
import com.googlecode.gwtmeasure.server.spi.MetricsEventHandler;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static com.googlecode.gwtmeasure.shared.Constants.*;
import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class AcceptanceTest {

    MetricsEventHandler handler;
    MetricConsumer consumer;

    @Before
    public void setUp() {
        MeasureContext context = MeasureContext.instance();
        context.reset();
        MeasureContext.init();

        consumer = mock(MetricConsumer.class);
        context.register(MetricConsumer.class, consumer);
        context.registerEventHandler(AggregatingMetricsHandler.class);

        handler = context.getBean(MetricsEventHandler.class);
    }

    @Test
    public void shouldFindSimpleMatch() {
        handler.onEvent(createTiming("1", "a", 1, TYPE_BEGIN));
        handler.onEvent(createTiming("1", "a", 3, TYPE_END));

        verify(consumer).publish(prepareResult("1", "a", 2));
    }

    @Test
    public void shouldFindByBeginEnd() {
        handler.onEvent(createTiming("1", "a", 1, TYPE_BEGIN));
        handler.onEvent(createTiming("1", "a", 2, TYPE_REQUEST_SENT));
        handler.onEvent(createTiming("1", "a", 3, TYPE_END));

        verify(consumer).publish(prepareResult("1", "a", 2));
    }

    private PerformanceMetric prepareResult(String eventGroup, String subSystem, int time) {
        PerformanceMetric metric = new PerformanceMetric();
        metric.setEventGroup(eventGroup);
        metric.setSubSystem(subSystem);
        metric.setTime(time);
        metric.setModuleName("");
        return metric;
    }

    private PerformanceTiming createTiming(String eventGroup, String subSystem, int millis, String type) {
        PerformanceTiming timing = new PerformanceTiming();
        timing.setSubSystem(subSystem);
        timing.setEventGroup(eventGroup);
        timing.setMillis(millis);
        timing.setType(type);
        return timing;
    }

}
