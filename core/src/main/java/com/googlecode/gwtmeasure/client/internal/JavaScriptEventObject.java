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

package com.googlecode.gwtmeasure.client.internal;

import com.googlecode.gwtmeasure.shared.PerformanceTiming;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public final class JavaScriptEventObject extends JavaScriptObject {

    // GWT Requirement
    protected JavaScriptEventObject() {
    }

    public final native String getModuleName() /*-{
        return this.moduleName == null ? null : "" + this.moduleName;
    }-*/;

    public final native String getSubSystem() /*-{
        return this.subSystem == null ? null : "" + this.subSystem;
    }-*/;

    public final native String getEventGroup() /*-{
        return this.evtGroup == null ? null : "" + this.evtGroup;
    }-*/;

    public final native double getMillis() /*-{
        return this.millis == null ? 0 : this.millis;
    }-*/;

    public final native String getType() /*-{
        return this.type == null ? null : "" + this.type;
    }-*/;

    private native JsArrayString getParameterNames() /*-{
        if (!this.extraParameters) {
            var a = new Array();
            for (name in this) {
                if (name != "moduleName" && name != "subSystem" && name != "evtGroup" && name != "millis" && name != "type") {
                  a.push(name);
                }
        }
        this.extraParameters = a;
        }
        return this.extraParameters
    }-*/;

    public native Object getParameter(String name) /*-{
        var r = this[name], t = typeof(r);
        if (t == "number") {
          r = @java.lang.Double::new(D)(r);
        } else if (t == "boolean") {
          r = @java.lang.Boolean::new(Z)(r);
        }
        return r;
  }-*/;

    public PerformanceTiming asJavaObject() {
        PerformanceTiming.Builder builder = new PerformanceTiming.Builder();
        builder.setModuleName(getModuleName())
                .setSubSystem(getSubSystem())
                .setEventGroup(getEventGroup())
                .setMillis(new Double(getMillis()).longValue())
                .setType(getType());

        JsArrayString names = getParameterNames();
        while (names.length() > 0) {
            String name = names.shift();
            Object value = getParameter(name);
            builder.setParameter(name, value);
        }

        return builder.create();
    }

}
