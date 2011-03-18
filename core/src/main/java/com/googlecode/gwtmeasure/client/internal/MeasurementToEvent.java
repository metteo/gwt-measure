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

package com.googlecode.gwtmeasure.client.internal;

import com.googlecode.gwtmeasure.client.PendingMeasurement;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.PerformanceMetrics;
import com.google.gwt.core.client.GWT;

import java.util.Set;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasurementToEvent {

    public PerformanceMetrics[] convert(PendingMeasurement measurement) {
        long from = measurement.getFrom();
        long to = measurement.getTo();
        String group = measurement.getGroup();
        String name = measurement.getName();

        PerformanceMetrics[] result = new PerformanceMetrics[2];

        PerformanceMetrics.Builder beginBuilder = new PerformanceMetrics.Builder()
                .setModuleName(moduleName())
                .setMillis(from)
                .setSubSystem(group)
                .setType(Constants.TYPE_START)
                .setEventGroup(name);

        appendParameters(measurement, beginBuilder);
        result[0] = beginBuilder.create();

        PerformanceMetrics.Builder endBuilder = new PerformanceMetrics.Builder()
                .setModuleName(moduleName())
                .setMillis(to)
                .setSubSystem(group)
                .setType(Constants.TYPE_END)
                .setEventGroup(name);
        
        appendParameters(measurement, endBuilder);
        result[1] = endBuilder.create();

        return result;
    }

    private void appendParameters(PendingMeasurement measurement, PerformanceMetrics.Builder builder) {
        Set<String> parameterNames = measurement.getParameterNames();
        for (String parameterName : parameterNames) {
            String value = measurement.getParameter(parameterName);
            builder.setParameter(parameterName, value);
        }
    }

    String moduleName() {
        return GWT.getModuleName();
    }

}