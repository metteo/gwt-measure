package com.google.code.gwtmeasure.client.rpc;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter;
import com.google.gwt.user.client.rpc.impl.Serializer;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasuringRemoteServiceProxy extends RemoteServiceProxy {

    protected MeasuringRemoteServiceProxy(String moduleBaseURL,
                                          String remoteServiceRelativePath,
                                          String serializationPolicyName,
                                          Serializer serializer) {
        super(moduleBaseURL, remoteServiceRelativePath, serializationPolicyName, serializer);
    }

    @Override
    protected <T> RequestCallback doCreateRequestCallback(RequestCallbackAdapter.ResponseReader responseReader,
                                                          String methodName,
                                                          int invocationCount,
                                                          AsyncCallback<T> callback) {
        MeasuringAsyncCallback wrappedCallback = new MeasuringAsyncCallback(callback);
        RequestCallback originalRequest = super.doCreateRequestCallback(responseReader, methodName, invocationCount, wrappedCallback);
        MeasuringRequestCallback requestCallback = new MeasuringRequestCallback(methodName, originalRequest, wrappedCallback);
        return requestCallback;
    }

}