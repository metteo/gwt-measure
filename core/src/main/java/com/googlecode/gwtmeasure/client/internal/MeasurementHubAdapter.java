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
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasurementHubAdapter {

    private final MeasurementHub hub;
    private final MeasurementToEvent converter = new MeasurementToEvent();

    public MeasurementHubAdapter(MeasurementHub hub) {
        this.hub = hub;
    }

    public void submit(PendingMeasurement measurement) {
        PerformanceTiming[] events = converter.convert(measurement);
        for (PerformanceTiming event : events) {
            hub.submit(event);
        }
    }

}
