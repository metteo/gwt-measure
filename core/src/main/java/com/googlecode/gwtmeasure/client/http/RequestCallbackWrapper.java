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

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.googlecode.gwtmeasure.client.Measurements;
import com.googlecode.gwtmeasure.client.internal.SafeGWT;
import com.googlecode.gwtmeasure.client.internal.TimeUtils;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class RequestCallbackWrapper implements RequestCallback {
    
    private RequestCallback callback;
    private int requestId;

    public RequestCallbackWrapper(RequestCallback callback, int requestId) {
        this.callback = callback;
        this.requestId = requestId;
    }

    public void onResponseReceived(Request request, Response response) {
        HttpStatsContext.setLastResolvedRequestId(requestId);

        responseReceived();
        callback.onResponseReceived(request, response);
        httpEnd();
    }

    public void onError(Request request, Throwable exception) {
        HttpStatsContext.setLastResolvedRequestId(requestId);

        responseReceived();
        callback.onError(request, exception);
        httpEnd();
    }

    public RequestCallback getCallback() {
        return callback;
    }

    private void httpEnd() {
        MeasurementHub hub = Measurements.getMeasurementHub();

        PerformanceTiming timing = new PerformanceTiming.Builder()
                .setMillis(TimeUtils.current())
                .setModuleName(SafeGWT.getModuleName())
                .setSubSystem(Constants.SUB_SYSTEM_HTTP)
                .setType(Constants.TYPE_END)
                .setEventGroup(Integer.toString(requestId))
                .create();

        hub.submit(timing);
    }

    private void responseReceived() {
        MeasurementHub hub = Measurements.getMeasurementHub();

        PerformanceTiming timing = new PerformanceTiming.Builder()
                .setMillis(TimeUtils.current())
                .setModuleName(SafeGWT.getModuleName())
                .setSubSystem(Constants.SUB_SYSTEM_HTTP)                
                .setType(Constants.TYPE_RESPONSE_RECEIVED)
                .setEventGroup(Integer.toString(requestId))
                .create();

        hub.submit(timing);
    }

}
