/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.gwtmeasure.server.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class LoggingMetricConsumer implements MetricConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MetricConsumer.class);

    public void publish(MeasurementTree metric) {
        StringBuilder output = new StringBuilder();
        traverse(metric, 0, output);
        logger.info(output.toString());
    }

    private void traverse(MeasurementTree metric, int i, StringBuilder output) {
        if (i > 0) {
            output.append("\n");
        }
        for (int j = 0; j < i; j++) {
            output.append(">   ");
        }
        output.append(metric);

        for (MeasurementTree child : metric.getChildren()) {
            traverse(child, ++i, output);
        }
    }

}
