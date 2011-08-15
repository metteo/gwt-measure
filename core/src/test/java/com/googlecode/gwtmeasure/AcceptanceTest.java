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
import com.googlecode.gwtmeasure.server.event.MeasurementTree;
import com.googlecode.gwtmeasure.server.event.MetricConsumer;
import com.googlecode.gwtmeasure.server.internal.BeanContainer;
import com.googlecode.gwtmeasure.server.spi.MetricsEventHandler;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;
import org.junit.Before;
import org.junit.Test;

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
        BeanContainer container = context.getBeanContainer();
        container.reset();
        MeasureContext.init();

        consumer = mock(MetricConsumer.class);
        container.register(MetricConsumer.class, consumer);
        context.registerEventHandler(AggregatingMetricsHandler.class);

        handler = container.getBean(MetricsEventHandler.class);
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

    @Test
    public void shouldSupportNestedMeasurements() {
        handler.onEvent(createTiming("1", "a", 1, TYPE_BEGIN));
        handler.onEvent(createTiming("1", "b", 2, TYPE_BEGIN));
        handler.onEvent(createTiming("1", "c", 3, TYPE_BEGIN));
        handler.onEvent(createTiming("1", "c", 4, TYPE_END));
        handler.onEvent(createTiming("1", "b", 5, TYPE_END));
        handler.onEvent(createTiming("1", "a", 6, TYPE_END));

        MeasurementTree subChild = prepareResult("1", "c", 1);
        MeasurementTree child = prepareResult("1", "b", 3, subChild);
        verify(consumer).publish(prepareResult("1", "a", 5, child));
    }

    @Test
    public void shouldSupportNestedSiblingMeasurements() {
        handler.onEvent(createTiming("1", "a", 1, TYPE_BEGIN));
        handler.onEvent(createTiming("1", "b", 2, TYPE_BEGIN));
        handler.onEvent(createTiming("1", "b", 3, TYPE_END));
        handler.onEvent(createTiming("1", "c", 4, TYPE_BEGIN));
        handler.onEvent(createTiming("1", "c", 5, TYPE_END));
        handler.onEvent(createTiming("1", "a", 6, TYPE_END));

        MeasurementTree child1 = prepareResult("1", "b", 1);
        MeasurementTree child2 = prepareResult("1", "c", 1);
        verify(consumer).publish(prepareResult("1", "a", 5, child1, child2));
    }

    private MeasurementTree prepareResult(String eventGroup, String subSystem, int time, MeasurementTree... children) {
        MeasurementTree result = prepareResult(eventGroup, subSystem, time);
        for (MeasurementTree child : children) {
            result.addChild(child);
        }
        return result;
    }

    private MeasurementTree prepareResult(String eventGroup, String subSystem, int time) {
        MeasurementTree metric = new MeasurementTree(null, null);
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
