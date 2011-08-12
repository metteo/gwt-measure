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

import com.googlecode.gwtmeasure.client.internal.MeasurementHubAdapter;
import com.googlecode.gwtmeasure.client.internal.TimeUtils;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.client.spi.MeasurementListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class PendingMeasurement {

    private MeasurementHubAdapter hubAdapter;
    private MeasurementListener listener;

    private String eventGroup;
    private String subSystem;

    private boolean discarded;
    private boolean stopped;

    private long from;
    private long to;    

    private final Map<String, String> parameters = new HashMap<String, String>();

    private final List<PendingMeasurement> children = new ArrayList<PendingMeasurement>();
    private PendingMeasurement parent;

    {
        this.from = TimeUtils.current();
    }

    public PendingMeasurement(String eventGroup,
                              String subSystem,
                              MeasurementHub measurementHub,
                              MeasurementListener listener) {
        this(eventGroup, subSystem, new MeasurementHubAdapter(measurementHub), listener);
    }

    public PendingMeasurement(String eventGroup,
                              String subSystem,
                              MeasurementHubAdapter hubAdapter,
                              MeasurementListener listener) {
        this.eventGroup = eventGroup;
        this.subSystem = subSystem;
        this.hubAdapter = hubAdapter;
        this.listener = listener;
    }

    public PendingMeasurement start(String subSystem) {
        PendingMeasurement subMeasurements = new PendingMeasurement(eventGroup, subSystem, hubAdapter, listener);
        subMeasurements.setParent(this);
        return subMeasurements;
    }

    /**
     * Stops this measurement and propagates start and stop events to event queue.
     */
    public void stop() {
        if (isActive()) {
            this.to = TimeUtils.current();

            if (parent != null && parent.isActive()) {
                parent.getChildren().add(this);
            } else { // root measurement
                listener.onSubmit(this);
                hubAdapter.submit(this);
            }
        }
        stopped = true;
    }

    private boolean isActive() {
        return !discarded && !stopped;
    }

    PendingMeasurement getParent() {
        return parent;
    }

    void setParent(PendingMeasurement measurement) {
        this.parent = measurement;
    }

    public List<PendingMeasurement> getChildren() {
        return children;
    }

    /**
     * Discards this measurement. Further calls to that will be ignored.
     */
    public void discard() {
        this.discarded = true;
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

    public boolean isDiscarded() {
        return discarded;
    }

    public boolean isStopped() {
        return stopped;
    }

    /**
     * Sets context parameter to be attached resulting events
     * @param name parameter name
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
        if (discarded || stopped) {
            throw new IllegalStateException("Measurement already invalidated." +
                    " Set properties before calling stop() or discard().");
        }
    }

}
