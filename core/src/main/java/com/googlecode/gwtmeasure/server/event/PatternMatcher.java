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

import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.List;
import java.util.Stack;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class PatternMatcher {

    public boolean isClosed(List<PerformanceTiming> matches) {
        int depth = 0;
        for (PerformanceTiming match : matches) {
            if (match.isBeginEvent()) {
                depth++;
            } else if (match.isEndEvent()) {
                depth--;
            }
        }
        return depth == 1;
    }

    public MeasurementTree prepareMeasurementTree(List<PerformanceTiming> matches) {
        Stack<PerformanceTiming> openTimings = new Stack<PerformanceTiming>();
        Stack<MeasurementTree> resultingMetrics = new Stack<MeasurementTree>();
        int lastNestingLevel = 0;

        for (PerformanceTiming match : matches) {
            if (match.isBeginEvent()) {
                openTimings.push(match);
            } else if (match.isEndEvent()) {
                PerformanceTiming startTiming = openTimings.pop();

                MeasurementTree metric = createMetric(match, startTiming);
                if (!resultingMetrics.isEmpty()) {
                    if (lastNestingLevel != openTimings.size()) {
                        for (MeasurementTree tree : resultingMetrics) {
                            metric.addChild(tree);
                        }
                        resultingMetrics.clear();
                    }
                    resultingMetrics.push(metric);
                } else {
                    resultingMetrics.push(metric);
                    lastNestingLevel = openTimings.size();
                }
            }
        }

        return resultingMetrics.pop();
    }

    private MeasurementTree createMetric(PerformanceTiming endTiming, PerformanceTiming startTiming) {
        return MeasurementTree.create(startTiming, endTiming);
    }

}
