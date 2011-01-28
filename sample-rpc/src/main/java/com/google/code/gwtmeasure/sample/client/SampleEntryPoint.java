package com.google.code.gwtmeasure.sample.client;

import com.google.code.gwtmeasure.client.Measurements;
import com.google.code.gwtmeasure.client.PendingMeasurement;
import com.google.code.gwtmeasure.client.spi.DeliveryChannel;
import com.google.code.gwtmeasure.sample.shared.Model;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class SampleEntryPoint implements EntryPoint, DeliveryChannel {

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
