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
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class ConfigurationTest {

    @Test
    public void testSetEndpointUrl() throws Exception {
        Configuration.setEndpointUrl("x");
        assertThat(Configuration.getEndpointUrl(), equalTo("x"));
    }

    @Test
    public void testSetHeaderLimit() throws Exception {
        Configuration.setHeaderLimit(1024);
        assertThat(Configuration.getHeaderLimit(), equalTo(1024));
    }

    @Test
    public void testSetMeasurementListener() throws Exception {
        MeasurementListener listener = mock(MeasurementListener.class);
        Configuration.setMeasurementListener(listener);
        assertThat(Configuration.getMeasurementListener(), sameInstance(listener));
    }

    @Test
    public void testSetMeasurementHub() throws Exception {
        MeasurementHub measurementHub = mock(MeasurementHub.class);
        Configuration.setMeasurementHub(measurementHub);
        assertThat(Configuration.getMeasurementHub(), sameInstance(measurementHub));
    }

    @Test
    public void testSetWindowId() throws Exception {
        WindowId id = new WindowId();
        Configuration.setWindowId(id);
        assertThat(Configuration.getWindowId(), sameInstance(id));
    }

}
