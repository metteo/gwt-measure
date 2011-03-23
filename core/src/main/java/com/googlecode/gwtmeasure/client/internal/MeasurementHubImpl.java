package com.googlecode.gwtmeasure.client.internal;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.googlecode.gwtmeasure.client.Measurements;
import com.googlecode.gwtmeasure.client.PendingMeasurement;
import com.googlecode.gwtmeasure.client.PerformanceEvent;
import com.googlecode.gwtmeasure.client.PerformanceEventHandler;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

/**
 * @author dmitry.buzdin
 */
public class MeasurementHubImpl implements MeasurementHub {

    private final MeasurementToEvent converter = new MeasurementToEvent();
    private final SimpleEventBus eventBus = new SimpleEventBus();

    public MeasurementHubImpl() {
    }

    public void submit(PendingMeasurement measurement) {
        PerformanceTiming[] events = converter.convert(measurement);
        for (PerformanceTiming event : events) {
            submit(event);
        }
    }

    public void submit(PerformanceTiming timing) {
        long windowId = Measurements.getWindowId().getValue();
        timing.setParameter(Constants.PARAM_WINDOWID, windowId);
        eventBus.fireEvent(new PerformanceEvent(timing));
    }

    public HandlerRegistration addHandler(PerformanceEventHandler handler) {
        return eventBus.addHandler(PerformanceEvent.TYPE, handler);
    }

}
