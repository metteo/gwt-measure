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

import com.googlecode.gwtmeasure.client.internal.WindowId;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.shared.Constants;

/**
 * Public API for controlling settings and creating new GWT Measurements.
 *
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class Measurements {

    private static MeasurementHub measurementHub;
    private static WindowId windowId;
    private static int deliveryInterval = 15000; // in milliseconds
    private static String endpointUrl = "measurements";
    private static int headerLimit = 4 * 1024; // in bytes

    private Measurements() {
    }

    /**
     * Sets centralized event routing hub.
     * @param measurementHub implementation of event routing and propagation
     */
    public static void setMeasurementHub(MeasurementHub measurementHub) {
        Measurements.measurementHub = measurementHub;
    }

    /**      
     * @return reference to current Hub implementation
     */
    public static MeasurementHub getMeasurementHub() {
        return measurementHub;
    }

    /**
     * Starts recording of new measurement interval.
     *
     * @param eventGroup eventGroup of the measured event
     * @return measurement object
     */
    public static PendingMeasurement start(String eventGroup) {
        return start(eventGroup, Constants.SUB_SYSTEM_DEFAULT);
    }

    /**
     * Starts recording of new measurement interval.
     * 
     * @param eventGroup eventGroup of the measured event
     * @param subSystem name of the measured event
     * @return measurement object
     * @see com.googlecode.gwtmeasure.client.PendingMeasurement
     */
    public static PendingMeasurement start(String eventGroup, String subSystem) {
        return new PendingMeasurement(eventGroup, subSystem, measurementHub);
    }

    /**
     * Sets uniquely identifying browser tab id for single-session/multi-tab measurements.
     * @param windowId unique window id
     */
    public static void setWindowId(WindowId windowId) {
        Measurements.windowId = windowId;
    }

    /**
     * @return current window unique window id
     */
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

    /**
     * Sets piggyback measurement delivery interval.
     * @param millis time period in millis
     */
    public static void setDeliveryInterval(int millis) {
        Measurements.deliveryInterval = deliveryInterval;
    }

    /**
     * Provides the maximum size of HTTP header sent by the framework.
     * If the limit is reached header will be split in several portions.
     *
     * Jetty has 10KB of header content limit by default.
     * @return header limit in bytes
     */
    public static int getHeaderLimit() {
        return headerLimit;
    }

    /**
     * Sets the maximum single HTTP header content size.
     * One character in JavaScript corresponds to two bytes according to ECMA Script spec.
     * @param headerLimit size of header in bytes.
     */
    public static void setHeaderLimit(int headerLimit) {
        Measurements.headerLimit = headerLimit;
    }

    /**
     * Provides relative location of measurement delivery endpoint (e.g. servlet)
     * @return defaults to "measurements"
     */
    public static String getEndpointUrl() {
        return endpointUrl;
    }

    /**
     * Sets relative endpoint url for sending standalone measurements after user's inactivity
     * @param endpointUrl relative url
     */
    public static void setEndpointUrl(String endpointUrl) {
        Measurements.endpointUrl = endpointUrl;
    }

}
