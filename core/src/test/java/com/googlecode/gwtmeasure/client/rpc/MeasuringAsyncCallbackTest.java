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
import com.googlecode.gwtmeasure.client.exception.IncidentReportFactory;
import com.googlecode.gwtmeasure.client.internal.VoidHub;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasuringAsyncCallbackTest {

    AsyncCallback originalCallback;
    IncidentReportFactory incidentReportFactory;
    MeasuringAsyncCallback<Object> callback;
    Object result;

    @Before
    public void setUp() throws Exception {
        Measurements.setMeasurementHub(new VoidHub());

        originalCallback = mock(AsyncCallback.class);
        callback = new MeasuringAsyncCallback<Object>(originalCallback, 5);
        result = new Object();

        incidentReportFactory = mock(IncidentReportFactory.class);
        callback.incidentReportFactory = incidentReportFactory;

        RpcContext.setLastResolvedRequestId(0);
    }

    @Test
    public void testOnSuccess() throws Exception {
        callback.onSuccess(result);

        assertThat(RpcContext.getLastResolvedRequestId(), equalTo(5));
        verify(originalCallback).onSuccess(result);
    }

    @Test
    public void testOnFailure() throws Exception {
        NullPointerException exception = new NullPointerException();

        callback.onFailure(exception);

        assertThat(RpcContext.getLastResolvedRequestId(), equalTo(5));
        verify(originalCallback).onFailure(exception);
        verify(incidentReportFactory).createRpcReport(exception);
    }

}
