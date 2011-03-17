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

import com.googlecode.gwtmeasure.client.internal.TimeUtils;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class PendingMeasurement {

    private long from;
    private long to;
    private String name;
    private String group;
    private MeasurementHub measurementHub;
    private boolean discarded;
    private final Map<String, String> parameters = new HashMap<String, String>();

    {
        this.from = TimeUtils.current();
    }

    public PendingMeasurement(String name, String group, MeasurementHub measurementHub) {
        this.name = name;
        this.group = group;
        this.measurementHub = measurementHub;
    }

    public void stop() {
        if (!discarded) {
            this.to = TimeUtils.current();
            measurementHub.submit(this);
        }
    }

    public void discard() {
        this.discarded = true;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setParameter(String name, String value) {
        parameters.put(name, value);
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Set<String> getParameterNames() {
        return parameters.keySet();
    }

    @Override
    public String toString() {
        return "PendingMeasurement{" +
                "from=" + from +
                ", to=" + to +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", discarded=" + discarded +
                '}';
    }

}
