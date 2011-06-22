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

package com.googlecode.gwtmeasure.shared;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class IncidentReportGwtTest extends GWTTestCase {

    private IncidentReport report;

    @Override
    protected void gwtSetUp() throws Exception {
        report = new IncidentReport();
    }

    @Override
    public String getModuleName() {
        return "com.googlecode.gwtmeasure.GWTMeasure";
    }

    public void testJsonEncode() throws Exception {
        report.setText("text");
        report.setTimestamp(123);
        report.setStrongName("name");
        report.setUrl("url");
        report.setMessage("message");
        report.setStackTrace(new String[] {"a", "b"});

        String json = report.jsonEncode();

        String expected = "{'timestamp':123, 'text':'text', 'message':'message', 'strongName':'name', " +
                "'url':'url', 'stackTrace':['a','b']}";

        assertEquals(expected.replaceAll("'", "\""), json);
    }

}
