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

import com.googlecode.gwtmeasure.shared.HasJsonRepresentation;
import com.googlecode.gwtmeasure.shared.JsonEncoder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class MeasurementSerializerTest extends Assert {

    private MeasurementSerializer serializer;

    @Before
    public void setUp() {
        serializer = new MeasurementSerializer();
    }

    @Test
    public void shouldSerialize() throws Exception {
        ArrayList<HasJsonRepresentation> jsonList = new ArrayList<HasJsonRepresentation>();
        HasJsonRepresentation jsonRepresentation = mock(HasJsonRepresentation.class);
        when(jsonRepresentation.jsonEncode((JsonEncoder) Matchers.any())).thenReturn("X");

        jsonList.add(jsonRepresentation);
        jsonList.add(jsonRepresentation);
        jsonList.add(jsonRepresentation);

        String result = serializer.serialize(jsonList, 10 * 1024);

        assertThat(result, equalTo("[X,X,X]"));
    }

    @Test
    public void shouldConsiderSizeLimt() throws Exception {
        ArrayList<HasJsonRepresentation> jsonList = new ArrayList<HasJsonRepresentation>();

        HasJsonRepresentation first = prepareJsonObject("value1");
        jsonList.add(first);
        jsonList.add(prepareJsonObject("value2"));
        jsonList.add(prepareJsonObject("value3"));
        jsonList.add(prepareJsonObject("value4"));

        String result = serializer.serialize(jsonList, 10 * 2);

        assertThat(result, equalTo("[value1]"));

        assertThat(jsonList.size(), equalTo(3));
        assertThat(jsonList.contains(first), equalTo(false));
    }

    @Test
    public void shouldHaveNoLimits() {
        ArrayList<HasJsonRepresentation> jsonList = new ArrayList<HasJsonRepresentation>();
        jsonList.add(prepareJsonObject("value1"));

        String result = serializer.serialize(jsonList, -1);

        assertThat(result, equalTo("[value1]"));
    }

    private HasJsonRepresentation prepareJsonObject(String value) {
        HasJsonRepresentation jsonRepresentation = mock(HasJsonRepresentation.class);
        when(jsonRepresentation.jsonEncode((JsonEncoder) Matchers.any())).thenReturn(value);
        return jsonRepresentation;
    }

}
