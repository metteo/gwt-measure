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
    private MeasurementHub hubAdapter;
    private MeasurementListener listener;

    @Before
    public void setUp() {
        converter = new MeasurementToEvent();
        hubAdapter = mock(MeasurementHub.class);
        listener = mock(MeasurementListener.class);
    }

    @Test
    public void shouldConvertStart() throws Exception {
        PendingMeasurement measurement = createMeasurement();

        PerformanceTiming result = converter.createStartTiming(measurement);

        assertTiming(result, "begin", "name", "group");
    }

    @Test
    public void shouldConvertEnd() throws Exception {
        PendingMeasurement measurement = createMeasurement();
        measurement.stop();

        PerformanceTiming result = converter.createEndTiming(measurement);

        assertTiming(result, "end", "name", "group");
    }

    private void assertTiming(PerformanceTiming event, String type, String subSystem, String eventGroup) {
        assertThat(event.getModuleName(), equalTo(""));
        assertThat(event.getType(), equalTo(type));
        assertThat(event.getSubSystem(), equalTo(subSystem));
        assertThat(event.getEventGroup(), equalTo(eventGroup));
    }

    private PendingMeasurement createMeasurement() {
        PendingMeasurement measurement = new PendingMeasurement(hubAdapter, listener);
        measurement.start("group", "name");
        return measurement;
    }


}
