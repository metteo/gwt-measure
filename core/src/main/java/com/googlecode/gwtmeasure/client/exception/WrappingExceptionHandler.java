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

package com.googlecode.gwtmeasure.client.exception;

import com.google.gwt.core.client.GWT;
import com.googlecode.gwtmeasure.client.internal.DeliveryQueue;
import com.googlecode.gwtmeasure.shared.IncidentReport;

/**
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class WrappingExceptionHandler implements GWT.UncaughtExceptionHandler {

    private GWT.UncaughtExceptionHandler handler;

    public WrappingExceptionHandler(GWT.UncaughtExceptionHandler handler) {
        this.handler = handler;
    }

    /**
     * If application has own uncaughtExceptionHandler delegates to that. Otherwise wraps exception into
     * UnhandledException and throws.
     * @param exception cause
     */
    public void onUncaughtException(Throwable exception) {
        IncidentReport report = IncidentReport.createReport(exception);
        DeliveryQueue.instance().pushIncident(report);

        if (handler != null) {
            handler.onUncaughtException(exception);
        } else {
            throw new UnhandledException(exception);
        }
    }



}
