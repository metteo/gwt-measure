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

import com.googlecode.gwtmeasure.server.incident.LoggingIncidentReportHandler;
import com.googlecode.gwtmeasure.server.internal.CompositeMetricsEventHandler;
import com.googlecode.gwtmeasure.server.internal.MeasureException;
import com.googlecode.gwtmeasure.server.internal.NullPerformanceEventFilter;
import com.googlecode.gwtmeasure.server.spi.IncidentReportHandler;
import com.googlecode.gwtmeasure.server.spi.MetricsEventHandler;
import com.googlecode.gwtmeasure.server.spi.PerformanceEventFilter;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class MeasureContext {

    private static final MeasureContext instance = new MeasureContext();

    private final Map<Class<?>, Class<?>> registry = new ConcurrentHashMap<Class<?>, Class<?>>();
    private final Map<Class<?>, Object> beans = new ConcurrentHashMap<Class<?>, Object>();

    // Default implementations
    static {
        CompositeMetricsEventHandler eventHandler = new CompositeMetricsEventHandler();
        eventHandler.addHandler(new LoggingHandler());

        instance.registerEventHandler(eventHandler);
        instance.registerIncidentHandler(LoggingIncidentReportHandler.class);
        instance.register(PerformanceEventFilter.class, new NullPerformanceEventFilter());
    }

    public static MeasureContext instance() {
        return instance;
    }

    public <T> T getBean(final Class<T> type) {
        Class<T> targetType = type;
        Class<?> impl = registry.get(type);
        if (impl != null) {
            targetType = (Class<T>) impl;
        }
        Object bean = beans.get(targetType);
        if (bean == null) {
            Object instance;
            try {
                // using first possible constructor
                Constructor<?> constructor = targetType.getConstructors()[0];
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] initargs = new Object[parameterTypes.length];
                for (int i = 0, parameterTypesLength = parameterTypes.length; i < parameterTypesLength; i++) {
                    Class<?> parameterType = parameterTypes[i];
                    Object dependency = getBean(parameterType);
                    initargs[i] = dependency;
                }
                instance = constructor.newInstance(initargs);
            } catch (Exception e) {
                throw new MeasureException("Failed to instantiate", e);
            }
            beans.put(targetType, instance);
            return (T) instance;
        }
        return (T) bean;
    }

    public <T> void register(Class<T> iface, T bean) {
        beans.put(iface, bean);
    }

    public <T> void register(Class<T> iface, Class<? extends T> impl) {
        registry.put(iface, impl);
    }

    public void reset() {
        beans.clear();
    }

    public void registerEventHandler(Class<? extends MetricsEventHandler> eventHandler) {
        register(MetricsEventHandler.class, eventHandler);
    }

    public void registerEventHandler(MetricsEventHandler eventHandler) {
        register(MetricsEventHandler.class, eventHandler);
    }

    public void registerIncidentHandler(IncidentReportHandler incidentHandler) {
        register(IncidentReportHandler.class, incidentHandler);
    }

    public void registerIncidentHandler(Class<? extends IncidentReportHandler> incidentHandler) {
        register(IncidentReportHandler.class, incidentHandler);
    }


}
