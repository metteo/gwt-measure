package com.google.code.gwtmeasure.client.internal;

import com.google.code.gwtmeasure.client.PendingMeasurement;
import com.google.code.gwtmeasure.client.spi.MeasurementControl;
import com.google.code.gwtmeasure.shared.PerformanceMetrics;
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
        converter = new MeasurementToEvent() {
            @Override
            String moduleName() {
                return "moduleName";
            }
        };
    }

    @Test
    public void testConvert() throws Exception {
        PendingMeasurement measurement = new PendingMeasurement("name", "group", mock(MeasurementControl.class));
        measurement.stop();

        PerformanceMetrics[] events = converter.convert(measurement);

        assertThat(events.length, is(2));

        assertThat(events[0].getModuleName(), equalTo("moduleName"));
        assertThat(events[0].getType(), equalTo("begin"));
        assertThat(events[0].getSubSystem(), equalTo("group"));
        assertThat(events[0].getMethod(), equalTo("name"));

        assertThat(events[1].getModuleName(), equalTo("moduleName"));
        assertThat(events[1].getType(), equalTo("end"));
        assertThat(events[1].getSubSystem(), equalTo("group"));
        assertThat(events[1].getMethod(), equalTo("name"));
    }

}