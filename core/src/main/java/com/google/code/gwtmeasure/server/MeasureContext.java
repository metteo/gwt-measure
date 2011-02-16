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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasureContext {

    private static final Logger logger = LoggerFactory.getLogger(MeasureContext.class);

    private static final MeasureContext instance = new MeasureContext();

    private final Map<Class<?>, Object> beans = new ConcurrentHashMap<Class<?>, Object>();

    public static MeasureContext instance() {
        return instance;
    }

    // TODO Tackle syncronization issues and provide simplistic constructor-based DI concept
    public <T> T getBean(Class<T> type) {
        Object bean = beans.get(type);
        if (bean == null) {
            Object instance;
            try {
                instance = type.newInstance();
            } catch (Exception e) {
                logger.error("Failed to instantiate", e);
                return null;
            }
            beans.put(type, instance);
            return (T) instance;
        }
        return (T) bean;
    }

}
