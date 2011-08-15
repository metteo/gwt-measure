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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class InMemoryStorage implements RawEventStorage {

    private final Map<String, List<PerformanceTiming>> pendingTimings = new ConcurrentHashMap<String, List<PerformanceTiming>>();

    public void put(PerformanceTiming timing) {
        String key = key(timing.getModuleName(), timing.getEventGroup());
        List<PerformanceTiming> timingSet = pendingTimings.get(key);
        if (timingSet == null) {
            timingSet = new ArrayList<PerformanceTiming>();
            pendingTimings.put(key, timingSet);
        }
        timingSet.add(timing);
    }

    public void remove(List<PerformanceTiming> timings) {
        for (PerformanceTiming timing : timings) {
            String key = key(timing.getModuleName(), timing.getEventGroup());
            List<PerformanceTiming> timingSet = pendingTimings.get(key);
            timingSet.remove(timing);
        }
    }

    public List<PerformanceTiming> findMatch(String moduleName, String eventGroup) {
        ArrayList<PerformanceTiming> result = new ArrayList<PerformanceTiming>();

        String key = key(moduleName, eventGroup);
        List<PerformanceTiming> timingSet = pendingTimings.get(key);

        if (timingSet == null) {
            return result;
        }

        for (PerformanceTiming suspect : timingSet) {
            result.add(suspect);
        }

        return result;
    }

    public void clear() {
        pendingTimings.clear();
    }

    private String key(String moduleName, String eventGroup) {
        StringBuilder builder = new StringBuilder();
        builder.append(moduleName);
        builder.append(eventGroup);
        return builder.toString();
    }

}
