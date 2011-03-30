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

package com.googlecode.gwtmeasure.client.internal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class DeliveryQueueTest extends Assert {

    private DeliveryQueue queue;

    @Before
    public void setUp() {
        queue = new DeliveryQueue();
    }    

    @Test
    public void shouldBeEmpty() {
        assertThat(queue.hasIncidents(), equalTo(Boolean.FALSE));
        assertThat(queue.hasTimings(), equalTo(Boolean.FALSE));
    }

}
