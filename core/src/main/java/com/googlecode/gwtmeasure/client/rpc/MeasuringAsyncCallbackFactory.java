package com.googlecode.gwtmeasure.client.rpc;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *  Factory class for overriding purposes.
 *
 * @author <a href="mailto:sergey.trasko@ctco.lv">Sergey Trasko</a>
 */
public class MeasuringAsyncCallbackFactory {

    public <T> MeasuringAsyncCallback<T> createAsyncCallback(AsyncCallback<T> originalCallback,
                                                             String methodName,
                                                             int requestId) {
        return new MeasuringAsyncCallback<T>(originalCallback, requestId);
    }

    public RequestCallback createRequestCallback(RequestCallback originalCallback,
                                                 String methodName,
                                                 MeasuringAsyncCallback asyncCallback) {
        return new MeasuringRequestCallback(originalCallback);
    }

}