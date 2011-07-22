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

package com.googlecode.gwtmeasure.client.delivery;

import com.google.gwt.http.client.RequestBuilder;
import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.List;

/**
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class RequestBodyInjector extends ResultInjector {

    private static final int NO_LIMIT = -1;

    public RequestBodyInjector() {
        this(new MeasurementSerializer());
    }

    public RequestBodyInjector(MeasurementSerializer serializer) {
        super(serializer);
    }

    public Result inject(RequestBuilder requestBuilder, List<PerformanceTiming> timings, List<IncidentReport> incidents) {
        Result result = new Result();

        if (!timings.isEmpty()) {
            String serializedTimings = serializer.serialize(timings, NO_LIMIT);
            requestBuilder.setRequestData(serializedTimings);
            result.shouldSend = true;
        }

        injectIncidents(requestBuilder, incidents, result);

        return result;
    }

}
