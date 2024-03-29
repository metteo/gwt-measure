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

package com.googlecode.gwtmeasure.client.delivery;

import com.googlecode.gwtmeasure.client.PerformanceEvent;
import com.googlecode.gwtmeasure.client.PerformanceEventHandler;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.logging.Logger;

/**
 * GWT Logging implementation for measurements.
 *
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class LoggingChannel implements PerformanceEventHandler {

    private static final Logger logger = Logger.getLogger("GWT-Measure");
    
    public void onPerformanceEvent(PerformanceEvent event) {
        PerformanceTiming timing = event.getTiming();
        logger.info(timing.toString());
    }

}
