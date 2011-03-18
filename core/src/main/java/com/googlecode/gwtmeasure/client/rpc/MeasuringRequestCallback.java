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

package com.googlecode.gwtmeasure.client.rpc;

import com.googlecode.gwtmeasure.shared.Constants;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasuringRequestCallback implements RequestCallback {

    private RequestCallback callback;
    private MeasuringAsyncCallback wrappedCallback;

    private long start = System.currentTimeMillis();

    public MeasuringRequestCallback(String name, RequestCallback callback, MeasuringAsyncCallback wrappedCallback) {        
        this.callback = callback;
        this.wrappedCallback = wrappedCallback;
    }

    public void onResponseReceived(Request request, Response response) {
        String uidHeader = response.getHeader(Constants.HEADER_UID);
        this.callback.onResponseReceived(request, response);
    }

    public void onError(Request request, Throwable exception) {
        this.callback.onError(request, exception);
    }

}