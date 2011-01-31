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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MetricsProcessor {

    public void extractAndProcess(HttpServletRequest request) {
        String result = request.getHeader(Constants.HEADER_RESULT);
        if (null != result) {
            handleMetrics(result);
        }
    }

    private void handleMetrics(String result) {
        String[] metrics = result.split("\\@");
        for (String metric : metrics) {
            PerformanceMetrics performanceMetrics = PerformanceMetrics.decode(metric);
            System.out.println(performanceMetrics);
        }
    }

}
