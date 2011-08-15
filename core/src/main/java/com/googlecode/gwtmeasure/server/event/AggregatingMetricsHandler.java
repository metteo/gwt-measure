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

import com.googlecode.gwtmeasure.server.spi.MetricsEventHandler;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.List;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class AggregatingMetricsHandler implements MetricsEventHandler {

    private RawEventStorage cache;
    private MetricConsumer consumer;
    private PatternMatcher matcher;

    public AggregatingMetricsHandler(RawEventStorage cache, MetricConsumer consumer, PatternMatcher matcher) {
        this.cache = cache;
        this.consumer = consumer;
        this.matcher = matcher;
    }

    public void onEvent(PerformanceTiming timing) {
        if (timing.isEndEvent()) {
            List<PerformanceTiming> timingStack = cache.findMatch(timing.getModuleName(), timing.getEventGroup());
            if (timingStack.isEmpty()) {
                cache.put(timing);
            } else if (matcher.isClosed(timingStack)) {
                cache.remove(timingStack);
                timingStack.add(timing);
                MeasurementTree result = matcher.prepareMeasurementTree(timingStack);
                consumer.publish(result);
            } else {
                cache.put(timing);
            }
        } else {
            cache.put(timing);
        }
    }

}
