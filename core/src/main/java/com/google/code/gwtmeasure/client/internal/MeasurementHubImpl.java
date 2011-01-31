package com.google.code.gwtmeasure.client.internal;

import com.google.code.gwtmeasure.client.PendingMeasurement;
import com.google.code.gwtmeasure.client.PerformanceEvent;
import com.google.code.gwtmeasure.client.PerformanceEventHandler;
import com.google.code.gwtmeasure.client.spi.MeasurementHub;
import com.google.code.gwtmeasure.shared.PerformanceMetrics;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

/**
 * @author dmitry.buzdin
 */
public class MeasurementHubImpl implements MeasurementHub {

    private final MeasurementToEvent converter = new MeasurementToEvent();
    private final SimpleEventBus eventBus = new SimpleEventBus();

    public MeasurementHubImpl() {
    }

    public void submit(PendingMeasurement measurement) {
        PerformanceMetrics[] events = converter.convert(measurement);
        for (PerformanceMetrics event : events) {
            submit(event);
        }
    }

    public void submit(PerformanceMetrics metrics) {
        eventBus.fireEvent(new PerformanceEvent(metrics));        
    }

    public HandlerRegistration addHandler(PerformanceEventHandler handler) {
        return eventBus.addHandler(PerformanceEvent.TYPE, handler);
    }

}
