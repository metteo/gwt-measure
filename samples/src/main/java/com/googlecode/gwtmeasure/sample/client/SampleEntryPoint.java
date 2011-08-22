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

package com.googlecode.gwtmeasure.sample.client;

import com.google.gwt.user.server.rpc.RPCRequest;
import com.googlecode.gwtmeasure.shared.Measurements;
import com.googlecode.gwtmeasure.client.PendingMeasurement;
import com.googlecode.gwtmeasure.client.http.HttpStatsContext;
import com.googlecode.gwtmeasure.client.http.MeasuredRequestBuilder;
import com.googlecode.gwtmeasure.client.rpc.RpcContext;
import com.googlecode.gwtmeasure.sample.shared.Model;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.gwtmeasure.shared.OpenMeasurement;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class SampleEntryPoint implements EntryPoint, ClickHandler {

    private TextArea textArea;

    private MyServiceAsync service = GWT.create(MyService.class);

    private Button customMeasureButton;
    private Button rpcButton;
    private Button xhrButton;
    private Button asyncButton;
    private Button errorButton;

    public void onModuleLoad() {
        OpenMeasurement measurement = Measurements.start("onModuleLoad");

        RootPanel panel = RootPanel.get();
        panel.add(new Label("Measurements"));
        textArea = new TextArea();
        textArea.setWidth("400px");
        textArea.setHeight("300px");

        VerticalPanel vpanel = new VerticalPanel();
        panel.add(vpanel);

        vpanel.add(textArea);

        customMeasureButton = new Button("Add Measurement");
        rpcButton = new Button("RPC Request");
        xhrButton = new Button("XHR Request");
        asyncButton = new Button("Run Async");
        errorButton = new Button("Exception");

        customMeasureButton.addClickHandler(this);
        rpcButton.addClickHandler(this);
        xhrButton.addClickHandler(this);
        asyncButton.addClickHandler(this);
        errorButton.addClickHandler(this);

        HorizontalPanel hpanel = new HorizontalPanel();

        hpanel.add(customMeasureButton);
        hpanel.add(rpcButton);
        hpanel.add(xhrButton);
        hpanel.add(asyncButton);

        vpanel.add(hpanel);

        HorizontalPanel hpanel2 = new HorizontalPanel();

        hpanel2.add(errorButton);

        vpanel.add(hpanel2);

        for (int i = 0; i < 3; i++) {
            OpenMeasurement random = Measurements.start("random");
            random.stop();
        }

        callServer();

        measurement.stop();
    }

    private void callXhrServer() {
        RequestBuilder builder = new MeasuredRequestBuilder(RequestBuilder.POST, "/servlet");
        builder.setCallback(new RequestCallback() {
            public void onResponseReceived(Request request, Response response) {
                textArea.setText("Success with request id " + HttpStatsContext.getLastResolvedRequestId());
            }

            public void onError(Request request, Throwable exception) {
                textArea.setText("Failure");
            }
        });
        try {
            builder.send();
        } catch (RequestException e) {
            textArea.setText("Exception : " + e.getMessage());
        }
    }

    private void callServer() {
        OpenMeasurement start = Measurements.start(getCounter(), "useraction");
        service.doStuff(new Model(), new MyCallback(start));
    }

    private String getCounter() {
        int requestId = RpcContext.getRequestIdCounter();
        if (requestId > 0) {
            requestId++;
        }
        return String.valueOf(requestId);
    }

    public void onClick(ClickEvent event) {
        Object source = event.getSource();

        if (source == errorButton) {
            throw new NullPointerException();
        } else if (source == customMeasureButton) {
            OpenMeasurement m = Measurements.start("test");
            m.setParameter("username", "user1");
            m.stop();
        } else if (source == rpcButton) {
            callServer();
        } else if (source == xhrButton) {
            callXhrServer();
        } else if (source == asyncButton) {
            GWT.runAsync(new RunAsyncCallback() {
                public void onFailure(Throwable reason) {
                    textArea.setText("Failure");
                }

                public void onSuccess() {
                    AdditionalView additionalView = new AdditionalView();
                    additionalView.render();
                }
            });
        }
    }

    public class MyCallback implements AsyncCallback<Model> {

        private OpenMeasurement openMeasurement;

        public MyCallback(OpenMeasurement openMeasurement) {
            this.openMeasurement = openMeasurement;
        }

        public void onFailure(Throwable caught) {
            textArea.setText("Failure");
            openMeasurement.discard();
        }

        public void onSuccess(Model result) {
            textArea.setText("Success with id " + RpcContext.getLastResolvedRequestId());
            openMeasurement.stop();
        }

    }

}
