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

import com.google.code.gwtmeasure.client.delivery.DebugPanelDelivery;
import com.google.code.gwtmeasure.client.delivery.RpcPiggibackDelivery;
import com.google.code.gwtmeasure.client.exception.WrappingExceptionHandler;
import com.google.code.gwtmeasure.client.internal.JavaScriptEventObject;
import com.google.code.gwtmeasure.client.spi.MeasurementHub;
import com.google.code.gwtmeasure.shared.PerformanceMetrics;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class GWTMeasureEntryPoint implements EntryPoint {

    private static final MeasurementHub hub = GWT.create(MeasurementHub.class);

    public void onModuleLoad() {
        Measurements.setDeliveryChannel(hub);
        
        hub.addHandler(new RpcPiggibackDelivery());
        hub.addHandler(new DebugPanelDelivery());

        hookGwtStatsFunctionAndSink();

        GWT.UncaughtExceptionHandler exceptionHandler = GWT.getUncaughtExceptionHandler();
        GWT.setUncaughtExceptionHandler(new WrappingExceptionHandler(exceptionHandler));
    }

    public static void handleEvent(JavaScriptEventObject event) {
        PerformanceMetrics metrics = event.asJavaObject();
        hub.submit(metrics);
    }

    private native void hookGwtStatsFunctionAndSink() /*-{
        $wnd.handleEvent = @com.google.code.gwtmeasure.client.GWTMeasureEntryPoint::handleEvent(Lcom/google/code/gwtmeasure/client/internal/JavaScriptEventObject;);
        $wnd.__gwtStatsEvent = function(event) {
            $wnd.sinkGwtEvents();
            $wnd.handleEvent(event);
            return true;
        };
        $wnd.sinkGwtEvents();
    }-*/;

}
