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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.googlecode.gwtmeasure.client.delivery.DebugPanelChannel;
import com.googlecode.gwtmeasure.client.delivery.RemoteServerChannel;
import com.googlecode.gwtmeasure.client.delivery.StandaloneDelivery;
import com.googlecode.gwtmeasure.client.exception.WrappingExceptionHandler;
import com.googlecode.gwtmeasure.client.internal.JavaScriptEventObject;
import com.googlecode.gwtmeasure.client.internal.WindowId;
import com.googlecode.gwtmeasure.client.internal.WindowUnloadHandler;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class GWTMeasureEntryPoint implements EntryPoint, CloseHandler<Window> {

    private static final MeasurementHub hub = GWT.create(MeasurementHub.class);
    private static final int TIMER_INTERVAL = 15000; // 15 seconds

    public void onModuleLoad() {
        Measurements.setWindowId(new WindowId());
        Measurements.setMeasurementHub(hub);

        registerChannels();
        registerResourceStartEvent();

        // Measurement hooks
        hookGwtStatsFunctionAndSink();
        hookWindowCloseHandler();
        hookToWindowUnload();
        hookTimer();

        // Exception handling hooks
        hookExceptionHandler();
    }

    private void registerChannels() {
        PerformanceEventHandler remoteHandler = GWT.create(RemoteServerChannel.class);
        hub.addHandler(remoteHandler);
        
        PerformanceEventHandler debugPanelHandler = GWT.create(DebugPanelChannel.class);
        hub.addHandler(debugPanelHandler);
    }

    private void registerResourceStartEvent() {
        String resourceStart = Cookies.getCookie(Constants.COOKIE_RESOURCE_LOAD_START);
        if (resourceStart == null) {
            return;
        }
        Cookies.removeCookie(Constants.COOKIE_RESOURCE_LOAD_START);
        
        PerformanceTiming.Builder builder = new PerformanceTiming.Builder();
        builder
                .setSubSystem(Constants.SUB_SYSTEM_RESOURCES)
                .setEventGroup(Constants.GRP_BOOTSTRAP)
                .setMillis(Long.parseLong(resourceStart));

        hub.submit(builder.create());
    }

    private void hookExceptionHandler() {
        GWT.UncaughtExceptionHandler exceptionHandler = GWT.getUncaughtExceptionHandler();
        GWT.setUncaughtExceptionHandler(new WrappingExceptionHandler(exceptionHandler));
    }

    // TODO Exponential backoff
    private void hookTimer() {
        MeasurementDeliveryTimer timer = new MeasurementDeliveryTimer();
        timer.scheduleRepeating(TIMER_INTERVAL);
    }

    private void hookToWindowUnload() {
        WindowUnloadHandler.attach(new WindowUnloadCommand());
    }

    private void hookWindowCloseHandler() {
        Window.addCloseHandler(this);
    }

    /**
     * Target for JSNI invocation
     * @param event JSON object representing performance event
     */
    public static void handleEvent(JavaScriptEventObject event) {
        PerformanceTiming timing = event.asJavaObject();
        hub.submit(timing);
    }

    private native void hookGwtStatsFunctionAndSink() /*-{
        $wnd.handleEvent = @com.googlecode.gwtmeasure.client.GWTMeasureEntryPoint::handleEvent(Lcom/googlecode/gwtmeasure/client/internal/JavaScriptEventObject;);
        $wnd.__gwtStatsEvent = function(event) {
            $wnd.sinkGwtEvents();
            $wnd.handleEvent(event);
            return true;
        };
        $wnd.sinkGwtEvents();
    }-*/;

    public void onClose(CloseEvent<Window> windowCloseEvent) {
        StandaloneDelivery.instance().deliver();
    }

    private static class WindowUnloadCommand implements Command {

        public void execute() {
            StandaloneDelivery.instance().deliver();
        }
        
    }

    private static class MeasurementDeliveryTimer extends Timer {

        @Override
        public void run() {
            StandaloneDelivery.instance().deliver();
        }

    }

}
