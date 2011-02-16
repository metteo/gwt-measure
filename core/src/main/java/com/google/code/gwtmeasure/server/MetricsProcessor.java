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

package com.google.code.gwtmeasure.server;

import com.google.code.gwtmeasure.shared.Constants;
import com.google.code.gwtmeasure.shared.PerformanceMetrics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MetricsProcessor {

    private final MetricsDecoder decoder = new MetricsDecoder();
    private MetricsSink sink = new LoggingSink();

    public void extractAndProcess(HttpServletRequest request) {
        Object processed = request.getAttribute(Constants.ATTR_PROCESSED);
        if (Boolean.TRUE.equals(processed)) {
            return;
        }

        String result = request.getHeader(Constants.HEADER_RESULT);
        if (null != result) {
            handleMetrics(result);
        }
        request.setAttribute(Constants.ATTR_PROCESSED, Boolean.TRUE);
    }

    private void handleMetrics(String result) {
        String[] metrics = result.split("\\@");
        List<PerformanceMetrics> decodedMetrics = new ArrayList<PerformanceMetrics>();
        for (String metric : metrics) {            
            PerformanceMetrics performanceMetrics = decoder.decode(metric);
            decodedMetrics.add(performanceMetrics);
        }

        if (sink != null) {
            sink.flush(decodedMetrics);
        }
    }

    public void setMetricsSink(MetricsSink sink) {
        this.sink = sink;
    }

    public MetricsSink getMetricsSink() {
        return sink;
    }

}
