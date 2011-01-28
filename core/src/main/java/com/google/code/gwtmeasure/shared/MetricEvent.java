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
