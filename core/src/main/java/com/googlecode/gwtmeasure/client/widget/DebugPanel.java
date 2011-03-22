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

package com.googlecode.gwtmeasure.client.widget;

import com.googlecode.gwtmeasure.shared.PerformanceTiming;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.DialogBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class DebugPanel extends DialogBox {

    private final List<PerformanceTiming> data = new ArrayList<PerformanceTiming>();
    private final CellTable<PerformanceTiming> cellTable;

    public DebugPanel() {
        setTitle("GWT-Measure Debug Panel");
        setModal(false);

        cellTable = new CellTable<PerformanceTiming>();
        
        cellTable.addColumn(new TextColumn<PerformanceTiming>() {
            @Override
            public String getValue(PerformanceTiming object) {
                return object.getModuleName();
            }
        }, new TextHeader("Module"));
        cellTable.addColumn(new TextColumn<PerformanceTiming>() {
            @Override
            public String getValue(PerformanceTiming object) {
                return object.getSubSystem();
            }
        }, new TextHeader("SubSystem"));
        cellTable.addColumn(new TextColumn<PerformanceTiming>() {
            @Override
            public String getValue(PerformanceTiming object) {
                return object.getEventGroup();
            }
        }, new TextHeader("Event Group"));
        cellTable.addColumn(new TextColumn<PerformanceTiming>() {
            @Override
            public String getValue(PerformanceTiming object) {
                return object.getMillis()+ "ms";
            }
        }, new TextHeader("Timestamp"));
        cellTable.addColumn(new TextColumn<PerformanceTiming>() {
            @Override
            public String getValue(PerformanceTiming object) {
                return object.getType();
            }
        }, new TextHeader("Type"));
        cellTable.addColumn(new TextColumn<PerformanceTiming>() {
            @Override
            public String getValue(PerformanceTiming object) {
                Set<String> names = object.getParameterNames();
                StringBuilder builder = new StringBuilder();
                for (String name : names) {
                    String value = object.getParameter(name);
                    builder.append(name).append("=\"").append(value).append("\" ");
                }
                return builder.toString();
            }
        }, new TextHeader("Parameters"));

        setWidget(cellTable);
    }

    public void appendDebugLine(PerformanceTiming metric) {
        if (isShowing()) {
            data.add(metric);
            cellTable.setRowData(data);
        }
    }

}
