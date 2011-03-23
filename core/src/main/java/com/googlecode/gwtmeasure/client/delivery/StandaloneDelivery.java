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

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.googlecode.gwtmeasure.client.internal.DeliveryBuffer;
import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.List;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class StandaloneDelivery {

    private static final StandaloneDelivery instance = new StandaloneDelivery();

    // TODO Make configurable
    private static final String SERVLET_LOCATION = "measurements";

    public static StandaloneDelivery instance() {
        return instance;
    }

    public void deliver() {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, SERVLET_LOCATION);

        DeliveryBuffer deliveryBuffer = DeliveryBuffer.instance();
        List<PerformanceTiming> timings = deliveryBuffer.popTimings();
        List<IncidentReport> incidents = deliveryBuffer.popIncidents();

        HeaderInjector injector = new HeaderInjector();
        if (injector.inject(builder, timings, incidents)) {
            MeasurementRequestCallback callback = new MeasurementRequestCallback(timings, incidents);
            builder.setCallback(callback);
            try {
                builder.send();
            } catch (RequestException e) {
                deliveryBuffer.pushTiming(timings);
                deliveryBuffer.pushIncident(incidents);
                // TODO Consider lost piggybacked timings as well
            }

        }
    }

    private static class MeasurementRequestCallback implements RequestCallback {

        private final List<PerformanceTiming> timings;
        private final List<IncidentReport> incidents;

        public MeasurementRequestCallback(List<PerformanceTiming> timings, List<IncidentReport> incidents) {
            this.timings = timings;
            this.incidents = incidents;
        }

        public void onResponseReceived(Request request, Response response) {
            // results are delivered
        }

        public void onError(Request request, Throwable exception) {
            DeliveryBuffer deliveryBuffer = DeliveryBuffer.instance();

            deliveryBuffer.pushTiming(timings);
            deliveryBuffer.pushIncident(incidents);
        }

    }

}
