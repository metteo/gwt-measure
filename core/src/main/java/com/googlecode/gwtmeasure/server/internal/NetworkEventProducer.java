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
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class NetworkEventProducer {

    private MetricsEventHandler eventHandler;

    public NetworkEventProducer(MetricsEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void requestReceived(HttpServletRequest request) {
        HttpSession session = request.getSession();

        PerformanceTiming.Builder builder = new PerformanceTiming.Builder();

        builder
                .setSubSystem(Constants.SUB_SYSTEM_RPC)
                .setMillis(ServerTime.current())
                .setType(Constants.TYPE_REQUEST_RECEIVED)
                .setEventGroup(request.getHeader(Constants.HEADER_UID))
                .setParameter(Constants.PARAM_SESSION_ID, session.getId());

        eventHandler.onEvent(builder.create());
    }

    public void reponseSent(HttpServletRequest response) {
        HttpSession session = response.getSession();

        PerformanceTiming.Builder builder = new PerformanceTiming.Builder();

        builder
                .setSubSystem(Constants.SUB_SYSTEM_RPC)
                .setMillis(ServerTime.current())
                .setType(Constants.TYPE_RESPONSE_SENT)
                .setEventGroup(response.getHeader(Constants.HEADER_UID))
                .setParameter(Constants.PARAM_SESSION_ID, session.getId());

        eventHandler.onEvent(builder.create());
    }

}
