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

import com.googlecode.gwtmeasure.shared.PerformanceTiming;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class EventCacheTest extends Assert {

    private EventCache cache;

    @Before
    public void setUp() throws Exception {
        cache = new EventCache();
    }

    @Test
    public void testAppend_Null() throws Exception {
        PerformanceTiming result = cache.findMatch(new PerformanceTiming());
        assertThat(result, nullValue());
    }
        
    @Test
    public void testAppend_Simple() throws Exception {
        PerformanceTiming timing = new PerformanceTiming();
        cache.append(timing);

        PerformanceTiming result = cache.findMatch(new PerformanceTiming());
        assertThat(result, sameInstance(timing));
    }

}
