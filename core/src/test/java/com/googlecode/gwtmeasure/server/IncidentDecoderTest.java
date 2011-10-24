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

package com.googlecode.gwtmeasure.server;

import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class IncidentDecoderTest extends Assert {

    String jsonValue;
    JsonDecoder decoder;

    @Before
    public void setUp() {
        jsonValue = "[{'timestamp':123, 'text':'text', 'message':'message', 'strongName':'name', " +
                "'url':'url', 'stackTrace':['a','b']}]";
        jsonValue = jsonValue.replaceAll("'", "\"");

        decoder = new JsonDecoder();
    }

    @Test
    public void testDecode() throws Exception {
        Collection<IncidentReport> reports = decoder.decodeErrors(jsonValue);

        assertThat(reports.size(), equalTo(1));

        IncidentReport report = reports.iterator().next();

        assertThat(report.getMessage(), equalTo("message"));
        assertThat(report.getStrongName(), equalTo("name"));
        assertThat(report.getText(), equalTo("text"));
        assertThat(report.getUrl(), equalTo("url"));
        assertThat(report.getTimestamp(), equalTo(123L));
        assertThat(report.getStackTrace(), equalTo(new String[] {"a", "b"}));
    }

}
