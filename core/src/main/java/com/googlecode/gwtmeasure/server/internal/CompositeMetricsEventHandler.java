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

package com.googlecode.gwtmeasure.server.internal;

import com.googlecode.gwtmeasure.server.spi.MetricsEventHandler;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class CompositeMetricsEventHandler implements MetricsEventHandler {

    final List<MetricsEventHandler> handlers = new ArrayList<MetricsEventHandler>();

    public void onEvent(PerformanceTiming timing) {
        for (MetricsEventHandler handler : handlers) {
            handler.onEvent(timing);
        }
    }

    public void addHandler(MetricsEventHandler handler) {
        handlers.add(handler);
    }

    public List<MetricsEventHandler> getHandlers() {
        return new ArrayList<MetricsEventHandler>(handlers);
    }

}
