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

package com.googlecode.gwtmeasure.shared;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class PerformanceTimingTest {

    private PerformanceTiming timing;

    @Before
    public void setUp() throws Exception {
        timing = new PerformanceTiming();
    }

    @Test
    public void testEquals() {
        PerformanceTiming suspect = new PerformanceTiming();
        assertHashCodeAndEquals(suspect, true);
    }

    @Test
    public void testEquals_Millis() {
        PerformanceTiming suspect = new PerformanceTiming();
        suspect.setMillis(1);
        assertHashCodeAndEquals(suspect, false);
    }

    @Test
    public void testEquals_Type() {
        PerformanceTiming suspect = new PerformanceTiming();
        suspect.setType("A");
        assertHashCodeAndEquals(suspect, false);
    }

    @Test
    public void testEquals_Group() {
        PerformanceTiming suspect = new PerformanceTiming();
        suspect.setEventGroup("A");
        assertHashCodeAndEquals(suspect, false);
    }

    @Test
    public void testEquals_Module() {
        PerformanceTiming suspect = new PerformanceTiming();
        suspect.setModuleName("A");
        assertHashCodeAndEquals(suspect, false);
    }

    @Test
    public void testEquals_SubSystem() {
        PerformanceTiming suspect = new PerformanceTiming();
        suspect.setSubSystem("A");
        assertHashCodeAndEquals(suspect, false);
    }

    @Test
    public void testEquals_Parameter() {
        PerformanceTiming suspect = new PerformanceTiming();
        suspect.setParameter("A", "B");
        assertHashCodeAndEquals(suspect, true);
    }

    private void assertHashCodeAndEquals(PerformanceTiming suspect, boolean equal) {
        assertThat(timing.equals(suspect), equalTo(equal));
        if (equal) {
            assertThat(timing.hashCode(), equalTo(suspect.hashCode()));
        } else {
            assertThat(timing.hashCode(), not(equalTo(suspect.hashCode())));
        }
    }

}
