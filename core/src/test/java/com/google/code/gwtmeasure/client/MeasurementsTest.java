package com.google.code.gwtmeasure.client;

import com.google.code.gwtmeasure.client.spi.DeliveryChannel;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.GreaterThan;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.Mockito.*;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasurementsTest extends Assert {

    private static final String MEASUREMENT_NAME = "a";
    private static final String MEASUREMENT_GROUP = "g";

    DeliveryChannel channel;

    @Before
    public void setUp() {
        this.channel = mock(DeliveryChannel.class);
        Measurements.setDeliveryChannel(channel);
    }

    @Test
    public void shouldMeasure() throws Exception {
        PendingMeasurement measurement = Measurements.start(MEASUREMENT_NAME);
        pause();

        measurement.stop();

        verify(channel).submit(measurement);
        assertThat(measurement.isDiscarded(), is(false));
        assertThat(measurement.getName(), is(MEASUREMENT_NAME));
        assertThat(measurement.getGroup(), is(""));
        assertThat(measurement.getTo(), new GreaterThan(measurement.getFrom()));
    }

    @Test
    public void shouldSetGroupName() throws Exception {
        PendingMeasurement measurement = Measurements.start(MEASUREMENT_NAME, MEASUREMENT_GROUP);

        measurement.stop();

        verify(channel).submit(measurement);
        assertThat(measurement.getGroup(), is(MEASUREMENT_GROUP));
    }

    @Test
    public void shouldDiscard() throws Exception {
        PendingMeasurement measurement = Measurements.start(MEASUREMENT_NAME);

        measurement.discard();

        verifyZeroInteractions(channel);
    }

    public static void pause() {
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
        }
    }

}
