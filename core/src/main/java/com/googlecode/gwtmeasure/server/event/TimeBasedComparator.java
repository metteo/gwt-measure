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

import java.util.Comparator;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class TimeBasedComparator implements Comparator<PerformanceTiming> {

    public int compare(PerformanceTiming object1, PerformanceTiming object2) {
        int timeCompare = ((Long) object1.getMillis()).compareTo(object2.getMillis());
        if (timeCompare != 0) {
            return timeCompare;
        }

        // if in doubt leave the order as is
        return -1;
    }

}
