package com.googlecode.gwtmeasure.client.internal;

import com.googlecode.gwtmeasure.client.PendingMeasurement;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.client.spi.MeasurementListener;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;
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
        MeasurementHubAdapter hubAdapter = mock(MeasurementHubAdapter.class);
        MeasurementListener listener = mock(MeasurementListener.class);
        PendingMeasurement measurement = new PendingMeasurement("name", "group", hubAdapter, listener);
        measurement.stop();

        PerformanceTiming[] events = converter.convert(measurement);

        assertThat(events.length, is(2));

        assertThat(events[0].getModuleName(), equalTo(""));
        assertThat(events[0].getType(), equalTo("begin"));
        assertThat(events[0].getSubSystem(), equalTo("group"));
        assertThat(events[0].getEventGroup(), equalTo("name"));

        assertThat(events[1].getModuleName(), equalTo(""));
        assertThat(events[1].getType(), equalTo("end"));
        assertThat(events[1].getSubSystem(), equalTo("group"));
        assertThat(events[1].getEventGroup(), equalTo("name"));
    }

}
