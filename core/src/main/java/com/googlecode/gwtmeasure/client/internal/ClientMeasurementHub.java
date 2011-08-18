package com.googlecode.gwtmeasure.client.internal;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.googlecode.gwtmeasure.client.Configuration;
import com.googlecode.gwtmeasure.client.PerformanceEvent;
import com.googlecode.gwtmeasure.client.PerformanceEventHandler;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

/**
 * @author dmitry.buzdin
 */
public class ClientMeasurementHub implements MeasurementHub {

    private EventBus eventBus;

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void submit(PerformanceTiming timing) {
        WindowId windowId = Configuration.getWindowId();
        long uniqueId = windowId.getValue();
        timing.setParameter(Constants.PARAM_WINDOWID, uniqueId);

        if (eventBus != null) {
            eventBus.fireEvent(new PerformanceEvent(timing));
        }
    }

}
