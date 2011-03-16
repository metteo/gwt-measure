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

package com.google.code.gwtmeasure.server;

import com.google.code.gwtmeasure.server.internal.CompositeMetricsEventHandler;
import com.google.code.gwtmeasure.server.internal.MeasureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasureContext {

    private static final MeasureContext instance = new MeasureContext();

    private final Map<Class<?>, Class<?>> registry = new ConcurrentHashMap<Class<?>, Class<?>>();
    private final Map<Class<?>, Object> beans = new ConcurrentHashMap<Class<?>, Object>();

    static {
        instance.register(MetricsEventHandler.class, new CompositeMetricsEventHandler());
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

}
