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

package com.googlecode.gwtmeasure.server;

import com.googlecode.gwtmeasure.client.PendingMeasurement;
import com.googlecode.gwtmeasure.client.internal.VoidMeasurementListener;
import com.googlecode.gwtmeasure.server.event.AggregatingMetricsHandler;
import com.googlecode.gwtmeasure.server.event.InMemoryStorage;
import com.googlecode.gwtmeasure.server.event.LoggingMetricConsumer;
import com.googlecode.gwtmeasure.server.event.MetricConsumer;
import com.googlecode.gwtmeasure.server.event.RawEventStorage;
import com.googlecode.gwtmeasure.server.incident.LoggingIncidentReportHandler;
import com.googlecode.gwtmeasure.server.internal.BeanContainer;
import com.googlecode.gwtmeasure.server.internal.NullPerformanceEventFilter;
import com.googlecode.gwtmeasure.server.internal.ServerMeasurementHub;
import com.googlecode.gwtmeasure.server.spi.IncidentReportHandler;
import com.googlecode.gwtmeasure.server.spi.MetricsEventHandler;
import com.googlecode.gwtmeasure.server.spi.PerformanceEventFilter;
import com.googlecode.gwtmeasure.shared.Measurements;
import com.googlecode.gwtmeasure.shared.OpenMeasurement;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class MeasurementEngine {

    private static final MeasurementEngine instance = new MeasurementEngine();
    private boolean initialized = false;

    public static MeasurementEngine instance() {
        return instance;
    }

    private final BeanContainer beanContainer = new BeanContainer();

    // Default implementations
    public void init() {
        if (initialized) {
            return;
        }

        instance.registerEventHandler(AggregatingMetricsHandler.class);
        instance.registerIncidentHandler(LoggingIncidentReportHandler.class);

        beanContainer.register(PerformanceEventFilter.class, new NullPerformanceEventFilter());
        beanContainer.register(RawEventStorage.class, new InMemoryStorage());
        beanContainer.register(MetricConsumer.class, new LoggingMetricConsumer());

        attachApiImpl();
	    initialized = true;
    }

    public void destroy() {
        beanContainer.reset();
    }

    private void attachApiImpl() {
        Measurements.setServerImpl(new Measurements.Impl() {
            public OpenMeasurement run(String eventGroup, String subSystem) {
                VoidMeasurementListener listener = new VoidMeasurementListener();
                MetricsEventHandler handler = beanContainer.getBean(MetricsEventHandler.class);
                ServerMeasurementHub hub = new ServerMeasurementHub(handler);
                PendingMeasurement measurement = new PendingMeasurement(hub, listener);
                measurement.start(eventGroup, subSystem);
                return measurement;
            }
        });
    }

    public BeanContainer getBeanContainer() {
        return beanContainer;
    }

    public void registerEventHandler(Class<? extends MetricsEventHandler> eventHandler) {
        beanContainer.register(MetricsEventHandler.class, eventHandler);
    }

    public void registerEventHandler(MetricsEventHandler eventHandler) {
        beanContainer.register(MetricsEventHandler.class, eventHandler);
    }

    public void registerIncidentHandler(IncidentReportHandler incidentHandler) {
        beanContainer.register(IncidentReportHandler.class, incidentHandler);
    }

    public void registerIncidentHandler(Class<? extends IncidentReportHandler> incidentHandler) {
        beanContainer.register(IncidentReportHandler.class, incidentHandler);
    }

}
