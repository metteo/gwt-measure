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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class PendingMeasurementTest extends Assert {

    private MeasurementHub hub;

    @Before
    public void setUp() {
        hub = mock(MeasurementHub.class);
    }

    @Test
    public void shouldRegisterInHub() {
        PendingMeasurement measurement = new PendingMeasurement("name", "group", hub);
        measurement.stop();

        verify(hub).submit(measurement);
    }

    @Test
    public void shouldDiscard() {
        PendingMeasurement measurement = new PendingMeasurement("name", "group", hub);
        measurement.discard();
        measurement.stop();

        verifyZeroInteractions(hub);
    }

    @Test
    public void shouldAvoidDoubleStops() {
        PendingMeasurement measurement = new PendingMeasurement("name", "group", hub);
        measurement.stop();
        measurement.stop();

        verify(hub).submit(measurement);
        verifyNoMoreInteractions(hub);
    }

    @Test
    public void shouldSetParameter() {
        PendingMeasurement measurement = new PendingMeasurement("name", "group", hub);
        assertThat(measurement.getParameter("A"), nullValue());

        measurement.setParameter("A", "V");

        assertThat(measurement.getParameter("A"), equalTo("V"));
    }


}
