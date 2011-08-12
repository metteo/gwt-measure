package com.googlecode.gwtmeasure.client.internal;

import com.googlecode.gwtmeasure.client.PendingMeasurement;
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
    private MeasurementHubAdapter hubAdapter;
    private MeasurementListener listener;

    @Before
    public void setUp() {
        converter = new MeasurementToEvent();
        hubAdapter = mock(MeasurementHubAdapter.class);
        listener = mock(MeasurementListener.class);
    }

    @Test
    public void shouldConvert() throws Exception {
        PendingMeasurement measurement = createMeasurement();
        measurement.stop();

        PerformanceTiming[] events = converter.convert(measurement);

        assertThat(events.length, is(2));

        assertTiming(events[0], "begin", "name", "group");
        assertTiming(events[1], "end", "name", "group");
    }

    @Test
    public void shouldConvertChildren() throws Exception {
        PendingMeasurement measurement = createMeasurement();
        PendingMeasurement child = measurement.start("subName");
        PendingMeasurement subChild = child.start("subSubName");
        subChild.stop();
        child.stop();
        measurement.stop();

        PerformanceTiming[] events = converter.convert(measurement);

        assertThat(events.length, is(6));

        assertTiming(events[0], "begin", "name", "group");
        assertTiming(events[1], "end", "name", "group");
        assertTiming(events[2], "begin", "subName", "group");
        assertTiming(events[3], "end", "subName", "group");
        assertTiming(events[4], "begin", "subSubName", "group");
        assertTiming(events[5], "end", "subSubName", "group");
    }

    private void assertTiming(PerformanceTiming event, String type, String subSystem, String eventGroup) {
        assertThat(event.getModuleName(), equalTo(""));
        assertThat(event.getType(), equalTo(type));
        assertThat(event.getSubSystem(), equalTo(subSystem));
        assertThat(event.getEventGroup(), equalTo(eventGroup));
    }

    private PendingMeasurement createMeasurement() {
        return new PendingMeasurement("group", "name", hubAdapter, listener);
    }


}
