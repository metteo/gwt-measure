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

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.GWT;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class TimeUtils {

    private static TimeProvider timeProvider;

    static {
        if (GWT.isClient()) {
            timeProvider = new ClientTimeProvider();
        } else {
            timeProvider = new ServerTimeProvider();
        }
    }

    private TimeUtils() {
    }

    public static long current() {
        return timeProvider.currentTime();
    }

    /**
     * Sets alternative time provider if the time in your system is not determined via System.currentTimeMillis()
     * @param timeProvider implementation
     */
    public static void setTimeProvider(TimeProvider timeProvider) {
        TimeUtils.timeProvider = timeProvider;
    }

    public static interface TimeProvider {
        long currentTime();
    }

    private static class ClientTimeProvider implements TimeProvider {

        public long currentTime() {
            return (long) Duration.currentTimeMillis();
        }

    }

    private static class ServerTimeProvider implements TimeProvider {

        public long currentTime() {
            return System.currentTimeMillis();
        }

    }

}
