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

package com.google.code.gwtmeasure.client;

import com.google.code.gwtmeasure.client.internal.TimeUtils;
import com.google.code.gwtmeasure.client.spi.MeasurementControl;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class PendingMeasurement {

    private long from;
    private long to;
    private String name;
    private String group;
    private MeasurementControl measurementControl;
    private boolean discarded;

    {
        this.from = TimeUtils.current();
    }

    public PendingMeasurement(String name, String group, MeasurementControl measurementControl) {
        this.name = name;
        this.group = group;
        this.measurementControl = measurementControl;
    }

    public void stop() {
        if (!discarded) {
            this.to = TimeUtils.current();
            measurementControl.submit(this);
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
