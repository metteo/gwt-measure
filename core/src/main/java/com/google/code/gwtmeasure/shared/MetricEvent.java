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
            this.event.moduleName = type;
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
    public String toString() {
        return "MetricEvent{" +
                "moduleName='" + moduleName + '\'' +
                ", subSystem='" + subSystem + '\'' +
                ", eventGroup='" + eventGroup + '\'' +
                ", millis=" + millis +
                ", type='" + type + '\'' +
                '}';
    }
}
