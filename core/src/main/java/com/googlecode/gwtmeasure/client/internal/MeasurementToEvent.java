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
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class MeasurementToEvent {

    public PerformanceTiming createStartTiming(PendingMeasurement measurement) {
        long from = measurement.getFrom();

        String group = measurement.getSubSystem();
        String name = measurement.getEventGroup();

        PerformanceTiming.Builder beginBuilder = new PerformanceTiming.Builder()
                .setModuleName(SafeGWT.getModuleName())
                .setMillis(from)
                .setSubSystem(group)
                .setType(Constants.TYPE_BEGIN)
                .setEventGroup(name);

        appendParameters(measurement, beginBuilder);

        return beginBuilder.create();
    }

    public PerformanceTiming createEndTiming(PendingMeasurement measurement) {
        long to = measurement.getTo();

        String group = measurement.getSubSystem();
        String name = measurement.getEventGroup();

        PerformanceTiming.Builder endBuilder = new PerformanceTiming.Builder()
                .setModuleName(SafeGWT.getModuleName())
                .setMillis(to)
                .setSubSystem(group)
                .setType(Constants.TYPE_END)
                .setEventGroup(name);

        appendParameters(measurement, endBuilder);
        return endBuilder.create();
    }

    private void appendParameters(PendingMeasurement measurement, PerformanceTiming.Builder builder) {
        Set<String> parameterNames = measurement.getParameterNames();
        for (String parameterName : parameterNames) {
            String value = measurement.getParameter(parameterName);
            builder.setParameter(parameterName, value);
        }
    }

}
