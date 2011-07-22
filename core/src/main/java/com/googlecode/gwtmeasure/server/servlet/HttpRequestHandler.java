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

package com.googlecode.gwtmeasure.server.servlet;

import com.googlecode.gwtmeasure.server.MetricsProcessor;
import com.googlecode.gwtmeasure.server.internal.NetworkEventProducer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public final class HttpRequestHandler {

    public static interface ServletClosure {
        void execute() throws IOException, ServletException;
    }

    private MetricsProcessor metricsProcessor;
    private NetworkEventProducer networkEventProducer;

    public HttpRequestHandler(MetricsProcessor metricsProcessor, NetworkEventProducer networkEventProducer) {
        this.metricsProcessor = metricsProcessor;
        this.networkEventProducer = networkEventProducer;
    }

    public void process(HttpServletRequest request, ServletClosure closure) throws IOException, ServletException {
        if (!metricsProcessor.isProcessed(request)) {
            networkEventProducer.requestReceived(request);
            metricsProcessor.extractAndProcess(request);
            metricsProcessor.markAsProcessed(request);
            closure.execute();
            networkEventProducer.responseSent(request);
        } else {
            closure.execute();
        }
    }

}
