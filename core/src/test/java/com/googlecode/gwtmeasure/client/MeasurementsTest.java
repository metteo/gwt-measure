/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import com.googlecode.gwtmeasure.client.spi.MeasurementListener;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.Measurements;
import com.googlecode.gwtmeasure.shared.OpenMeasurement;
import org.hamcrest.core.IsSame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class MeasurementsTest extends Assert {

    private Measurements.Impl impl;

    @Before
    public void setUp() {
        impl = mock(Measurements.Impl.class);
        Measurements.setServerImpl(impl);
    }

    @Test
    public void testStart() throws Exception {
        OpenMeasurement openMeasurement = mock(OpenMeasurement.class);
        when(impl.run("A", Constants.SUB_SYSTEM_DEFAULT)).thenReturn(openMeasurement);

        OpenMeasurement measurement = Measurements.start("A");

        assertThat(measurement, sameInstance(openMeasurement));
    }

}
