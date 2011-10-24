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

package com.googlecode.gwtmeasure.shared;

import com.google.gwt.core.client.GWT;
import com.googlecode.gwtmeasure.client.PendingMeasurement;
import com.googlecode.gwtmeasure.client.internal.VoidHub;
import com.googlecode.gwtmeasure.client.internal.VoidMeasurementListener;

/**
 * Public API for creating new GWT Measurements.
 *
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class Measurements {

    private static Impl clientImpl = new VoidImpl();
    private static Impl serverImpl = new VoidImpl();

    // TODO Package level
    public static interface Impl {
        OpenMeasurement run(String eventGroup, String subSystem);
    }

    private static final class VoidImpl implements Impl {
        public OpenMeasurement run(String eventGroup, String subSystem) {
            VoidHub hub = new VoidHub();
            VoidMeasurementListener listener = new VoidMeasurementListener();
            PendingMeasurement measurement = new PendingMeasurement(hub, listener);
            measurement.start(eventGroup, subSystem);
            return measurement;
        }
    }

    private Measurements() {
    }

    // TODO Package level
    public static void setClientImpl(Impl clientImpl) {
        Measurements.clientImpl = clientImpl;
    }

    // TODO Package level
    public static void setServerImpl(Impl serverImpl) {
        Measurements.serverImpl = serverImpl;
    }

    /**
     * Starts recording of new measurement interval.
     *
     * @param eventGroup eventGroup of the measured event
     * @return measurement object
     */
    public static OpenMeasurement start(String eventGroup) {
        return start(eventGroup, Constants.SUB_SYSTEM_DEFAULT);
    }

    /**
     * Starts recording of new measurement interval.
     *
     * @param eventGroup eventGroup of the measured event
     * @param subSystem  name of the measured event
     * @return measurement object
     * @see com.googlecode.gwtmeasure.client.PendingMeasurement
     */
    public static OpenMeasurement start(String eventGroup, String subSystem) {
        if (GWT.isClient()) {
            return clientImpl.run(eventGroup, subSystem);
        } else {
            return serverImpl.run(eventGroup, subSystem);
        }
    }

}
