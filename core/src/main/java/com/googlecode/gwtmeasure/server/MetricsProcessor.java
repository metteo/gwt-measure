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

package com.googlecode.gwtmeasure.server;

import com.googlecode.gwtmeasure.server.spi.IncidentReportHandler;
import com.googlecode.gwtmeasure.server.spi.MetricsEventHandler;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class MetricsProcessor {

    private final JsonDecoder decoder;
    private final MetricsEventHandler eventHandler;
    private IncidentReportHandler reportHandler;

    public MetricsProcessor(JsonDecoder decoder, MetricsEventHandler eventHandler, IncidentReportHandler reportHandler) {
        this.decoder = decoder;
        this.eventHandler = eventHandler;
        this.reportHandler = reportHandler;
    }

    public void extractAndProcess(HttpServletRequest request) {
        for (int i = 0; ; i++) {
            String result = request.getHeader(Constants.HEADER_RESULT + "-" + i);
            if (null == result) { // No more headers
                break;
            }
            Collection<PerformanceTiming> performanceTimings = decoder.decodeTimings(result);
            sinkEvents(performanceTimings);
        }
        String errors = request.getHeader(Constants.HEADER_ERRORS);
        if (null != errors) {
            handleErrors(errors);
        }
    }

    private void handleErrors(String errors) {
        Collection<IncidentReport> incidentReports = decoder.decodeErrors(errors);
        for (IncidentReport report : incidentReports) {
            reportHandler.onEvent(report);
        }
    }


    private void sinkEvents(Collection<PerformanceTiming> performanceTiming) {
        for (PerformanceTiming timing : performanceTiming) {
            eventHandler.onEvent(timing);
        }
    }

    public void markAsProcessed(HttpServletRequest request) {
        request.setAttribute(Constants.ATTR_PROCESSED, Boolean.TRUE);
    }

    public boolean isProcessed(HttpServletRequest request) {
        Object processed = request.getAttribute(Constants.ATTR_PROCESSED);
        return Boolean.TRUE.equals(processed);
    }

}
