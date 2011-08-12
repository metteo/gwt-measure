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

import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class PatternMatcher {

    private RawEventStorage eventCache;

    public PatternMatcher(RawEventStorage eventCache) {
        this.eventCache = eventCache;
    }

    public boolean isTriggered(PerformanceTiming timing) {
        return Constants.TYPE_END.equals(timing.getType());
    }

    // TODO Visitor Query pattern
    public List<PerformanceTiming> findMatch(PerformanceTiming timing) {
        return eventCache.findMatch(timing, Constants.TYPE_BEGIN);
    }

    public PerformanceMetric prepareMetric(PerformanceTiming timing, List<PerformanceTiming> match) {
        PerformanceTiming result = match.get(0);

        PerformanceMetric metric = new PerformanceMetric();
        metric.setEventGroup(result.getEventGroup());
        metric.setModuleName(result.getModuleName());
        metric.setSubSystem(result.getSubSystem());

        long timeA = timing.getMillis();
        long timeB = result.getMillis();

        metric.setTime(Math.abs(timeA - timeB));

        return metric;
    }

}
