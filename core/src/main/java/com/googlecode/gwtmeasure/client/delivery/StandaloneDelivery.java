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

import com.google.gwt.http.client.*;
import com.googlecode.gwtmeasure.client.Configuration;
import com.googlecode.gwtmeasure.client.internal.DeliveryQueue;
import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.List;

/**
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class StandaloneDelivery {

    private static final StandaloneDelivery instance = new StandaloneDelivery();

    public static StandaloneDelivery instance() {
        return instance;
    }

    private final DeliveryQueue deliveryQueue;

    private DeliveryHandler deliveryHandler = new DeliveryHandler() {
        public void onDelivery(Request request, Response response) {
        }
    };

    public interface DeliveryHandler {
        void onDelivery(Request request, Response response);
    }

    public StandaloneDelivery() {
        deliveryQueue = DeliveryQueue.instance();
    }

    public void setDeliveryHandler(DeliveryHandler deliveryHandler) {
        this.deliveryHandler = deliveryHandler;
    }

    public void deliver() {
        String url = Configuration.getEndpointUrl();
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);

        List<PerformanceTiming> timings = deliveryQueue.popTimings();
        List<IncidentReport> incidents = deliveryQueue.popIncidents();

        ResultInjector injector = new RequestBodyInjector();
        HeaderInjector.Result result = injector.inject(builder, timings, incidents);

        if (result.shouldSend()) {
            MeasurementRequestCallback callback = new MeasurementRequestCallback(timings, incidents);
            builder.setCallback(callback);
            try {
                builder.send();
            } catch (RequestException e) {
                deliveryQueue.pushTiming(timings);
                deliveryQueue.pushIncident(incidents);
                // TODO Consider lost piggybacked timings as well
            }

        }
    }

    void measurementsDelivered(Request request, Response response) {
        deliveryHandler.onDelivery(request, response);
    }

    private class MeasurementRequestCallback implements RequestCallback {

        private final List<PerformanceTiming> timings;
        private final List<IncidentReport> incidents;

        public MeasurementRequestCallback(List<PerformanceTiming> timings, List<IncidentReport> incidents) {
            this.timings = timings;
            this.incidents = incidents;
        }

        public void onResponseReceived(Request request, Response response) {
            measurementsDelivered(request, response);
        }

        public void onError(Request request, Throwable exception) {
            DeliveryQueue deliveryQueue = DeliveryQueue.instance();

            deliveryQueue.pushTiming(timings);
            deliveryQueue.pushIncident(incidents);
        }

    }

}
