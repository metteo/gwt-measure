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
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.googlecode.gwtmeasure.client.internal.TimeUtils;
import com.googlecode.gwtmeasure.shared.IncidentReport;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class IncidentReportFactory {

    public IncidentReport createReport(Throwable throwable) {
        IncidentReport report = new IncidentReport();
        report.setText("Unhandled client side exception");

        prepareCommonAttributes(throwable, report);

        return report;
    }

    public IncidentReport createRpcReport(Throwable throwable) {
        IncidentReport report = new IncidentReport();
        String cause = determineRpcErrorCause(throwable);
        report.setText(cause);

        prepareCommonAttributes(throwable, report);

        return report;
    }

    private String determineRpcErrorCause(Throwable throwable) {
        try {
            throw throwable;
        } catch (IncompatibleRemoteServiceException e) {
            return "Client version is not compatible with server version";
        } catch (StatusCodeException e) {
            StatusCodeException statusCodeException = (StatusCodeException) throwable;
            int statusCode = statusCodeException.getStatusCode();
            if (statusCode != 0) {
                return "Response has unexpected http status code " + statusCode;
            } else {
                return "Server is not available";
            }
        } catch (InvocationException e) {
            return "Server is not available";
        } catch (Throwable e) {
            return "Internal server error";
        }
    }

    private void prepareCommonAttributes(Throwable throwable, IncidentReport report) {
        report.setTimestamp(TimeUtils.current());
        report.setMessage(JsonUtils.escapeValue(throwable.getMessage()));
        report.setStrongName(JsonUtils.escapeValue(GWT.getPermutationStrongName()));
        report.setUrl(Window.Location.getHref());

        int length = throwable.getStackTrace().length;
        String[] stackTrace = new String[length];
        StackTraceElement[] stackTrace1 = throwable.getStackTrace();
        for (int i = 0, stackTrace1Length = stackTrace1.length; i < stackTrace1Length; i++) {
            StackTraceElement element = stackTrace1[i];
            stackTrace[i] = JsonUtils.escapeValue(element.getMethodName());
        }
        report.setStackTrace(stackTrace);
    }

}
