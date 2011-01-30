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
import com.google.code.gwtmeasure.client.spi.MeasurementControl;
import com.google.code.gwtmeasure.sample.shared.Model;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class SampleEntryPoint implements EntryPoint, MeasurementControl {

    private TextArea textArea;

    public void onModuleLoad() {
        Measurements.setDeliveryChannel(this);

        PendingMeasurement measurement = Measurements.start("rendering");

        RootPanel panel = RootPanel.get();
        panel.add(new Label("Measurements"));
        textArea = new TextArea();
        textArea.setWidth("500px");
        textArea.setHeight("400px");
        panel.add(textArea);

        MyServiceAsync service = GWT.create(MyService.class);
        service.doStuff(new Model(), new AsyncCallback<Model>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(Model result) {
            }
        });

        measurement.stop();
    }

    public void submit(PendingMeasurement measurement) {
        textArea.setText(textArea.getText() + "\n" + measurement);
    }
}
