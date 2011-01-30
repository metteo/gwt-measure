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

    private String moduleName;
    private String subSystem;
    private String eventGroup;
    private long millis;
    private String type;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetricEvent event = (MetricEvent) o;

        if (millis != event.millis) return false;
        if (eventGroup != null ? !eventGroup.equals(event.eventGroup) : event.eventGroup != null) return false;
        if (moduleName != null ? !moduleName.equals(event.moduleName) : event.moduleName != null) return false;
        if (subSystem != null ? !subSystem.equals(event.subSystem) : event.subSystem != null) return false;
        if (type != null ? !type.equals(event.type) : event.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = moduleName != null ? moduleName.hashCode() : 0;
        result = 31 * result + (subSystem != null ? subSystem.hashCode() : 0);
        result = 31 * result + (eventGroup != null ? eventGroup.hashCode() : 0);
        result = 31 * result + (int) (millis ^ (millis >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
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
                '}';
    }

    public String encode() {
        return moduleName + '|'
                + subSystem + '|'
                + eventGroup + '|'
                + millis + '|'
                + type;
    }

    public static MetricEvent decode(String encodedEvent) {
        String[] tokens = encodedEvent.split("\\|");
        return new Builder()
                .setModuleName(tokens[0])
                .setSubSystem(tokens[1])
                .setEventGroup(tokens[2])
                .setMillis(Long.parseLong(tokens[3]))
                .setType(tokens[4])
                .create();
    }
    
}
