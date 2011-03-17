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

package com.google.code.gwtmeasure.sample.client;

import com.google.code.gwtmeasure.client.Measurements;
import com.google.code.gwtmeasure.client.PendingMeasurement;
import com.google.code.gwtmeasure.sample.shared.Model;
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

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class SampleEntryPoint implements EntryPoint {

    private TextArea textArea;

    private MyServiceAsync service = GWT.create(MyService.class);

    public void onModuleLoad() {        
        PendingMeasurement measurement = Measurements.start("custom-measurement");

        RootPanel panel = RootPanel.get();
        panel.add(new Label("Measurements"));
        textArea = new TextArea();
        textArea.setWidth("400px");
        textArea.setHeight("300px");

        VerticalPanel vpanel = new VerticalPanel();
        panel.add(vpanel);

        vpanel.add(textArea);

        Button measureButton = new Button("Add Measurement");
        measureButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                PendingMeasurement m = Measurements.start("test");
                m.setParameter("user", "user1");
                m.stop();
            }
        });

        Button rpcButton = new Button("Submit RPC Request");
        rpcButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                callServer();
            }
        });

        Button xhrButton = new Button("Submit XHR Request");
        xhrButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                callXhrServer();
            }
        });

        Button asyncButton = new Button("Run Async");
        asyncButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
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
        });

        HorizontalPanel hpanel = new HorizontalPanel();

        hpanel.add(measureButton);
        hpanel.add(rpcButton);
        hpanel.add(xhrButton);
        hpanel.add(asyncButton);

        vpanel.add(hpanel);

        callServer();

        measurement.stop();
    }

    private void callXhrServer() {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "/servlet");
        builder.setCallback(new RequestCallback() {
            public void onResponseReceived(Request request, Response response) {
                textArea.setText("Success");
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
        service.doStuff(new Model(), new MyCallback());
    }

    public class MyCallback implements AsyncCallback<Model> {

        public void onFailure(Throwable caught) {
            textArea.setText("Failure");            
        }

        public void onSuccess(Model result) {
            textArea.setText("Success");
        }

    }

}
