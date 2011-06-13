/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.gwtmeasure.client;

import com.googlecode.gwtmeasure.client.internal.MeasurementHubAdapter;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.GreaterThan;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class PendingMeasurementTest extends Assert {

    private MeasurementHubAdapter hub;

    @Before
    public void setUp() {
        hub = mock(MeasurementHubAdapter.class);
    }

    @Test
    public void shouldHaveDefaults() {
        PendingMeasurement measurement = createMeasurement();

        assertThat(measurement.isStopped(), equalTo(FALSE));
        assertThat(measurement.isDiscarded(), equalTo(FALSE));
    }

    @Test
    public void shouldRegisterInHub() {
        PendingMeasurement measurement = createMeasurement();
        measurement.stop();

        verify(hub).submit(measurement);
        assertThat(measurement.isStopped(), equalTo(TRUE));
    }

    @Test
    public void shouldDiscard() {
        PendingMeasurement measurement = createMeasurement();
        measurement.discard();
        measurement.stop();

        verifyZeroInteractions(hub);
        assertThat(measurement.isStopped(), equalTo(TRUE));
        assertThat(measurement.isDiscarded(), equalTo(TRUE));
    }

    @Test
    public void shouldAvoidDoubleStops() {
        PendingMeasurement measurement = createMeasurement();
        measurement.stop();
        measurement.stop();

        verify(hub).submit(measurement);
        verifyNoMoreInteractions(hub);
    }

    @Test
    public void shouldSetParameter() {
        PendingMeasurement measurement = createMeasurement();
        assertThat(measurement.getParameter("A"), nullValue());

        measurement.setParameter("A", "V");

        assertThat(measurement.getParameter("A"), equalTo("V"));
    }

    @Test
    public void shouldMeasure() throws Exception {
        PendingMeasurement measurement = createMeasurement();
        pause();

        measurement.stop();

        verify(hub).submit(measurement);
        assertThat(measurement.isDiscarded(), is(false));
        assertThat(measurement.getName(), is("name"));
        assertThat(measurement.getGroup(), is("group"));
        assertThat(measurement.getTo(), new GreaterThan(measurement.getFrom()));
    }

    @Test
    public void shouldChangeNameAndGroupId() {
        PendingMeasurement measurement = createMeasurement();

        measurement.setName("newName");
        measurement.setGroup("newGroup");

        assertThat(measurement.getName(), is("newName"));
        assertThat(measurement.getGroup(), is("newGroup"));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailOnAttemptToModifyGroup() {
        PendingMeasurement measurement = createMeasurement();
        measurement.stop();
        measurement.setGroup("A");
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailOnAttemptToModifyName() {
        PendingMeasurement measurement = createMeasurement();
        measurement.stop();
        measurement.setName("A");
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailOnAttemptToModifyParam() {
        PendingMeasurement measurement = createMeasurement();
        measurement.stop();
        measurement.setParameter("A", "B");
    }

    private PendingMeasurement createMeasurement() {
        return new PendingMeasurement("name", "group", hub);
    }

    public static void pause() {
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
        }
    }

}
