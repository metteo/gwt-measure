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

import com.google.code.gwtmeasure.shared.MetricEvent;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class GWTMeasureEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        hookGwtStatsFunctionAndSink();
    }

    public static void handleEvent(String group, String moduleName, String subSystem, String type, String millis) {
        MetricEvent metricEvent = new MetricEvent.Builder()
                .setEventGroup(group)
                .setModuleName(moduleName)
                .setSubSystem(subSystem)
                .setType(type)
                .setMillis(Long.parseLong(millis))
                .create();
        
        Window.alert(metricEvent.toString());
    }

    private native void hookGwtStatsFunctionAndSink() /*-{
        $wnd.gwtInitialized = true;
        $wnd.handleEvent = @com.google.code.gwtmeasure.client.GWTMeasureEntryPoint::handleEvent(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;);
        $wnd.sinkGwtEvents();
    }-*/;

}
