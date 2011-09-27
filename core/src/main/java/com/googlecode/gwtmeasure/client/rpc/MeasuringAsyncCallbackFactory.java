package com.googlecode.gwtmeasure.client.rpc;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *  Factory class for overriding purposes.
 *
 * @author <a href="mailto:sergey.trasko@ctco.lv">Sergey Trasko</a>
 */
public class MeasuringAsyncCallbackFactory {

    public <T> AsyncCallback<T> createAsyncCallback(AsyncCallback<T> originalCallback, String methodName, int requestId) {
        return new MeasuringAsyncCallback<T>(originalCallback, requestId);
    }

    public RequestCallback createRequestCallback(RequestCallback originalCallback) {
        return new MeasuringRequestCallback(originalCallback);
    }

}