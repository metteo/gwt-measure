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

package com.googlecode.gwtmeasure.client.http;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.googlecode.gwtmeasure.client.Measurements;
import com.googlecode.gwtmeasure.client.delivery.HeaderInjector;
import com.googlecode.gwtmeasure.client.internal.DeliveryQueue;
import com.googlecode.gwtmeasure.client.internal.TimeUtils;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.List;

/**
 * Instrumented implementation of RequestBuilder. Use it instead of the standard one to recieve
 * measurements of the http requests and piggybacked delivery of results via HTTP headers.
 *
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class MeasuringRequestBuilder extends RequestBuilder {

    public MeasuringRequestBuilder(Method httpMethod, String url) {
        super(httpMethod, url);
    }

    @Override
    public Request send() throws RequestException {
        int id = HttpStatsContext.getNextRequestId();
        attachHeaders(id);
        attachMeasurements();
        requestSent(id);

        return super.send();
    }

    @Override
    public Request sendRequest(String requestData, RequestCallback callback) throws RequestException {
        int id = HttpStatsContext.getNextRequestId();
        attachHeaders(id);
        attachMeasurements();
        requestSent(id);

        RequestCallbackWrapper wrapper = new RequestCallbackWrapper(callback, id);
        return super.sendRequest(requestData, wrapper);
    }

    @Override
    public void setCallback(RequestCallback callback) {
        int requestId = HttpStatsContext.getLastRequestId();
        super.setCallback(new RequestCallbackWrapper(callback, requestId));
    }

    @Override
    public RequestCallback getCallback() {
        RequestCallbackWrapper wrapper = (RequestCallbackWrapper) super.getCallback();
        return wrapper.getCallback();
    }

    private void attachHeaders(int id) {
        setHeader(Constants.HEADER_UID, Integer.toString(id));
    }

    private void attachMeasurements() {
        DeliveryQueue deliveryQueue = DeliveryQueue.instance();
        List<PerformanceTiming> timings = deliveryQueue.popTimings();
        List<IncidentReport> incidents = deliveryQueue.popIncidents();

        HeaderInjector injector = new HeaderInjector();
        injector.inject(this, timings, incidents);
    }

    private void requestSent(int id) {
        MeasurementHub hub = Measurements.getMeasurementHub();

        PerformanceTiming timing = new PerformanceTiming.Builder()
                .setMillis(TimeUtils.current())
                .setModuleName(GWT.getModuleName())
                .setSubSystem(Constants.SUB_SYSTEM_HTTP)
                .setType(Constants.TYPE_REQUEST_SENT)
                .setEventGroup(Integer.toString(id))
                .create();

        hub.submit(timing);
    }
}
