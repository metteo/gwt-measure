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

import com.google.code.gwtmeasure.client.spi.MeasurementControl;
import com.google.code.gwtmeasure.shared.MetricEvent;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class GWTMeasureEntryPoint implements EntryPoint {

    private static final MeasurementControl control = GWT.create(MeasurementControl.class);

    public void onModuleLoad() {
        hookGwtStatsFunctionAndSink();
    }

    public static void handleEvent(String group,
                                   String moduleName,
                                   String subSystem,
                                   String type,
                                   String millis,
                                   String sessionId,
                                   String method,
                                   String bytes) {
        MetricEvent.Builder builder = new MetricEvent.Builder()
                .setEventGroup(group)
                .setModuleName(moduleName)
                .setSubSystem(subSystem)
                .setType(type)
                .setSessionId(sessionId)
                .setMethod(method);

        if (millis != null && !"undefined".equals(bytes)) {
            builder.setMillis(Long.parseLong(millis));
        }
        if (bytes != null && !"undefined".equals(bytes)) {
            builder.setBytes(Long.parseLong(bytes));
        }

        MetricEvent metricEvent = builder.create();

        control.submit(metricEvent);
    }

    private native void hookGwtStatsFunctionAndSink() /*-{
        $wnd.handleEvent = @com.google.code.gwtmeasure.client.GWTMeasureEntryPoint::handleEvent(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;);
        $wnd.__gwtStatsEvent = function(event) {
            $wnd.sinkGwtEvents();
            $wnd.handleEvent(
                    "" + event.evtGroup,
                    "" + event.moduleName,
                    "" + event.subSystem,
                    "" + event.type,
                    "" + event.millis,
                    "" + event.sessionId,
                    "" + event.method,
                    "" + event.bytes);
            return true;
        };
        $wnd.sinkGwtEvents();
    }-*/;

}
