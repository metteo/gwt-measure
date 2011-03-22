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

import com.googlecode.gwtmeasure.server.MetricsEventHandler;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class AggregatingMetricsHandler implements MetricsEventHandler {

    private EventCache cache;
    private MetricConsumer consumer;

    public AggregatingMetricsHandler(EventCache cache, MetricConsumer consumer) {
        this.cache = cache;
        this.consumer = consumer;
    }

    public void onEvent(PerformanceTiming timing) {
        PerformanceTiming result = cache.findMatch(timing);
        if (result == null) {
            cache.append(timing);
        } else {
            PerformanceMetric metric = prepareMetric(timing, result);
            consumer.publish(metric);
        }
    }

    private PerformanceMetric prepareMetric(PerformanceTiming timing, PerformanceTiming result) {
        PerformanceMetric metric = new PerformanceMetric();
        metric.setEventGroup(result.getEventGroup());
        metric.setModuleName(result.getModuleName());
        metric.setSubSystem(result.getSubSystem());

        long timeA = timing.getMillis();
        long timeB = result.getMillis();

        metric.setTime(Math.abs(timeA - timeB));

        return metric;
    }

}
