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

import com.googlecode.gwtmeasure.shared.PerformanceMetrics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MetricsDecoderTest extends Assert {
    
    private String jsonValue;
    private MetricsDecoder decoder;

    @Before
    public void setUp() {
        jsonValue = "{\"moduleName\":\"moduleName\",\n" +
                "\"subSystem\":\"subSystem\",\n" +
                "\"eventGroup\":\"eventGroup\",\n" +
                "\"millis\":100,\n" +
                "\"type\":\"type\",\n" +
                "\"parameters\":{\n" +
                "  \"param\":\"value\"\n" +
                "}}";
        decoder = new MetricsDecoder();
    }

    @Test
    public void testDecode() throws Exception {
        PerformanceMetrics metrics = decoder.decode(jsonValue);
        
        assertThat(metrics.getModuleName(), equalTo("moduleName"));
        assertThat(metrics.getSubSystem(), equalTo("subSystem"));
        assertThat(metrics.getEventGroup(), equalTo("eventGroup"));
        assertThat(metrics.getMillis(), equalTo(100L));
        assertThat(metrics.getType(), equalTo("type"));
        assertThat(metrics.getParameter("param"), equalTo("value"));
    }

}
