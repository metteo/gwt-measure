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

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter;
import com.google.gwt.user.client.rpc.impl.RpcStatsContext;
import com.google.gwt.user.client.rpc.impl.Serializer;
import com.googlecode.gwtmeasure.client.delivery.DeliveryRpcRequestBuilder;

/**
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class MeasuringRemoteServiceProxy extends RemoteServiceProxy {

    final MeasuringAsyncCallbackFactory callbackFactory = GWT.create(MeasuringAsyncCallbackFactory.class);

    protected MeasuringRemoteServiceProxy(String moduleBaseURL,
                                          String remoteServiceRelativePath,
                                          String serializationPolicyName,
                                          Serializer serializer) {
        super(moduleBaseURL, remoteServiceRelativePath, serializationPolicyName, serializer);
        RpcRequestBuilder rpcRequestBuilder = GWT.create(DeliveryRpcRequestBuilder.class);
        setRpcRequestBuilder(rpcRequestBuilder);
    }

    @Override
    protected <T> RequestCallback doCreateRequestCallback(RequestCallbackAdapter.ResponseReader responseReader,
                                                          String methodName,
                                                          RpcStatsContext statsContext,
                                                          AsyncCallback<T> callback) {
        int requestId = getRequestId() - 1;
        RpcContext.setRequestIdCounter(requestId);
        MeasuringAsyncCallback<T> wrappedCallback = callbackFactory.createAsyncCallback(callback, methodName, requestId);
        RequestCallback originalRequest = super.doCreateRequestCallback(responseReader, methodName, statsContext, wrappedCallback);
        return callbackFactory.createRequestCallback(originalRequest, methodName, wrappedCallback);
    }

}