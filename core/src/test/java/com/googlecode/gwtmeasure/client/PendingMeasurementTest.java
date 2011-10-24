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

import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.client.spi.MeasurementListener;
import com.googlecode.gwtmeasure.shared.OpenMeasurement;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.GreaterThan;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class PendingMeasurementTest extends Assert {

    private MeasurementHub hub;
    private MeasurementListener listener;

    @Before
    public void setUp() {
        hub = mock(MeasurementHub.class);
        listener = mock(MeasurementListener.class);
    }

    @Test
    public void shouldHaveDefaults() {
        PendingMeasurement measurement = createMeasurement();
        assertThat(measurement.getStatus(), is(OpenMeasurement.Status.STARTED));
    }

    @Test
    public void shouldRegisterInHub() {
        PendingMeasurement measurement = createMeasurement();
        measurement.stop();

        verify(hub, times(2)).submit(Mockito.any(PerformanceTiming.class));
        verify(listener).onStart(measurement);
        verify(listener).onStop(measurement);
        assertThat(measurement.getStatus(), is(OpenMeasurement.Status.STOPPED));
    }

    @Test
    public void shouldDiscard() {
        PendingMeasurement measurement = createMeasurement();
        measurement.discard();
        measurement.stop();

        verify(hub).submit(Mockito.any(PerformanceTiming.class));
        assertThat(measurement.getStatus(), is(OpenMeasurement.Status.STOPPED));
    }

    @Test
    public void shouldAvoidDoubleStops() {
        PendingMeasurement measurement = createMeasurement();
        measurement.stop();
        measurement.stop();

        verify(hub, times(2)).submit(Mockito.any(PerformanceTiming.class));
        verify(listener).onStart(measurement);
        verify(listener).onStop(measurement);
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

        verify(hub, times(2)).submit(Mockito.any(PerformanceTiming.class));
        verify(listener).onStart(measurement);
        verify(listener).onStop(measurement);

        assertThat(measurement.getStatus(), is(OpenMeasurement.Status.STOPPED));
        assertThat(measurement.getEventGroup(), is("name"));
        assertThat(measurement.getSubSystem(), is("group"));
        assertThat(measurement.getTo(), new GreaterThan(measurement.getFrom()));
    }

    @Test
    public void shouldChangeNameAndGroupId() {
        PendingMeasurement measurement = createMeasurement();

        measurement.setEventGroup("newName");
        measurement.setSubSystem("newGroup");

        assertThat(measurement.getEventGroup(), is("newName"));
        assertThat(measurement.getSubSystem(), is("newGroup"));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailOnAttemptToModifyGroup() {
        PendingMeasurement measurement = createMeasurement();
        measurement.stop();
        measurement.setSubSystem("A");
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailOnAttemptToModifyName() {
        PendingMeasurement measurement = createMeasurement();
        measurement.stop();
        measurement.setEventGroup("A");
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailOnAttemptToModifyParam() {
        PendingMeasurement measurement = createMeasurement();
        measurement.stop();
        measurement.setParameter("A", "B");
    }

    @Test
    public void shouldReferenceParent() {
        PendingMeasurement measurement = createMeasurement();
        PendingMeasurement child = measurement.start("subName");
        child.stop();

        verify(hub, times(3)).submit(Mockito.any(PerformanceTiming.class));
        verify(listener).onStart(measurement);
        verify(listener).onStop(child);
        verify(listener).onStop(child);
    }

    @Test
    public void shouldSupportNestedMeasurements() {
        PendingMeasurement measurement = createMeasurement();
        PendingMeasurement child = measurement.start("subName");
        PendingMeasurement subChild = child.start("subSubName");
        subChild.stop();
        child.stop();
        measurement.stop();

        verify(hub, times(6)).submit(Mockito.any(PerformanceTiming.class));
        verify(listener).onStart(measurement);
        verify(listener).onStart(child);
        verify(listener).onStart(subChild);
        verify(listener).onStop(subChild);
        verify(listener).onStop(child);
        verify(listener).onStop(measurement);
    }

    private PendingMeasurement createMeasurement() {
        PendingMeasurement measurement = new PendingMeasurement(hub, listener);
        measurement.start("name", "group");
        return measurement;
    }

    private static void pause() {
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
        }
    }

}
