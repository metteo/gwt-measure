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

package com.google.code.gwtmeasure.client.internal;

import com.google.code.gwtmeasure.client.PendingMeasurement;
import com.google.code.gwtmeasure.shared.MetricEvent;
import com.google.gwt.core.client.GWT;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasurementToEvent {

    public MetricEvent[] convert(PendingMeasurement measurement) {
        long from = measurement.getFrom();
        long to = measurement.getTo();
        String group = measurement.getGroup();
        String name = measurement.getName();

        MetricEvent[] result = new MetricEvent[2];

        result[0] = new MetricEvent.Builder()
                .setModuleName(moduleName())
                .setMillis(from)
                .setSubSystem(group)
                .setType("begin")
                .setMethod(name)
                .create();

        result[1] = new MetricEvent.Builder()
                .setModuleName(moduleName())
                .setMillis(to)
                .setSubSystem(group)
                .setType("end")
                .setMethod(name)
                .create();

        return result;
    }

    String moduleName() {
        return GWT.getModuleName();
    }

}
