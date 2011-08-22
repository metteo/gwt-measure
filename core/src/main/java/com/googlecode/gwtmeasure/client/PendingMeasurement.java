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

import com.googlecode.gwtmeasure.client.internal.MeasurementToEvent;
import com.googlecode.gwtmeasure.client.internal.TimeUtils;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.client.spi.MeasurementListener;
import com.googlecode.gwtmeasure.shared.OpenMeasurement;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class PendingMeasurement implements OpenMeasurement {

    private static final MeasurementToEvent CONVERTER = new MeasurementToEvent();

    private final MeasurementHub measurementHub;
    private final MeasurementListener listener;

    private String eventGroup;
    private String subSystem;

    private Status status = Status.CREATED;

    private long from;
    private long to;

    private final Map<String, String> parameters = new HashMap<String, String>();

    // TODO Make package level
    public PendingMeasurement(MeasurementHub measurementHub,
                              MeasurementListener listener) {
        this.from = TimeUtils.current();
        this.measurementHub = measurementHub;
        this.listener = listener;
    }

    public void start(String eventGroup, String subSystem) {
        this.eventGroup = eventGroup;
        this.subSystem = subSystem;

        this.status = Status.STARTED;

        listener.onStart(this);

        PerformanceTiming timing = CONVERTER.createStartTiming(this);
        measurementHub.submit(timing);
    }

    /**
     * Starts nested measurement within the same group.
     *
     * @param subSystem name
     * @return nested measurement object
     */
    public PendingMeasurement start(String subSystem) {
        PendingMeasurement subMeasurement = new PendingMeasurement(measurementHub, listener);
        subMeasurement.start(eventGroup, subSystem);
        return subMeasurement;
    }

    /**
     * Stops this measurement and propagates start and stop events to event queue.
     */
    public void stop() {
        if (Status.STARTED.equals(this.status)) {
            this.to = TimeUtils.current();

            listener.onStop(this);

            PerformanceTiming timing = CONVERTER.createEndTiming(this);
            measurementHub.submit(timing);
        }
        this.status = Status.STOPPED;
    }

    /**
     * Discards this measurement. Further calls to that will be ignored.
     */
    public void discard() {
        this.status = Status.DISCARDED;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public String getEventGroup() {
        return eventGroup;
    }

    public String getSubSystem() {
        return subSystem;
    }

    public Status getStatus() {
        return this.status;
    }

    /**
     * Sets context parameter to be attached resulting events
     *
     * @param name  parameter name
     * @param value parameter value
     */
    public void setParameter(String name, String value) {
        checkIfValid();
        parameters.put(name, value);
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Set<String> getParameterNames() {
        return parameters.keySet();
    }

    public void setEventGroup(String eventGroup) {
        checkIfValid();
        this.eventGroup = eventGroup;
    }

    public void setSubSystem(String subSystem) {
        checkIfValid();
        this.subSystem = subSystem;
    }

    private void checkIfValid() {
        if (!Status.STARTED.equals(this.status)) {
            throw new IllegalStateException("Measurement already invalidated." +
                    " Set properties before calling stop() or discard().");
        }
    }

}
