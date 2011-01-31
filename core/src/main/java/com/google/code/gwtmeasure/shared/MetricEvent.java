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

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MetricEvent implements IsSerializable {

    private String moduleName = "";
    private String subSystem  = "";
    private String eventGroup  = "";
    private long millis;
    private String type = "";
    private String sessionId  = "";
    private long bytes;
    private String method = "";

    public MetricEvent() {
    }

    public static class Builder {

        private MetricEvent event;

        public Builder() {
            this.event = new MetricEvent();
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

        public Builder setMillis(long millis) {
            this.event.millis = millis;
            return this;
        }

        public Builder setSessionId(String sessionId) {
            this.event.sessionId = sessionId;
            return this;
        }

        public Builder setMethod(String method) {
            this.event.method = method;
            return this;
        }

        public Builder setBytes(long bytes) {
            this.event.bytes = bytes;
            return this;
        }

        public Builder setType(String type) {
            this.event.type = type;
            return this;
        }

        public MetricEvent create() {
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

    public long getMillis() {
        return millis;
    }

    public String getType() {
        return type;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Long getBytes() {
        return bytes;
    }

    public String getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetricEvent that = (MetricEvent) o;

        if (bytes != that.bytes) return false;
        if (millis != that.millis) return false;
        if (eventGroup != null ? !eventGroup.equals(that.eventGroup) : that.eventGroup != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        if (moduleName != null ? !moduleName.equals(that.moduleName) : that.moduleName != null) return false;
        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;
        if (subSystem != null ? !subSystem.equals(that.subSystem) : that.subSystem != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = moduleName != null ? moduleName.hashCode() : 0;
        result = 31 * result + (subSystem != null ? subSystem.hashCode() : 0);
        result = 31 * result + (eventGroup != null ? eventGroup.hashCode() : 0);
        result = 31 * result + (int) (millis ^ (millis >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        result = 31 * result + (int) (bytes ^ (bytes >>> 32));
        result = 31 * result + (method != null ? method.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MetricEvent{" +
                "moduleName='" + moduleName + '\'' +
                ", subSystem='" + subSystem + '\'' +
                ", eventGroup='" + eventGroup + '\'' +
                ", millis=" + millis +
                ", type='" + type + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", bytes=" + bytes +
                ", method='" + method + '\'' +
                '}';
    }

    public String encode() {
        return string(moduleName) + '|'
                + string(subSystem) + '|'
                + string(eventGroup) + '|'
                + millis + '|'
                + string(type) + '|'
                + string(sessionId) + '|'
                + bytes + '|'
                + string(method);
    }

    private String string(String value) {
        return value == null ? "" : value;
    }

    public static MetricEvent decode(String encodedEvent) {
        String[] tokens = encodedEvent.split("\\|");
        return new Builder()
                .setModuleName(tokens.length >=1 ? tokens[0] : "")
                .setSubSystem(tokens.length >=2 ? tokens[1] : "")
                .setEventGroup(tokens.length >=3 ? tokens[2] : "")
                .setMillis(tokens.length >=4 ? Long.parseLong(tokens[3]) : 0)
                .setType(tokens.length >=5 ? tokens[4] : "")
                .setSessionId(tokens.length >=6 ? tokens[5] : "")
                .setBytes(tokens.length >=7 ? Long.parseLong(tokens[6]) : 0)
                .setMethod(tokens.length >=8 ? tokens[7] : "")
                .create();
    }
    
}
