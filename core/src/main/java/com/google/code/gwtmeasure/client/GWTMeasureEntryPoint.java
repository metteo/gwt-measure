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
