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

import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class EventCache {

    private final Map<String,String> lookupTable = new HashMap<String, String>();

    {
        lookupTable.put(Constants.TYPE_BEGIN, Constants.TYPE_END);
    }

    private final Map<String, List<PerformanceTiming>> pendingTimings = new HashMap<String, List<PerformanceTiming>>();

    public void append(PerformanceTiming timing) {
        String key = key(timing);
        List<PerformanceTiming> list = pendingTimings.get(key);
        if (list == null) {
            list = new ArrayList<PerformanceTiming>();
            pendingTimings.put(key, list);
        }
        list.add(timing);
    }

    public PerformanceTiming findMatch(PerformanceTiming timing) {
        String key = key(timing);
        List<PerformanceTiming> timings = pendingTimings.get(key);
        if (timings == null || timings.isEmpty()) {
            return null;
        } else {
            return timings.remove(0);
        }
    }

    private String key(PerformanceTiming timing) {
        StringBuilder builder = new StringBuilder();
        builder.append(timing.getModuleName());
        builder.append(timing.getSubSystem());
        builder.append(timing.getEventGroup());
        return builder.toString();
    }

}
