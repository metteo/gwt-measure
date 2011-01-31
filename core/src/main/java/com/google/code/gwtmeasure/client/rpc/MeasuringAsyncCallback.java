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

package com.google.code.gwtmeasure.client.rpc;

import com.google.code.gwtmeasure.client.Measurements;
import com.google.code.gwtmeasure.client.PendingMeasurement;
import com.google.code.gwtmeasure.shared.Constants;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasuringAsyncCallback<T> implements AsyncCallback<T> {

    final AsyncCallback<T> originalCallback;
    final PendingMeasurement measurement;

    public MeasuringAsyncCallback(AsyncCallback<T> originalCallback) {
        this.originalCallback = originalCallback;
        String callbackType = originalCallback.getClass().getName() + ".onSuccess";
        this.measurement = Measurements.start(callbackType, Constants.SUB_SYSTEM_RPC);
    }

    public void onSuccess(T result) {
        try {
            originalCallback.onSuccess(result);
        } catch (RuntimeException exception) {
            measurement.discard();
            throw exception;
        } finally {
            measurement.stop();
        }
    }

    public void onFailure(Throwable caught) {
        measurement.discard();
        originalCallback.onFailure(caught);
    }

}