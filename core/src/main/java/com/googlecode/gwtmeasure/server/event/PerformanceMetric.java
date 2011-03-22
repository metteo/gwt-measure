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

package com.googlecode.gwtmeasure.server.event;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class PerformanceMetric {

    private String moduleName;
    private String subSystem;
    private String eventGroup;
    private long time;
    private String name;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    public String getEventGroup() {
        return eventGroup;
    }

    public void setEventGroup(String eventGroup) {
        this.eventGroup = eventGroup;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PerformanceMetric that = (PerformanceMetric) o;

        if (time != that.time) return false;
        if (eventGroup != null ? !eventGroup.equals(that.eventGroup) : that.eventGroup != null) return false;
        if (moduleName != null ? !moduleName.equals(that.moduleName) : that.moduleName != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (subSystem != null ? !subSystem.equals(that.subSystem) : that.subSystem != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = moduleName != null ? moduleName.hashCode() : 0;
        result = 31 * result + (subSystem != null ? subSystem.hashCode() : 0);
        result = 31 * result + (eventGroup != null ? eventGroup.hashCode() : 0);
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PerformanceMetric{" +
                "moduleName='" + moduleName + '\'' +
                ", subSystem='" + subSystem + '\'' +
                ", eventGroup='" + eventGroup + '\'' +
                ", time=" + time +
                ", name='" + name + '\'' +
                '}';
    }
}
