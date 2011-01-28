package com.google.code.gwtmeasure.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class GWTMeasureEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        hookGwtStatsFunctionAndSink();
    }

    public static void handleEvent(String group, String name, String subSystem, String type, String millis) {
        Window.alert(group + name + subSystem + type + millis);
    }

    private native void hookGwtStatsFunctionAndSink() /*-{
        $wnd.gwtInitialized = true;
        $wnd.handleEvent = @com.google.code.gwtmeasure.client.GWTMeasureEntryPoint::handleEvent(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;);
        $wnd.sinkGwtEvents();
    }-*/;

}
