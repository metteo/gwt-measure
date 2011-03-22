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
import com.googlecode.gwtmeasure.client.internal.TimeUtils;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.PerformanceMetrics;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasuredRequestBuilder extends RequestBuilder {

    public MeasuredRequestBuilder(Method httpMethod, String url) {
        super(httpMethod, url);
    }

    protected MeasuredRequestBuilder(String httpMethod, String url) {
        super(httpMethod, url);
    }

    @Override
    public Request send() throws RequestException {
        requestSent();
        return super.send();
    }

    @Override
    public Request sendRequest(String requestData, RequestCallback callback) throws RequestException {
        requestSent();
        return super.sendRequest(requestData, callback);
    }

    @Override
    public void setCallback(RequestCallback callback) {
        super.setCallback(new RequestCallbackWrapper(callback));
    }

    @Override
    public RequestCallback getCallback() {
        RequestCallbackWrapper wrapper = (RequestCallbackWrapper) super.getCallback();
        return wrapper.getCallback();
    }

    // TODO EventGroup missing
    private void requestSent() {
        MeasurementHub hub = Measurements.getMeasurementHub();

        PerformanceMetrics metrics = new PerformanceMetrics.Builder()
                .setMillis(TimeUtils.current())
                .setModuleName(GWT.getModuleName())
                .setSubSystem(Constants.SUB_SYSTEM_HTTP)
                .setType(Constants.TYPE_REQUEST_SENT)
                .create();

        hub.submit(metrics);
    }
}
