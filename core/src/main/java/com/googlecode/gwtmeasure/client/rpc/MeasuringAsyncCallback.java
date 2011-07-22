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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.gwtmeasure.client.Measurements;
import com.googlecode.gwtmeasure.client.PendingMeasurement;
import com.googlecode.gwtmeasure.client.internal.DeliveryQueue;
import com.googlecode.gwtmeasure.client.internal.TypeUtils;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.IncidentReport;

/**
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class MeasuringAsyncCallback<T> implements AsyncCallback<T> {

    private final int requestId;
    final AsyncCallback<T> originalCallback;
    final PendingMeasurement measurement;

    public MeasuringAsyncCallback(AsyncCallback<T> originalCallback, int requestId) {
        this.originalCallback = originalCallback;
        this.requestId = requestId;
        String callbackType = originalCallback.getClass().getName();
        String methodName = TypeUtils.classSimpleName(callbackType) + ".onSuccess";
        this.measurement = Measurements.start(Integer.toString(requestId), Constants.SUB_SYSTEM_RPC);
        this.measurement.setParameter(Constants.PARAM_METHOD, methodName);
    }

    public void onSuccess(T result) {
        RpcContext.setLastResolvedRequestId(requestId);
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
        IncidentReport report = IncidentReport.createRpcReport(caught);
        DeliveryQueue.instance().pushIncident(report);
        
        measurement.discard();
        originalCallback.onFailure(caught);
    }

}