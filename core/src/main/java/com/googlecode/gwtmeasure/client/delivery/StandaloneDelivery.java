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

import com.googlecode.gwtmeasure.client.internal.MeasurementBuffer;
import com.googlecode.gwtmeasure.shared.Constants;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class StandaloneDelivery {

    private static final StandaloneDelivery instance = new StandaloneDelivery();
    
    private final MeasurementSerializer serializer = new MeasurementSerializer();

    // TODO Make configurable
    private static final String SERVLET_LOCATION = "measurements";

    public static StandaloneDelivery instance() {
        return instance;
    }

    public void deliver() {
        MeasurementBuffer buffer = MeasurementBuffer.instance();
        String data = serializer.serialize(buffer);
        if (!"".equals(data)) {
            sendToServer(data);
        }
    }

    private void sendToServer(String data) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, SERVLET_LOCATION);
        builder.setCallback(new MeasurementRequestCallback());
        builder.setHeader(Constants.HEADER_RESULT, data);
        try {
            Request request = builder.send();
        } catch (RequestException e) {
            // TODO Temporary solution
        }
    }

    private static class MeasurementRequestCallback implements RequestCallback {

        public void onResponseReceived(Request request, Response response) {
        }

        public void onError(Request request, Throwable exception) {
        }

    }

}
