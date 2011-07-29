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

package com.googlecode.gwtmeasure.sample.server;

import com.googlecode.gwtmeasure.server.MeasureContext;
import com.googlecode.gwtmeasure.server.spi.IncidentReportHandler;
import com.googlecode.gwtmeasure.server.spi.MetricsEventHandler;
import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class FilterConfiguration implements ServletContextListener, MetricsEventHandler, IncidentReportHandler {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        MeasureContext.instance().registerEventHandler(this);
        MeasureContext.instance().registerIncidentHandler(this);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    public void onEvent(PerformanceTiming metric) {
        System.out.println(metric);
    }

    public void onEvent(IncidentReport report) {
        System.out.println(report);
    }

}
