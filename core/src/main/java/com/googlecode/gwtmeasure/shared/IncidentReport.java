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

package com.googlecode.gwtmeasure.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.googlecode.gwtmeasure.client.internal.TimeUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Representation of application incident. Usually it means unexpected client-side exception.
 *
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class IncidentReport implements HasJsonRepresentation {

    // TODO Incident unique id
    private long timestamp;
    private String text;
    private String message;
    private String strongName;
    private String url;
    private String[] stackTrace;

    private final Map<String, String> parameters = new HashMap<String, String>();

    public IncidentReport() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Set<String> getParameterNames() {
        return parameters.keySet();
    }

    public String getStrongName() {
        return strongName;
    }

    public void setStrongName(String strongName) {
        this.strongName = strongName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setParameter(String name, Object value) {
        String string = value == null ? "" : value.toString();
        this.parameters.put(name, string);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public static IncidentReport createReport(Throwable throwable) {
        IncidentReport report = new IncidentReport();
        report.setText("Unhandled client side exception");

        prepareCommonAttributes(throwable, report);

        return report;
    }

    public static IncidentReport createRpcReport(Throwable throwable) {
        IncidentReport report = new IncidentReport();
        String cause = determineRpcErrorCause(throwable);
        report.setText(cause);

        prepareCommonAttributes(throwable, report);

        return report;
    }

    private static String determineRpcErrorCause(Throwable throwable) {
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

    private static void prepareCommonAttributes(Throwable throwable, IncidentReport report) {
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

    public String jsonEncode(JsonEncoder encoder) {
        return encoder.encode(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IncidentReport{")
                .append("timestamp='")
                .append(timestamp)
                .append("', text='")
                .append(text)
                .append("', message='")
                .append(message)
                .append("', strongName='")
                .append(strongName)
                .append("', url='")
                .append(url)
                .append("', stackTrace=[");

        for (String element : stackTrace) {
            builder.append(element);
            builder.append(",");
        }

        builder.append("', parameters=[");
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            builder.append(",");
        }
        builder.append("]}");
        return builder.toString();
    }

}
