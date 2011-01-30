package com.google.code.gwtmeasure.client.internal;

import com.google.code.gwtmeasure.client.PendingMeasurement;
import com.google.code.gwtmeasure.client.spi.MeasurementControl;
import com.google.code.gwtmeasure.shared.MetricEvent;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;

/**
 * @author dmitry.buzdin
 */
public class MeasurementToEventTest extends Assert {

    private MeasurementToEvent converter;

    @Before
    public void setUp() {
        converter = new MeasurementToEvent();
    }

    @Test
    public void testConvert() throws Exception {
        PendingMeasurement measurement = new PendingMeasurement("A", "B", mock(MeasurementControl.class));
        measurement.stop();

        MetricEvent[] events = converter.convert(measurement);

        assertThat(events.length, is(2));
        assertThat(events[0].getType(), equalTo("begin"));
        assertThat(events[1].getType(), equalTo("end"));
    }

}
