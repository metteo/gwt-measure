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

import com.googlecode.gwtmeasure.client.internal.WindowId;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.shared.Constants;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class MeasurementsTest extends Assert {

    @Test
    public void testSetMeasurementHub() throws Exception {
        MeasurementHub measurementHub = mock(MeasurementHub.class);
        Measurements.setMeasurementHub(measurementHub);
        assertThat(Measurements.getMeasurementHub(), sameInstance(measurementHub));
    }

    @Test
    public void testStart() throws Exception {
        PendingMeasurement measurement = Measurements.start("A");
        assertThat(measurement.getEventGroup(), equalTo("A"));
        assertThat(measurement.getSubSystem(), equalTo(Constants.SUB_SYSTEM_DEFAULT));
    }

    @Test
    public void testStartGroup() throws Exception {
        PendingMeasurement measurement = Measurements.start("A", "B");
        assertThat(measurement.getEventGroup(), equalTo("A"));
        assertThat(measurement.getSubSystem(), equalTo("B"));
    }

    @Test
    public void testSetWindowId() throws Exception {
        WindowId id = new WindowId();
        Measurements.setWindowId(id);
        assertThat(Measurements.getWindowId(), sameInstance(id));
    }

    @Test
    public void testSetEndpointUrl() throws Exception {
        Measurements.setEndpointUrl("x");
        assertThat(Measurements.getEndpointUrl(), equalTo("x"));
    }

    @Test
    public void testSetHeaderLimit() throws Exception {
        Measurements.setHeaderLimit(1024);
        assertThat(Measurements.getHeaderLimit(), equalTo(1024));
    }

}
