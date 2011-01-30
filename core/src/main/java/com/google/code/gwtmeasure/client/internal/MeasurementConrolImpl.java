package com.google.code.gwtmeasure.client.internal;

import com.google.code.gwtmeasure.client.PendingMeasurement;
import com.google.code.gwtmeasure.client.spi.MeasurementControl;
import com.google.code.gwtmeasure.shared.MetricEvent;

/**
 * @author dmitry.buzdin
 */
public class MeasurementConrolImpl implements MeasurementControl {

    final MeasurementToEvent converter = new MeasurementToEvent();

    public void submit(PendingMeasurement measurement) {
        MetricEvent[] events = converter.convert(measurement);
        for (MetricEvent event : events) {
            submit(event);
        }
    }

    public void submit(MetricEvent event) {
        MeasurementBuffer.instance().push(event);
    }

}
