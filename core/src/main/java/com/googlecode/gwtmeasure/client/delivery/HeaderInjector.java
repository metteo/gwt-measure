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

package com.googlecode.gwtmeasure.client.delivery;

import com.google.gwt.http.client.RequestBuilder;
import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.client.internal.DeliveryBuffer;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.List;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class HeaderInjector {

    private MeasurementSerializer serializer;

    public HeaderInjector() {
        this(new MeasurementSerializer());
    }

    public HeaderInjector(MeasurementSerializer serializer) {
        this.serializer = serializer;
    }

    public boolean inject(RequestBuilder requestBuilder, List<PerformanceTiming> timings, List<IncidentReport> incidents) {
        boolean result = false;
        if (!timings.isEmpty()) {            
            String serializedTimings = serializer.serialize(timings);
            requestBuilder.setHeader(Constants.HEADER_RESULT, serializedTimings);
            result = true;
        }
        if (!incidents.isEmpty()) {
            String serializedIncidents = serializer.serialize(incidents);
            requestBuilder.setHeader(Constants.HEADER_ERRORS, serializedIncidents);
            result = true;
        }
        return result;
    }

}
