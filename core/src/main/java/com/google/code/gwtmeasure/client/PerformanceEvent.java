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

import com.google.code.gwtmeasure.shared.PerformanceMetrics;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class PerformanceEvent extends GwtEvent<PerformanceEventHandler> {
    
    public static final Type<PerformanceEventHandler> TYPE = new Type<PerformanceEventHandler>();

    private final PerformanceMetrics metrics;

    public PerformanceEvent(PerformanceMetrics metrics) {
        this.metrics = metrics;
    }

    @Override
    public Type<PerformanceEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PerformanceEventHandler handler) {
        handler.onPerformanceEvent(this);
    }

    public PerformanceMetrics getMetrics() {
        return metrics;
    }

}
