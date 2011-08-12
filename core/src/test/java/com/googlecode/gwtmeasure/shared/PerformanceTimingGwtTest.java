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
import com.googlecode.gwtmeasure.client.delivery.JsonEncoderImpl;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class PerformanceTimingGwtTest extends GWTTestCase {

    private PerformanceTiming timing;

    @Override
    protected void gwtSetUp() throws Exception {
        timing = new PerformanceTiming();
    }

    @Override
    public String getModuleName() {
        return "com.googlecode.gwtmeasure.GWTMeasure";
    }

    public void testJsonEncode() throws Exception {
        timing.setEventGroup("eventGroup");
        timing.setModuleName("moduleName");
        timing.setMillis(1);
        timing.setParameter("a", "b");
        timing.setSubSystem("subSystem");
        timing.setType("type");

        String json = timing.jsonEncode(new JsonEncoderImpl());

        String expected = "{'moduleName':'moduleName', 'subSystem':'subSystem', 'eventGroup':'eventGroup', 'millis':1, 'type':'type', 'parameters':{'a':'b'}}";

        assertEquals(expected.replaceAll("'", "\""), json);
    }

}
