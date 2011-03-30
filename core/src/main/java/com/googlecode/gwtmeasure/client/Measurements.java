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
import com.googlecode.gwtmeasure.client.internal.WindowId;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.shared.Constants;

/**
 * Public API for GWT Measurements.
 *
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class Measurements {

    private static MeasurementHub measurementHub;
    private static WindowId windowId;
    private static int deliveryInterval = 15000;
    private static String endpointUrl = "measurements";

    private Measurements() {
    }

    public static void setMeasurementHub(MeasurementHub measurementHub) {
        Measurements.measurementHub = measurementHub;
    }

    public static MeasurementHub getMeasurementHub() {
        return measurementHub;
    }

    public static PendingMeasurement start(String name) {
        return start(name, Constants.SUB_SYSTEM_DEFAULT);
    }

    /**
     * Starts recording of new measurement interval.
     * 
     * @param name name of the measured event
     * @param group name of the event group
     * @return measurement object
     * @see com.googlecode.gwtmeasure.client.PendingMeasurement
     */
    public static PendingMeasurement start(String name, String group) {
        return new PendingMeasurement(name, group, measurementHub);
    }

    public static void setWindowId(WindowId windowId) {
        Measurements.windowId = windowId;
    }

    public static WindowId getWindowId() {
        return windowId;
    }

    /**
     *
     * @return milliseconds, defaults to 15 seconds.
     */
    public static int getDeliveryInterval() {
        return deliveryInterval;
    }

    public static void setDeliveryInterval(int millis) {
        Measurements.deliveryInterval = deliveryInterval;
    }

    /**
     * Provides relative location of measurement delivery endpoint (e.g. servlet)
     * @return defaults to "measurements"
     */
    public static String getEndpointUrl() {
        return endpointUrl;
    }

    public static void setEndpointUrl(String endpointUrl) {
        Measurements.endpointUrl = endpointUrl;
    }

}
