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

package com.googlecode.gwtmeasure.client.delivery;

import com.google.gwt.http.client.RequestBuilder;
import com.googlecode.gwtmeasure.client.internal.DeliveryBuffer;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.HasJsonRepresentation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class HeaderInjectorTest extends Assert {

    private RequestBuilder requestBuilder;
    private HeaderInjector injector;
    private DeliveryBuffer buffer;
    private MeasurementSerializer serializer;

    @Before
    public void setUp() {
        buffer = mock(DeliveryBuffer.class);
        serializer = mock(MeasurementSerializer.class);
        injector = new HeaderInjector(buffer, serializer);
        requestBuilder = mock(RequestBuilder.class);
    }

    @Test
    public void testInject() throws Exception {
        boolean result = injector.inject(requestBuilder);
        verifyNoMoreInteractions(requestBuilder);
        assertThat(result, equalTo(false));
    }

    @Test
    public void testInject_OnlyEvents() throws Exception {
        when(buffer.hasTimings()).thenReturn(Boolean.TRUE);
        when(serializer.serialize((List<? extends HasJsonRepresentation>) any())).thenReturn("V");
        
        boolean result = injector.inject(requestBuilder);

        verify(requestBuilder).setHeader(Constants.HEADER_RESULT, "V");
        assertThat(result, equalTo(true));    
    }

    @Test
    public void testInject_OnlyErrors() throws Exception {
        when(buffer.hasIncidents()).thenReturn(Boolean.TRUE);
        when(serializer.serialize((List<? extends HasJsonRepresentation>) any())).thenReturn("V");

        boolean result = injector.inject(requestBuilder);

        verify(requestBuilder).setHeader(Constants.HEADER_ERRORS, "V");
        assertThat(result, equalTo(true));
    }

}
