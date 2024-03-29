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
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.googlecode.gwtmeasure.client.delivery.DebugPanelChannel;
import com.googlecode.gwtmeasure.client.delivery.LoggingChannel;
import com.googlecode.gwtmeasure.client.delivery.RemoteServerChannel;
import com.googlecode.gwtmeasure.client.delivery.StandaloneDelivery;
import com.googlecode.gwtmeasure.client.exception.WrappingExceptionHandler;
import com.googlecode.gwtmeasure.client.internal.ClientMeasurementHub;
import com.googlecode.gwtmeasure.client.internal.JavaScriptEventObject;
import com.googlecode.gwtmeasure.client.internal.VoidEventHandler;
import com.googlecode.gwtmeasure.client.internal.WindowId;
import com.googlecode.gwtmeasure.client.internal.WindowUnloadHandler;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.client.spi.MeasurementListener;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.Measurements;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

/**
 * Bootstrap engine, which registers measurement hooks in GWT.
 *
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class MeasurementEngine implements EntryPoint, CloseHandler<Window> {

    private static final ClientMeasurementHub hub = GWT.create(MeasurementHub.class);

    private final EventBus eventBus = new SimpleEventBus();

    public void onModuleLoad() {
        hub.setEventBus(eventBus);

        Configuration.setWindowId(new WindowId());
        Configuration.setMeasurementHub(hub);

        attachApiImpl();

        registerDeliveryChannels();
        registerResourceStartEvent();

        // Measurement hooks
        hookGwtStatsFunctionAndSink();
        hookWindowCloseHandler();
        hookToWindowUnload();
        hookTimer();

        // Exception handling hooks
        hookExceptionHandler();
    }

    private void attachApiImpl() {
        Measurements.setClientImpl(new Measurements.Impl() {
            public PendingMeasurement run(String eventGroup, String subSystem) {
                MeasurementHub hub = Configuration.getMeasurementHub();
                MeasurementListener listener = Configuration.getMeasurementListener();

                PendingMeasurement measurement = new PendingMeasurement(hub, listener);
                measurement.start(eventGroup, subSystem);
                return measurement;
            }
        });
    }

    private HandlerRegistration addHandler(PerformanceEventHandler handler) {
        return eventBus.addHandler(PerformanceEvent.TYPE, handler);
    }

    private void registerDeliveryChannels() {
        PerformanceEventHandler remoteHandler = GWT.create(RemoteServerChannel.class);
        registerIfNotNull(remoteHandler);

        PerformanceEventHandler debugPanelHandler = GWT.create(DebugPanelChannel.class);
        registerIfNotNull(debugPanelHandler);

        if (LogConfiguration.loggingIsEnabled()) {
            PerformanceEventHandler loggingHandler = GWT.create(LoggingChannel.class);
            registerIfNotNull(loggingHandler);
        }
    }

    private void registerIfNotNull(PerformanceEventHandler handler) {
        if (!(handler instanceof VoidEventHandler)) {
            addHandler(handler);
        }
    }

    private void registerResourceStartEvent() {
        String resourceStart = Cookies.getCookie(Constants.COOKIE_RESOURCE_LOAD_START);
        if (resourceStart == null) {
            return;
        }
        Cookies.removeCookie(Constants.COOKIE_RESOURCE_LOAD_START);

        PerformanceTiming.Builder builder = new PerformanceTiming.Builder();
        builder
                .setSubSystem(Constants.SUB_SYSTEM_STARTUP)
                .setEventGroup(Constants.GRP_BOOTSTRAP)
                .setType(Constants.TYPE_PAGE_LOADED)
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
        int interval = Configuration.getDeliveryInterval();
        timer.scheduleRepeating(interval);
    }

    private void hookToWindowUnload() {
        WindowUnloadHandler.attach(new WindowUnloadCommand());
    }

    private void hookWindowCloseHandler() {
        Window.addCloseHandler(this);
    }

    /**
     * Target for JSNI invocation
     *
     * @param event JSON object representing performance event
     */
    public static void handleEvent(JavaScriptEventObject event) {
        PerformanceTiming timing = event.asJavaObject();
        hub.submit(timing);
    }

    private native void hookGwtStatsFunctionAndSink() /*-{
        $wnd.handleEvent = @com.googlecode.gwtmeasure.client.MeasurementEngine::handleEvent(Lcom/googlecode/gwtmeasure/client/internal/JavaScriptEventObject;);
        $wnd.__gwtStatsEvent = function(event) {
            $wnd.sinkGwtEvents();
            $wnd.handleEvent(event);
            return true;
        };
        $wnd.sinkGwtEvents();
    }-*/;

    // Trigger measurement delivery on browser closing
    public void onClose(CloseEvent<Window> windowCloseEvent) {
        StandaloneDelivery.instance().deliver();
    }

    // Trigger measurement delivery on navigation
    private static class WindowUnloadCommand implements Command {

        public void execute() {
            StandaloneDelivery.instance().deliver();
        }

    }

    // Time-based trigger for measurement delivery
    private static class MeasurementDeliveryTimer extends Timer {

        @Override
        public void run() {
            StandaloneDelivery.instance().deliver();
        }

    }

}
