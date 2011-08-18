/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.gwtmeasure.client.delivery;

import com.google.gwt.http.client.RequestBuilder;
import com.googlecode.gwtmeasure.client.Configuration;
import com.googlecode.gwtmeasure.client.internal.WindowId;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public abstract class ResultInjector {

    protected MeasurementSerializer serializer;

    protected ResultInjector(MeasurementSerializer serializer) {
        this.serializer = serializer;
    }

    public abstract Result inject(RequestBuilder requestBuilder,
                                  List<PerformanceTiming> timings,
                                  List<IncidentReport> incidents);

    // TODO Check what happens when incident is too long
    protected void injectIncidents(RequestBuilder requestBuilder, List<IncidentReport> incidents, Result result) {
        WindowId windowId = Configuration.getWindowId();
        if (windowId != null) {
            String stringValue = Long.toString(windowId.getValue());
            requestBuilder.setHeader(Constants.HEADER_WND_ID, stringValue);
        }

        int headerLimit = Configuration.getHeaderLimit();
        if (!incidents.isEmpty()) {
            String serializedIncidents = serializer.serialize(incidents, headerLimit);
            requestBuilder.setHeader(Constants.HEADER_ERRORS, serializedIncidents);

            result.shouldSend = true;
        }
    }

    public final static class Result {

        boolean shouldSend = false;
        List<PerformanceTiming> timings = new ArrayList<PerformanceTiming>();

        public boolean shouldSend() {
            return shouldSend;
        }

        public List<PerformanceTiming> getExcessTimings() {
            return timings;
        }
    }

}
