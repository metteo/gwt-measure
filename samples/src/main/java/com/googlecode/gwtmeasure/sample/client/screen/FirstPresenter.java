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

package com.googlecode.gwtmeasure.sample.client.screen;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.gwtmeasure.client.http.HttpStatsContext;
import com.googlecode.gwtmeasure.client.http.MeasuredRequestBuilder;
import com.googlecode.gwtmeasure.client.rpc.RpcContext;
import com.googlecode.gwtmeasure.sample.client.MyServiceAsync;
import com.googlecode.gwtmeasure.sample.shared.Model;
import com.googlecode.gwtmeasure.shared.Measurements;
import com.googlecode.gwtmeasure.shared.OpenMeasurement;

import javax.inject.Inject;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class FirstPresenter extends AbstractActivity {

    public static final FirstPlace PLACE = new FirstPlace();

    public static class FirstPlace extends Place {
    }

    private MyServiceAsync service;
    private FirstView view;

    @Inject
    public FirstPresenter(MyServiceAsync service) {
        this.service = service;
    }

    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        OpenMeasurement measurement = Measurements.start("onModuleLoad");

        view = new FirstView();
        view.setPresenter(this);
        panel.setWidget(view);

        callServer();

        measurement.stop();
    }

  public void callXhrServer() {
        RequestBuilder builder = new MeasuredRequestBuilder(RequestBuilder.POST, "/servlet");
        builder.setCallback(new RequestCallback() {
            public void onResponseReceived(Request request, Response response) {
                view.textArea.setText("Success with request id " + HttpStatsContext.getLastResolvedRequestId());
            }

            public void onError(Request request, Throwable exception) {
                view.textArea.setText("Failure");
            }
        });
        try {
            builder.send();
        } catch (RequestException e) {
            view.textArea.setText("Exception : " + e.getMessage());
        }
    }

    public void callServer() {
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

    public void addMeasurement() {
        OpenMeasurement m = Measurements.start("test");
        m.setParameter("username", "user1");
        m.stop();
    }

    public void throwException() {
        throw new NullPointerException();
    }

    public void loadAsync() {
        GWT.runAsync(new RunAsyncCallback() {
            public void onFailure(Throwable reason) {
                view.textArea.setText("Failure");
            }

            public void onSuccess() {
                AdditionalView additionalView = new AdditionalView();
                additionalView.render();
            }
        });
    }

    public class MyCallback implements AsyncCallback<Model> {

        private OpenMeasurement openMeasurement;

        public MyCallback(OpenMeasurement openMeasurement) {
            this.openMeasurement = openMeasurement;
        }

        public void onFailure(Throwable caught) {
            view.textArea.setText("Failure");
            openMeasurement.discard();
        }

        public void onSuccess(Model result) {
            view.textArea.setText("Success with id " + RpcContext.getLastResolvedRequestId());
            openMeasurement.stop();
        }

    }

}
