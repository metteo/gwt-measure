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

package com.google.code.gwtmeasure.shared;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class PerformanceMetrics implements IsSerializable {

    private String moduleName = "";
    private String subSystem = "";
    private String eventGroup = "";
    private double millis;
    private String type = "";

    private Map<String, String> parameters = new HashMap<String, String>();

    public PerformanceMetrics() {
    }

    public static class Builder {

        private PerformanceMetrics event;

        public Builder() {
            this.event = new PerformanceMetrics();
        }

        public Builder setModuleName(String moduleName) {
            this.event.moduleName = moduleName;
            return this;
        }

        public Builder setSubSystem(String subSystem) {
            this.event.subSystem = subSystem;
            return this;
        }

        public Builder setEventGroup(String eventGroup) {
            this.event.eventGroup = eventGroup;
            return this;
        }

        public Builder setMillis(double millis) {
            this.event.millis = millis;
            return this;
        }

        public Builder setType(String type) {
            this.event.type = type;
            return this;
        }

        public Builder setParameter(String name, Object value) {
            String string = value == null ? "" : value.toString();
            this.event.parameters.put(name, string);
            return this;
        }

        public PerformanceMetrics create() {
            return this.event;
        }

    }

    public String getModuleName() {
        return moduleName;
    }

    public String getSubSystem() {
        return subSystem;
    }

    public String getEventGroup() {
        return eventGroup;
    }

    public double getMillis() {
        return millis;
    }

    public String getType() {
        return type;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public String getSessionId() {
        return parameters.get(Constants.PARAM_SESSION_ID);
    }

    public Double getBytes() {
        return Double.valueOf(parameters.get(Constants.PARAM_BYTES));
    }

    public String getMethod() {
        return parameters.get(Constants.PARAM_METHOD);
    }

    public String getFragment() {
        return parameters.get(Constants.PARAM_FRAGMENT);
    }

    public String getSize() {
        return parameters.get(Constants.PARAM_SIZE);
    }

    public void setParam(String param) {
        parameters.put("param", param);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PerformanceMetrics metrics = (PerformanceMetrics) o;

        if (eventGroup != null ? !eventGroup.equals(metrics.eventGroup) : metrics.eventGroup != null) return false;
        if (moduleName != null ? !moduleName.equals(metrics.moduleName) : metrics.moduleName != null) return false;
        if (subSystem != null ? !subSystem.equals(metrics.subSystem) : metrics.subSystem != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = moduleName != null ? moduleName.hashCode() : 0;
        result = 31 * result + (subSystem != null ? subSystem.hashCode() : 0);
        result = 31 * result + (eventGroup != null ? eventGroup.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Metrics{" +
                "moduleName='" + moduleName + '\'' +
                ", subSystem='" + subSystem + '\'' +
                ", eventGroup='" + eventGroup + '\'' +
                ", millis=" + millis +
                ", type='" + type + '\'' +
                '}';
    }

    public String jsonEncode() {
        JSONObject object = new JSONObject();

        if (moduleName != null) object.put("moduleName", new JSONString(moduleName));
        if (subSystem != null) object.put("subSystem", new JSONString(subSystem));
        if (eventGroup != null) object.put("eventGroup", new JSONString(eventGroup));
        object.put("millis", new JSONNumber(millis));
        if (type!= null) object.put("type", new JSONString(type));

        if (!parameters.isEmpty()) {
            JSONObject params = new JSONObject();
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();
                if (value != null) {
                    params.put(name, new JSONString(value));
                }
            }
            object.put("parameters", params);
        }

        return object.toString();
    }

    private String string(String value) {
        return value == null ? "" : value;
    }

}
