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
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class TimeBasedComparatorTest {

    private TimeBasedComparator comparator;
    private ArrayList<PerformanceTiming> timings;

    @Before
    public void setUp() {
        comparator = new TimeBasedComparator();
        timings = new ArrayList<PerformanceTiming>();
    }

    @Test
    public void shouldSortByTime() throws Exception {
        PerformanceTiming a = createTiming(3);
        PerformanceTiming b = createTiming(1);

        timings.add(a);
        timings.add(b);

        Collections.sort(timings, comparator);

        assertThat(timings.get(0), sameInstance(b));
        assertThat(timings.get(1), sameInstance(a));
    }

    @Test
    public void shouldLeaveTrickyCasesAsIs() throws Exception {
        PerformanceTiming a = createTiming(1);
        PerformanceTiming b = createTiming(1);

        timings.add(a);
        timings.add(b);

        Collections.sort(timings, comparator);

        assertThat(timings.get(0), sameInstance(a));
        assertThat(timings.get(1), sameInstance(b));
    }

    private PerformanceTiming createTiming(long millis) {
        PerformanceTiming.Builder builder = new PerformanceTiming.Builder();
        builder.setMillis(millis);
        return builder.create();
    }


}
