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

package com.googlecode.gwtmeasure.server;

import com.googlecode.gwtmeasure.shared.PerformanceTiming;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MetricsDecoderTest extends Assert {
    
    private String jsonValue;
    private JsonDecoder decoder;

    @Before
    public void setUp() {
        jsonValue = "[{\"moduleName\":\"moduleName\",\n" +
                "\"subSystem\":\"subSystem\",\n" +
                "\"eventGroup\":\"eventGroup\",\n" +
                "\"millis\":100,\n" +
                "\"type\":\"type\",\n" +
                "\"parameters\":{\n" +
                "  \"param\":\"value\"\n" +
                "}}]";
        decoder = new JsonDecoder();
    }

    @Test
    public void testDecode() throws Exception {
        Collection<PerformanceTiming> timings = decoder.decodeTimings(jsonValue);

        assertThat(timings.size(), equalTo(1));

        PerformanceTiming timing = timings.iterator().next();

        assertThat(timing.getModuleName(), equalTo("moduleName"));
        assertThat(timing.getSubSystem(), equalTo("subSystem"));
        assertThat(timing.getEventGroup(), equalTo("eventGroup"));
        assertThat(timing.getMillis(), equalTo(100L));
        assertThat(timing.getType(), equalTo("type"));
        assertThat(timing.getParameter("param"), equalTo("value"));
    }

}
