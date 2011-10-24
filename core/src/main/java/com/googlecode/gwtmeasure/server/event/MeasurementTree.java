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

import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class MeasurementTree {

    private String moduleName;
    private String subSystem;
    private String eventGroup;

    private final PerformanceTiming from;
    private final PerformanceTiming to;

    private long time;

    private final List<MeasurementTree> children = new ArrayList<MeasurementTree>();

    public static MeasurementTree create(PerformanceTiming from, PerformanceTiming to) {
        MeasurementTree tree = new MeasurementTree(from, to);
        tree.time = Math.abs(to.getMillis() - from.getMillis());

        tree.moduleName = from.getModuleName();
        tree.eventGroup = from.getEventGroup();
        tree.subSystem = from.getSubSystem();

        return tree;
    }

    public MeasurementTree(PerformanceTiming from, PerformanceTiming to) {
        this.from = from;
        this.to = to;
    }

    public void addChild(MeasurementTree child) {
        this.children.add(child);
    }

    public List<MeasurementTree> getChildren() {
        return Collections.unmodifiableList(children);
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

    public long getTime() {
        return time;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    public void setEventGroup(String eventGroup) {
        this.eventGroup = eventGroup;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public PerformanceTiming getFrom() {
        return from;
    }

    public PerformanceTiming getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasurementTree that = (MeasurementTree) o;

        if (time != that.time) return false;
        if (children != null ? !children.equals(that.children) : that.children != null) return false;
        if (eventGroup != null ? !eventGroup.equals(that.eventGroup) : that.eventGroup != null) return false;
        if (moduleName != null ? !moduleName.equals(that.moduleName) : that.moduleName != null) return false;
        if (subSystem != null ? !subSystem.equals(that.subSystem) : that.subSystem != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = moduleName != null ? moduleName.hashCode() : 0;
        result = 31 * result + (subSystem != null ? subSystem.hashCode() : 0);
        result = 31 * result + (eventGroup != null ? eventGroup.hashCode() : 0);
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MeasurementTree{" +
                "moduleName='" + moduleName + '\'' +
                ", subSystem='" + subSystem + '\'' +
                ", eventGroup='" + eventGroup + '\'' +
                ", time=" + time +
                ", children=" + children +
                '}';
    }
}
